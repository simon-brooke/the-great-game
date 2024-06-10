(ns cc.journeyman.the-great-game.merchants.strategies.simple
  "Default trading strategy for merchants.

  The simple strategy buys a single product in the local market if there is
  one which can be traded profitably, trades it to the chosen target market,
  and sells it there. If there is no commodity locally which can be traded
  profitably, moves towards home with no cargo. If at home and no commodity
  can be traded profitably, does not move."
  (:require [taoensso.telemere.timbre :as l :refer [info error spy]]
            [cc.journeyman.the-great-game.utils :refer [deep-merge]]
            [cc.journeyman.the-great-game.gossip.gossip :refer [move-gossip]]
            [cc.journeyman.the-great-game.merchants.planning :refer [augment-plan plan-trade select-cargo]]
            [cc.journeyman.the-great-game.merchants.merchant-utils :refer
             [add-stock add-known-prices]]
            [cc.journeyman.the-great-game.world.routes :refer [find-route]]))

(defn plan-and-buy
  "Return a world like this `world`, in which this `merchant` has planned
  a new trade, and bought appropriate stock for it. If no profitable trade
  can be planned, the merchant is simply moved towards their home."
  [merchant world]
  (let [m (cond
            (keyword? merchant)
            (-> world :merchants merchant)
            (map? merchant)
            merchant)
        id (:id m)
        location (:location m)
        market (-> world :cities location)
        plan (select-cargo merchant world)]
    (l/debug "plan-and-buy: merchant" id)
    (cond
      (seq? plan)
      (let
        [c (:commodity plan)
         p (* (:quantity plan) (:buy-price plan))
         q (:quantity plan)]
        (l/info "Merchant" id "bought" q "units of" c "at" location "for" p plan)
        {:merchants
         {id
          {:stock (add-stock (:stock m) {c q})
           :cash (- (:cash m) p)
           :known-prices (add-known-prices m world)
           :plan plan}}
         :cities
         {location
          {:stock (assoc (:stock market) c (- (-> market :stock c) q))
           :cash (+ (:cash market) p)}}})
      ;; if no plan, then if at home stay put
      (= (:location m) (:home m))
      (do
        (l/info "Merchant" id "remains at home in" location)
        {})
      ;; else move towards home
      :else
      (let [route (find-route world location (:home m))
            next-location (nth route 1)]
        (l/info "No trade possible at" location "; merchant" id "moves to" next-location)
        (merge
          {:merchants
           {id
            {:location next-location}}}
          (move-gossip id world next-location))))))

(defn re-plan
  "Having failed to sell a cargo at current location, re-plan a route to
  sell the current cargo. Returns a revised world."
  [merchant world]
  (let [m (cond
            (keyword? merchant)
            (-> world :merchants merchant)
            (map? merchant)
            merchant)
        id (:id m)
        plan (augment-plan m world (plan-trade m world (-> m :plan :commodity)))]
    (l/debug "re-plan: merchant" id)
    (deep-merge
      world
      {:merchants
       {id
        {:plan plan}}})))

(defn sell-and-buy
  "Return a new world like this `world`, in which this `merchant` has sold
  their current stock in their current location, and planned a new trade, and
  bought appropriate stock for it."
  ;; TODO: this either sells the entire cargo, or, if the market can't afford
  ;; it, none of it. And it does not cope with selling different commodities
  ;; in different markets.
  [merchant world]
  (let [m (cond
            (keyword? merchant)
            (-> world :merchants merchant)
            (map? merchant)
            merchant)
        id (:id m)
        location (:location m)
        market (-> world :cities location)
        stock-value (reduce
                      +
                      (map
                        #(* (-> m :stock %) (-> market :prices m))
                        (keys (:stock m))))]
    (l/debug "sell-and-buy: merchant" id)
    (if
      (>= (:cash market) stock-value)
      (do
        (l/info "Merchant" id "sells" (:stock m) "at" location "for" stock-value)
        (plan-and-buy
          merchant
          (deep-merge
            world
            {:merchants
             {id
              {:stock {}
               :cash (+ (:cash m) stock-value)
               :known-prices (add-known-prices m world)}}
             :cities
             {location
              {:stock (add-stock (:stock m) (:stock market))
               :cash (- (:cash market) stock-value)}}})))
      ;; else
      (re-plan merchant world))))

(defn move-merchant
  "Handle general en route movement of this `merchant` in this `world`;
  return a (partial or full) world like this `world` but in which the
  merchant may have been moved ot updated."
  [merchant world]
  (let [m (cond
            (keyword? merchant)
            (-> world :merchants merchant)
            (map? merchant)
            merchant)
        id (:id m)
        at-destination? (and (:plan m) (= (:location m) (-> m :plan :destination)))
        plan (:plan m)
        next-location (if plan
                        (nth
                          (find-route
                            world
                            (:location m)
                            (:destination plan))
                          1)
                        (:location m))]
    (l/debug "move-merchant: merchant" id "at" (:location m)
             "destination" (-> m :plan :destination) "next" next-location
             "at destination" at-destination?)
    (cond
      ;; if the merchant is at the destination of their current plan
      ;; sell all cargo and repurchase.
      at-destination?
      (sell-and-buy merchant world)
      ;; if they don't have a plan, seek to create one
      (nil? plan)
      (plan-and-buy merchant world)
      ;; otherwise, move one step towards their destination
      (and next-location (not= next-location (:location m)))
      (do
        (l/info "Merchant " id " moving from " (:location m) " to " next-location)
        (deep-merge
          {:merchants
           {id
            {:location next-location
             :known-prices (add-known-prices m world)}}}
          (move-gossip id world next-location)))
      :else
      (do
        (l/info "Merchant" id "has plan but no next-location; currently at"
                (:location m) ", destination is" (:destination plan))
        world))))


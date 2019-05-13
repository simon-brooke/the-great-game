(ns the-great-game.merchants.merchants
  "Trade planning for merchants, primarily."
  (:require [the-great-game.world.routes :refer [find-routes]]
            [the-great-game.world.world :refer [actual-price]]))


(defn expected-price
  "Find the price anticipated, given this `world`, by this `merchant` for
  this `commodity` in this `city`. If no information, assume 1.
  `merchant` should be passed as a map, `commodity` and `city` should be passed as keywords."
  [merchant commodity city]
  (or
    (:price
      (last
        (sort-by
          :date
          (-> merchant :known-prices city commodity))))
    1))


(defn burden
  "The total weight of the current cargo carried by this `merchant` in this
  `world`."
  [merchant world]
  (let [m (cond
            (keyword? merchant)
            (-> world :merchants merchant)
            (map? merchant)
            merchant)
        cargo (-> m :stock)]
    (reduce
      +
      0
      (map
        #(* (cargo %) (-> world :commodities % :weight))
        (keys cargo)))))


(defn find-trade-plan
  "Find the best destination in this `world` for this `commodity` given this
  `merchant` and this `origin`. If two cities are anticipated to offer the
  same price, the nearer should be preferred; if two are equally distant, the
  ones nearer to the merchant's home should be preferred.
  `merchant` may be passed as a map or a keyword; `commodity` should  be
  passed as a keyword.

  The returned plan is a map with keys:

  # :merchant - the id of the `merchant` for whom the plan was created;
  # :origin - the city from which the trade starts;
  # :destination - the city to which the trade is planned;
  # :commodity - the `commodity` to be carried;
  # :buy-price - the price at which that `commodity` can be bought;
  # :expected-price - the price at which the `merchant` anticipates
      that `commodity` can be sold;
  # :distance - the number of stages in the planned journey
  # :dist-to-home - the distance from `destination` to the `merchant`'s
      home city."
  [merchant world commodity]
  (let [m (cond
            (keyword? merchant)
            (-> world :merchants merchant)
            (map? merchant)
            merchant)
        origin (-> m :location)
        destinations (remove #(= % origin) (keys (-> world :cities)))
        plans (map
                 #(hash-map
                    :merchant (-> m :id)
                    :origin origin
                    :destination %
                    :commodity commodity
                    :buy-price (actual-price world commodity origin)
                    :expected-price (expected-price
                                      merchant
                                      commodity
                                      %)
                    :distance (count
                                (first
                                  (find-routes (:routes world) origin %)))
                    :dist-to-home (count
                                    (first
                                      (find-routes
                                        (:routes world)
                                        (-> world :merchants merchant :home)
                                        %)))
                    )
                 destinations)
        best-price (apply min (filter number? (map :expected-price plans)))
        nearest (apply min (map :distance plans))]
    (first
      (sort
        #(compare (:dist-to-home %1) (:dist-to-home %2))
        (filter
          #(and
             (= (:expected-price %) best-price)
             (= (:distance %) nearest))
          plans)))))


(defn augment-plan
  "Augment this `plan` constructed in this `world` for this `merchant` with
  the `:quantity` of goods which should be bought and the `:expected-profit`
  of the trade.

  Returns the augmented plan."
  [merchant world plan]
  (let [m (cond
            (keyword? merchant)
            (-> world :merchants merchant)
            (map? merchant)
            merchant)
        available (-> world :cities (:origin plan) :stock (:commodity plan))
        can-carry (quot
                    (- (-> m :capacity) (burden m world))
                    (-> world :commodities (:commodity plan) :weight))
        can-afford (quot
                     (-> merchant :cash)
                     (-> world :commodities (:commodity plan) :weight))
        q (min available can-carry can-afford)
        p (* q (- (:expected-price plan) (:buy-price plan)))]
    (assoc plan :quantity q :expected-profit p)))


(defn select-cargo
  "A `merchant`, in a given location in a `world`, will choose to buy a cargo
  within the limit they are capable of carrying, which they can anticipate
  selling for a profit at a destination."
  [merchant world]
  (let [m (cond
            (keyword? merchant)
            (-> world :merchants merchant)
            (map? merchant)
            merchant)
        origin (-> m :location)
        available (-> world :cities origin :stock)
        plans (map
                #(augment-plan
                   m
                   world
                   (find-trade-plan m world %))
                (filter
                  #(let [q (-> world :cities origin :stock %)]
                     (and (number? q) (> q 0)))
                  (keys available)))
        best-profit (apply min (filter number? (map :expected-profit plans)))
        nearest (apply min (map :distance plans))]
    (first
      (sort
        #(compare (:dist-to-home %1) (:dist-to-home %2))
        (filter
          #(and
             (= (:expected-profit %) best-profit)
             (= (:distance %) nearest))
          plans))) ))


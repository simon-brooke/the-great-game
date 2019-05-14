(ns the-great-game.merchants.merchants
  "Trade planning for merchants, primarily."
  (:require [taoensso.timbre :as l :refer [info error]]
            [the-great-game.utils :refer [deep-merge]]
            [the-great-game.world.routes :refer [find-route]]
            [the-great-game.world.world :refer [actual-price default-world]]))


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


(defn make-target-filter
  "Construct a filter which, when applied to a list of maps,
  will pass those which match these `targets`, where each target
  is a tuple [key value]."
  ;; TODO: this would probably be more elegant as a macro
  [targets]
  (eval
    (list
      'fn
      (vector 'plan)
      (cons
        'and
        (map
          #(list
             '=
             (list (first %) 'plan)
             (nth % 1))
          targets)))))


(defn generate-trade-plans
  "Generate all possible trade plans for this `merchant` and this `commodity`
  in this `world`.

  Returned plans are maps with keys:

* :merchant - the id of the `merchant` for whom the plan was created;
* :origin - the city from which the trade starts;
* :destination - the city to which the trade is planned;
* :commodity - the `commodity` to be carried;
* :buy-price - the price at which that `commodity` can be bought;
* :expected-price - the price at which the `merchant` anticipates
      that `commodity` can be sold;
* :distance - the number of stages in the planned journey
* :dist-to-home - the distance from `destination` to the `merchant`'s
      home city."
  [merchant world commodity]
  (let [m (cond
            (keyword? merchant)
            (-> world :merchants merchant)
            (map? merchant)
            merchant)
        origin (-> m :location)]
    (map
      #(hash-map
         :merchant (-> m :id)
         :origin origin
         :destination %
         :commodity commodity
         :buy-price (actual-price world commodity origin)
         :expected-price (expected-price
                           m
                           commodity
                           %)
         :distance (count
                     (find-route world origin %))
         :dist-to-home (count
                         (find-route
                             world
                             (:home m)
                             %)))
      (remove #(= % origin) (keys (-> world :cities))))))

(defn nearest-with-targets
  "Return the distance to the nearest destination among those of these
  `plans` which match these `targets`. Plans are expected to be plans
  as returned by `generate-trade-plans`, q.v.; `targets` are expected to be
  as accepted by `make-target-filter`, q.v."
  [plans targets]
  (apply
    min
    (map
      :distance
      (filter
        (make-target-filter targets)
        plans))))

(defn plan-trade
  "Find the best destination in this `world` for this `commodity` given this
  `merchant` and this `origin`. If two cities are anticipated to offer the
  same price, the nearer should be preferred; if two are equally distant, the
  ones nearer to the merchant's home should be preferred.
  `merchant` may be passed as a map or a keyword; `commodity` should  be
  passed as a keyword.

  The returned plan is a map with keys:

  * :merchant - the id of the `merchant` for whom the plan was created;
  * :origin - the city from which the trade starts;
  * :destination - the city to which the trade is planned;
  * :commodity - the `commodity` to be carried;
  * :buy-price - the price at which that `commodity` can be bought;
  * :expected-price - the price at which the `merchant` anticipates
  that `commodity` can be sold;
  * :distance - the number of stages in the planned journey
  * :dist-to-home - the distance from `destination` to the `merchant`'s
  home city."
  [merchant world commodity]
  (let [plans (generate-trade-plans merchant world commodity)
        best-prices (filter
                      (make-target-filter
                        [[:expected-price
                          (apply
                            max
                            (filter number? (map :expected-price plans)))]])
                      plans)]
    (first
      (sort-by
        ;; all other things being equal, a merchant would prefer to end closer
        ;; to home.
        #(- 0 (:dist-to-home %))
        ;; a merchant will seek the best price, but won't go further than
        ;; needed to get it.
        (filter
          (make-target-filter
            [[:distance
              (apply min (filter number? (map :distance best-prices)))]])
          best-prices)))))


(defn can-carry
  "Return the number of units of this `commodity` which this `merchant`
  can carry in this `world`, given their current burden."
  [merchant world commodity]
  (let [m (cond
            (keyword? merchant)
            (-> world :merchants merchant)
            (map? merchant)
            merchant)]
    (quot
      (- (:capacity m) (burden m world))
      (-> world :commodities commodity :weight))))

(defn can-afford
  "Return the number of units of this `commodity` which this `merchant`
  can afford to buy in this `world`."
  [merchant world commodity]
  (let [m (cond
            (keyword? merchant)
            (-> world :merchants merchant)
            (map? merchant)
            merchant)
        l (:location m)]
    (quot
      (-> m :cash)
      (-> world :cities l :prices commodity))))

(defn augment-plan
  "Augment this `plan` constructed in this `world` for this `merchant` with
  the `:quantity` of goods which should be bought and the `:expected-profit`
  of the trade.

  Returns the augmented plan."
  [merchant world plan]
  (let [c (:commodity plan)
        o (:origin plan)
        q (min
            (or
              (-> world :cities o :stock c)
              0)
            (can-carry merchant world c)
            (can-afford merchant world c))
        p (* q (- (:expected-price plan) (:buy-price plan)))]
    (assoc plan :quantity q :expected-profit p)))

;; (-> default-world :cities :buckie :stock :iron)
;; (burden :fiona default-world)
;; (-> default-world :commodities :iron :weight)
;; (quot 0 10)

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
                   (plan-trade m world %))
                (filter
                  #(let [q (-> world :cities origin :stock %)]
                     (and (number? q) (> q 0)))
                  (keys available)))]
    (if
      (not (empty? plans))
      (first
        (sort-by
          #(- 0 (:dist-to-home %))
          (filter
            (make-target-filter
              [[:expected-profit
                (apply max (filter number? (map :expected-profit plans)))]])
            plans))))))

(defn add-stock
  "Where `a` and `b` are both maps all of whose values are numbers, return
  a map whose keys are a union of the keys of `a` and `b` and whose values
  are the sums of their respective values."
  [a b]
  (reduce
    merge
    a
    (map
      #(hash-map % (+ (or (a %) 0) (or (b %) 0)))
      (keys b))))

(defn add-known-prices
  "Add the current prices at this `merchant`'s location in the `world`
  to a new cacke of known prices, and return it."
  [merchant world]
  (let [m (cond
            (keyword? merchant)
            (-> world :merchants merchant)
            (map? merchant)
            merchant)
        k (:known-prices m)
        l (:location m)
        d (:date world)
        p (-> world :cities l :prices)]
    (reduce
      merge
      k
      (map
        #(hash-map % (apply vector cons {:price (p %) :date d} (k %)))
        (-> world :commodities keys)))))

;;; Right, from here on in we're actually moving things in the world, so
;;; functions generally return modified partial worlds.

(defn plan-and-buy
  "Return a world like this `world`, in which this `merchant` has planned
  a new trade, and bought appropriate stock for it. If no profitable trade
  can be planned, the merchant is simply moved towards their home."
  [merchant world]
  (deep-merge
    world
    (let [m (cond
              (keyword? merchant)
              (-> world :merchants merchant)
              (map? merchant)
              merchant)
          id (:id m)
          location (:location m)
          market (-> world :cities location)
          plan (select-cargo merchant world)]
      (cond
        (not (empty? plan))
        (let
          [c (:commodity plan)
           p (* (:quantity plan) (:buy-price plan))
           q (:quantity plan)]
          (l/info "Merchant " id " bought " q " units of " c " at " location " for " p)
          {:merchants
           {id
            {:stock (add-stock (:stock m) {c q})
             :cash (- (:cash m) p)
             :known-prices (add-known-prices m world)}}
           :cities
           {location
            {:stock (assoc (:stock market) c (- (-> market :stock c) q))
             :cash (+ (:cash market) p)}}})
        ;; if no plan, then if at home stay put
        (= (:location m) (:home m))
        {}
        ;; else move towards home
        true
        (let [route (find-route world location (:home m))
              next-location (nth route 1)]
          (l/info "No trade possible at " location "; merchant " id " moves to " next-location)
          {:merchants
           {id
            {:location next-location}}})))))


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
        location (:location m)
        plan (augment-plan m world (plan-trade m world (-> m :plan :commodity)))]
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
    (if
      (>= (:cash market) stock-value)
      (do
        (l/info
          (apply str (flatten (list "Merchant " id " sells " (:stock m) " at " location " for " stock-value))))
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
  "Handle general en route movement of this `merchant` in this `world`."
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
                        (nth 1 (find-route world (:location m) (:destination plan)))
                        (:location m))]
    (l/info "Merchant " id " at " (:location m))
    (cond at-destination?
          (sell-and-buy merchant world plan)
          (nil? (:plan m))
          (plan-and-buy merchant world)
          true
          {:merchants
           {id
            {:id id
             :location next-location
             :known-prices (add-known-prices m world)}}})))


(defn run
  "Return a world like this `world`, but with each merchant moved."
  [world]
  (try
    (reduce
      deep-merge
      world
      (map
        #(try
           (move-merchant % world)
           (catch Exception any
             (l/error any "Failure while moving merchant " %)
             {}))
        (keys (:merchants world))))
    (catch Exception any
      (l/error any "Failure while moving merchants")
      world)))


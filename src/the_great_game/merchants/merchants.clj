(ns the-great-game.merchants.merchants
  "Trade planning for merchants, primarily."
  (:require [the-great-game.world.routes :refer [find-routes]]
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
                     (first
                       (find-routes (:routes world) origin %)))
         :dist-to-home (count
                         (first
                           (find-routes
                             (:routes world)
                             (:home m)
                             %)))
         )
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
    (first
      (sort-by
        #(- 0 (:dist-to-home %))
        (filter
          (make-target-filter
            [[:expected-profit
              (apply max (filter number? (map :expected-profit plans)))]])
         plans)))))


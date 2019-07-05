(ns the-great-game.merchants.merchant-utils
  "Useful functions for doing low-level things with merchants.")

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
        cargo (:stock m)]
    (reduce
      +
      0
      (map
        #(* (cargo %) (-> world :commodities % :weight))
        (keys cargo)))))


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
      (:cash m)
      (-> world :cities l :prices commodity))))

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

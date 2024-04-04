(ns cc.journeyman.the-great-game.merchants.markets
  "Adjusting quantities and prices in markets."
  (:require [taoensso.timbre :as l :refer [info error]]
            [cc.journeyman.the-great-game.utils :refer [deep-merge]]))

(defn new-price
  "If `stock` is greater than the maximum of `supply` and `demand`, then
  there is surplus and `old` price is too high, so shold be reduced. If
  lower, then it is too low and should be increased."
  [old stock supply demand]
  (let
    [delta (dec' (/ (max supply demand 1) (max stock 1)))
     scaled (/ delta 100)]
    (+ old scaled)))


(defn adjust-quantity-and-price
  "Adjust the quantity of this `commodity` currently in stock in this `city`
  of this `world`. Return a fragmentary world which can be deep-merged into
  this world."
  [world city commodity]
  (let [c (cond
            (keyword? city) (-> world :cities city)
            (map? city) city)
        id (:id c)
        p (or (-> c :prices commodity) 0)
        d (or (-> c :demands commodity) 0)
        st (or (-> c :stock commodity) 0)
        su (or (-> c :supplies commodity) 0)
        decrement (min st d)
        increment (cond
                    ;; if we've two turns' production of this commodity in
                    ;; stock, halt production
                    (> st (* su 2))
                    0
                    ;; if it is profitable to produce this commodity, the
                    ;; craftspeople of the city will do so.
                    (> p 1) su
                    ;; otherwise, if there isn't a turn's production in
                    ;; stock, top up the stock, so that there's something for
                    ;; incoming merchants to buy
                    (> su st)
                    (- su st)
                    :else
                    0)
        n (new-price p st su d)]
    (if
      (not= p n)
      (l/info "Price of" commodity "at" id "has changed from" (float p) "to" (float n)))
    {:cities {id
              {:stock
               {commodity (+ (- st decrement) increment)}
               :prices
               {commodity n}}}}))


(defn update-markets
  "Return a world like this `world`, with quantities and prices in markets
  updated to reflect supply and demand. If `city` or `city` and `commodity`
  are specified, return a fragmentary world with only the changes for that
  `city` (and `commodity` if specified) populated."
  ([world]
   (reduce
     deep-merge
     world
     (map
       #(update-markets world %)
       (keys (:cities world)))))
  ([world city]
   (reduce
     deep-merge
     {}
     (map #(update-markets world city %)
          (keys (:commodities world)))))
  ([world city commodity]
    (adjust-quantity-and-price world city commodity)))


(defn run
  "Return a world like this `world`, with quantities and prices in markets
  updated to reflect supply and demand."
  [world]
  (update-markets world))


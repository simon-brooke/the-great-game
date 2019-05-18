(ns the-great-game.merchants.merchants
  "Trade planning for merchants, primarily."
  (:require [taoensso.timbre :as l :refer [info error spy]]
            [the-great-game.utils :refer [deep-merge]]
            [the-great-game.merchants.strategies.simple :refer [move-merchant]]))


(defn run
  "Return a partial world based on this `world`, but with each merchant moved."
  [world]
  (try
    (reduce
      deep-merge
      world
      (map
        #(try
           (let [move-fn (or
                           (-> world :merchants % :move-fn)
                           move-merchant)]
             (apply move-fn (list % world)))
           (catch Exception any
             (l/error any "Failure while moving merchant " %)
             {}))
        (keys (:merchants world))))
    (catch Exception any
      (l/error any "Failure while moving merchants")
      world)))


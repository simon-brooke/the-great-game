(ns the-great-game.utils)


(defn cyclic?
  "True if two or more elements of `route` are identical"
  [route]
  (not (= (count route)(count (set route)))))

(defn deep-merge
  "Recursively merges maps. Stolen from
  https://dnaeon.github.io/recursively-merging-maps-in-clojure/"
  [& maps]
  (letfn [(m [& xs]
             (if (some #(and (map? %) (not (record? %))) xs)
               (apply merge-with m xs)
               (last xs)))]
    (reduce m maps)))

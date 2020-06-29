(ns the-great-game.utils)

(defn cyclic?
  "True if two or more elements of `route` are identical"
  [route]
  (not= (count route)(count (set route))))

(defn deep-merge
  "Recursively merges maps. Stolen from
  https://dnaeon.github.io/recursively-merging-maps-in-clojure/"
  [& maps]
  (letfn [(m [& xs]
             (if (some #(and (map? %) (not (record? %))) xs)
               (apply merge-with m xs)
               (last xs)))]
    (reduce m maps)))

(defn make-target-filter
  "Construct a filter which, when applied to a list of maps,
  will pass those which match these `targets`, where each target
  is a tuple [key value]."
  ;; TODO: this would probably be more elegant as a macro
  [targets]
  (eval
    (list
      'fn
      (vector 'm)
      (cons
        'and
        (map
          #(list
             '=
             (list (first %) 'm)
             (nth % 1))
          targets)))))

(defn value-or-default
  "Return the value of this key `k` in this map `m`, or this `dflt` value if
  there is none."
  [m k dflt]
  (or (when (map? m) (m k)) dflt))

;; (value-or-default {:x 0 :y 0 :altitude 7} :altitude 8)
;; (value-or-default {:x 0 :y 0 :altitude 7} :alt 8)
;; (value-or-default nil :altitude 8)

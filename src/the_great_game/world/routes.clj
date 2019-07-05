(ns the-great-game.world.routes
  "Conceptual (plan level) routes, represented as tuples of location ids."
  (:require [the-great-game.utils :refer [cyclic?]]))

(defn find-routes
  "Find routes from among these `routes` from `from`; if `to` is supplied,
  to `to`, by breadth-first search."
  ([routes from]
   (map
     (fn [to] (cons from to))
     (remove
       empty?
       (map
         (fn [route]
           (remove
             #(= from %)
             (if (some #(= % from) route) route)))
         routes))))
  ([routes from to]
   (let [steps (find-routes routes from)
         found (filter
                 (fn [step] (if (some #(= to %) step) step))
                 steps)]
     (if
       (empty? found)
       (find-routes routes from to steps)
       found)))
  ([routes from to steps]
   (if
     (not (empty? steps))
     (let [paths (remove
                   cyclic?
                   (mapcat
                       (fn [path]
                         (map
                           (fn [x] (concat path (rest x)))
                           (find-routes routes (last path))))
                       steps))
           found (filter
                   #(= (last %) to) paths)]
       (if
         (empty? found)
         (find-routes routes from to paths)
         found)))))

(defn find-route
  "Find a single route from `from` to `to` in this `world-or-routes`, which
  may be either a world as defined in [[the-great-game.world.world]] or else
  a sequence of tuples of keywords."
  [world-or-routes from to]
  (first
    (find-routes
      (or (:routes world-or-routes) world-or-routes)
      from
      to)))

(ns cc.journeyman.the-great-game.world.location
  "Functions dealing with location in the world."
  (:require [clojure.math.numeric-tower :refer [expt sqrt]]))

;;   A 'location' value is a list comprising at most the x/y coordinate location
;;   and the ids of the settlement and region (possibly hierarchically) that contain
;;   the location. If the x/y is not local to the home of the receiving agent, they
;;   won't remember it and won't pass it on; if any of the ids are not interesting
;;   So location information will degrade progressively as the item is passed along.

;;   It is assumed that the `:home` of a character is a location in this sense.

(defn get-coords
  "Return the coordinates in the game world of `location`, which may be
  1. A coordinate pair in the format {:x 5 :y 32};
  2. A location, as discussed above;
  3. Any other gameworld object, having a `:location` property whose value
    is one of the above."
  [location]
  (cond
    (empty? location) nil
    (map? location)
    (cond
      (and (number? (:x location)) (number? (:y location)))
      location
      (:location location)
      (:location location))
    :else
    (get-coords (first (remove keyword? location)))))

(defn distance-between
  [location-1 location-2]
  (let [c1 (get-coords location-1)
        c2 (get-coords location-2)]
    (when
      (and c1 c2)
      (sqrt (+ (expt (- (:x c1) (:x c2)) 2) (expt (- (:y c1) (:y c2)) 2))))))

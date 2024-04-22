(ns cc.journeyman.the-great-game.proving.sketches
  "Code that's useful for exploring, but probably not part of the final 
   product, and if it is, will end up somewhere else."
  (:require [wherefore-art-thou.core :refer [*genders*]]))


(defn happy-cell?
  "True if all NPCs at `c` (assumed to be a MicroWorld-style cell) are of
   a happy disposition."
  [c]
  (when (:npcs c)
    (every? #(> (:disposition %) 2) (:npcs c))))

(defn couple-cell?
  [c]
  (let [npcs (:npcs c)]
    (when
     (every? pos?
             (map (fn [g]
                    (count (filter #(and (= g (:gender %)) 
                                         (#{:ancestor :citizen} (:goal %)) 
                                         (pos? (:disposition %))) npcs)))
                  (keys *genders*)))
      c)))
(ns cc.journeyman.the-great-game.proving.core
  "Phase one of '[Baking the World](Bakine-the-world.html#phase-one-proving-the-procedural-world)'"
  (:require [mw-engine.core :refer [run-world]]
            [mw-engine.drainage :refer [run-drainage]]
            ;;[mw-engine.flow :refer []]
            [mw-engine.heightmap :refer [apply-heightmap]]
            [mw-engine.utils :refer [map-world]]
            [mw-parser.declarative :refer [compile]]
            [taoensso.timbre :refer [info]]
            [wherefore-art-thou.core :refer [*genders* generate]]))

(defn get-drainage-map
  "Given this `height-map` (a monochrome raster) and optionally this 
   `rainfall-map` (also a monochrome raster), return a drainage map 
   (a microworld style world tagged with drainage data)."
  ([height-map]
   (run-drainage (apply-heightmap height-map)))
  ([height-map _rainfall-map]
   ;; TODO: currently I'm ignoring the rainfall map and relying on 
   ;; `rain-world` in `mw-engine.drainage`, but I should not do so.
   (get-drainage-map height-map)))

(defn get-biome-map
  "Given this `height-map` (a monochrome raster) and optionally this 
   `rainfall-map` (also a monochrome raster), return a biome map (a 
   microworld style world tagged with vegetation, etc, data). "
  ([height-map]
   (let [drained-world (get-drainage-map height-map)]
     (run-world drained-world (compile (slurp "resources/baking/biome-rules.txt")) (count drained-world))))
  ([height-map _rainfall-map]
   (get-biome-map height-map)))

(def ^:dynamic *life-goals*
  "TODO: This definitely doesn't belong here, and will be moved."
  (into []
        (flatten
         (map #(repeat %2 %1)
              ;; goals
              [:ancestor :citizen :climber :conqueror :explorer :hoarder :master]
              ;; relative frequency of these goals
              [10        10       8        5          3         5        8]))))

(defn- create-npc
  ;; TODO: this function needs not only to create a fully formed NPC, but 
  ;; persist them in the database being built. This is just a sketch. 
  [prototype]
  (let [g (or (:gender prototype) (rand-nth (keys *genders*)))
        p (generate g)]
    (dissoc (merge {:age (+ 18 (rand-int 18))
                    :disposition (- (rand-int 9) 4) ;; -4: surly to +4 sunny
                    :gender g
                    :goal (rand-nth *life-goals*)
                    :family-name (generate)
                    :occupation :vagrant
                    :personal-name p} prototype)
            ;; it's useful to have the world available to the create function,
            ;; but we don't want to return it in the results because clutter.
            :world)))

(defn- populate-npcs
  [prototype]
  (let [family (generate)]
    (into [] (map #(create-npc (assoc prototype :family-name family :occupation %))
                  (concat [:settler] (repeat 3 (:occupation prototype)))))))

(defn populate-cell
  [world cell]
  ;; (info (format "populate-cell: w is %s; cell is %s" (type world) cell))
  (let [npcs (case (:state cell)
               :camp (populate-npcs {:world world :cell cell :occupation :nomad})
               :house (populate-npcs {:world world :cell cell :occupation :peasant})
               :inn (populate-npcs {:world world :cell cell :occupation :innkeeper})
               ;; else
               nil)]
    (if npcs (assoc cell :npcs npcs)
        cell)))

(defn populate-world
  "Given this `biome-map` (as returned by `get-biome-map`), populate a world
   (probably some form of database) and return a structure which allows that
   database o be interrogated."
  [biome-map]
  (let [world (run-world biome-map (compile (slurp "resources/baking/settlement-rules.txt")) (count biome-map))
        with-npcs (map-world world (vary-meta (fn [w c] (populate-cell w c)) assoc :rule-type :ad-hoc))]
    ;; right, with that settled world, I'm going to put one herdsman with 
    ;; five animals (either all sheep, all cattle, all goats, all horses or 
    ;; all camels on each cell with state camp, and one settler; one farmer 
    ;; on each cell with state farm, and one settler; one innkeeper on each 
    ;; cell with state inn. Given that our cells are currently one kilometer 
    ;; squares (i.e. 100 hectares) the 'inn' cell will be sufficient to 
    ;; start a village, and the 'farm' cells will ultimately support about 
    ;; five farming households.

    ;; Settlers should move around, forming roads

    ;; what I return at the end of this is a structure which contains keys 
    ;; to a database I've stored all the NPCs in, and a (vector) roadmap of 
    ;; all the roads that have been created, and a (vector) drainage map.

    {:world with-npcs
     :roadmap []}))

(defn get-road-map
  [populated-world])

(defn prove
  "Given this `height-map` (a monochrome raster) and optionally this 
   `rainfall-map` (also a monochrome raster), return a populated world."
  ([height-map rainfall-map]
   (let [drainage-map (get-drainage-map height-map)
         biome-map (get-biome-map height-map rainfall-map)
         populated-world (populate-world biome-map)]
     {:height-map height-map
      :drainage-map drainage-map
      :populated-world populated-world
      :road-map (get-road-map populated-world)})))


(ns cc.journeyman.the-great-game.proving.core
  "Phase one of '[Baking the World](Bakine-the-world.html#phase-one-proving-the-procedural-world)'"
  (:require [mw-engine.core :refer [run-world]]
            [mw-engine.drainage :refer [run-drainage]]
            ;;[mw-engine.flow :refer []]
            [mw-engine.heightmap :refer [apply-heightmap]]
            [mw-parser.declarative :refer [compile]]))

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

(defn populate-world
  "Given this `biome-map` (as returned by `get-biome-map`), populate a world
   (probably some form of database) and return a structure which allows that
   database o be interrogated."
  [biome-map]
  (let [world (run-world biome-map (compile (slurp "resources/baking/settlement-rules.txt")) (count biome-map))]
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
    {:world world}))

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


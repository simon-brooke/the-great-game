(ns cc.journeyman.the-great-game.proving.core
  "Phase one of '[Baking the World](Bakine-the-world.html#phase-one-proving-the-procedural-world)'"
  (require [mw-engine.core :refer []]
           [mw-engine.drainage :refer []]
           [mw-engine.flow :refer []]))

(defn get-drainage-map
  "Given this `height-map` (a monochrome raster) and optionally this 
   `rainfall-map` (also a monochrome raster), return a drainage map (exact 
   format uncertain, probably still a raster)."
  ([height-map])
  ([height-map rainfall-map]))

(defn get-biome-map
  "Given this `height-map` (a monochrome raster) and optionally this 
   `rainfall-map` (also a monochrome raster), return a biome map."
  ([height-map])
  ([height-map rainfall-map]))

(defn populate-world
  "Given this `biome-map` (as returned by `get-biome-map`), populate a world
   (probably some form of database) and return a structure which allows that
   database o be interrogated."
  [biome-map drainage-map])

(defn get-road-map
  [populated-world])

(defn prove
  "Given this `height-map` (a monochrome raster) and optionally this 
   `rainfall-map` (also a monochrome raster), return a populated world."
  ([height-map rainfall-map]
   (let [drainage-map (get-drainage-map height-map)
         biome-map (get-biome-map height-map rainfall-map)
         populated-world (populate-world biome-map drainage-map)]
     {:height-map height-map
      :drainage-map drainage-map
      :populated-world populated-world
      :road-map (get-road-map populated-world)})))


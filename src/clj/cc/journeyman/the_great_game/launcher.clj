(ns cc.journeyman.the-great-game.launcher
  "Launcher for the game"
  (:require [clojure.tools.cli :refer [parse-opts]]
            [jme-clj.core :refer [add-control add-to-root app-settings cam
                                  defsimpleapp fly-cam get-height-map image
                                  image-based-height-map load-height-map
                                  load-texture material set* start
                                  terrain-lod-control terrain-quad]]) 
  (:import (com.jme3.texture Texture$WrapMode))
  (:gen-class))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;
;;;; Launcher: parses any command line options, and launches the game.
;;;;
;;;; This program is free software; you can redistribute it and/or
;;;; modify it under the terms of the GNU General Public License
;;;; as published by the Free Software Foundation; either version 2
;;;; of the License, or (at your option) any later version.
;;;;
;;;; This program is distributed in the hope that it will be useful,
;;;; but WITHOUT ANY WARRANTY; without even the implied warranty of
;;;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
;;;; GNU General Public License for more details.
;;;;
;;;; You should have received a copy of the GNU General Public License
;;;; along with this program; if not, write to the Free Software
;;;; Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
;;;; USA.
;;;;
;;;; Copyright (C) 2024 Simon Brooke
;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def cli-options
  ;; An option with a required argument
  [["-p" "--port PORT" "Port number"
    :default 80
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]
   ;; A non-idempotent option (:default is applied first)
   ["-v" nil "Verbosity level"
    :id :verbosity
    :default 0
    :update-fn inc] ; Prior to 0.4.1, you would have to use:
                   ;; :assoc-fn (fn [m k _] (update-in m [k] inc))
   ;; A boolean option defaulting to nil
   ["-h" "--help"]])

(defn init []
  (set* (fly-cam) :move-speed 50)
  (let [grass          (set* (load-texture "textures/terrain/splat/grass.jpg") :wrap Texture$WrapMode/Repeat)
        dirt           (set* (load-texture "textures/terrain/splat/dirt.jpg") :wrap Texture$WrapMode/Repeat)
        rock           (set* (load-texture "textures/terrain/splat/road.jpg") :wrap Texture$WrapMode/Repeat)
        mat            (material "Common/MatDefs/Terrain/Terrain.j3md")
        height-map-tex (load-texture "textures/terrain/splat/mountains512.png")
        height-map     (->> height-map-tex image image-based-height-map load-height-map)
        patch-size     65
        terrain        (terrain-quad "my terrain" patch-size 513 (get-height-map height-map))]
    (-> mat
        (set* :texture "Alpha" (load-texture "textures/terrain/splat/alphamap.png"))
        (set* :texture "Tex1" grass)
        (set* :float "Tex1Scale" (float 64))
        (set* :texture "Tex2" dirt)
        (set* :float "Tex2Scale" (float 32))
        (set* :texture "Tex3" rock)
        (set* :float "Tex3Scale" (float 128)))
    (-> terrain
        (set* :material mat)
        (set* :local-translation 0 -100 0)
        (set* :local-scale 2 1 2)
        (add-to-root)
        (add-control (terrain-lod-control terrain (cam))))))

(defsimpleapp app :init init)

(defn -main 
  "Launch the game."
  [& args]
  (parse-opts args cli-options)

  ;; this isn't working, not sure why not.
  ;; (.setSettings app (app-settings false :dialog-image "images/splash.png"))

  (start app))
  
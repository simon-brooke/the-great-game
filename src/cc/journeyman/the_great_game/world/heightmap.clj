(ns cc.journeyman.the-great-game.world.heightmap
  "Functions dealing with the tessellated multi-layer heightmap."
    (:require [clojure.math.numeric-tower :refer [expt sqrt]]
              [mw-engine.core :refer []]
              [mw-engine.heightmap :refer [apply-heightmap]]
              [mw-engine.utils :refer [get-cell in-bounds? map-world scale-world]]
              [cc.journeyman.the-great-game.utils :refer [value-or-default]]))

;; It's not at all clear to me yet what the workflow for getting a MicroWorld
;; map into The Great Game, and whether it passes through Walkmap to get here.
;; This file as currently written assumes it doesn't.

;; It's utterly impossible to hold a whole continent at one metre scale in
;; memory at one time. So we have to be able to regenerate high resolution
;; surfaces from much lower resolution heightmaps.
;;
;; Thus to reproduce a segment of surface at a particular level of detail,
;; we:
;; 1. load the base heightmap into a grid (see
;;    `mw-engine.heightmap/apply-heightmap`);
;; 2. scale the base hightmap to kilometre scale (see `scale-grid`);
;; 3. exerpt the portion of that that we want to reproduce (see `exerpt-grid`);
;; 4. interpolate that grid to get the resolution we require (see
;;    `interpolate-grid`);
;; 5. create an appropriate purturbation grid from the noise map(s) for the
;;    same coordinates to break up the smooth interpolation;
;; 6. sum the altitudes of the two grids.
;;
;; In production this will have to be done **very** fast!

(def ^:dynamic *base-map* "resources/maps/heightmap.png")
(def ^:dynamic *noise-map* "resources/maps/noise.png")

(defn scale-grid
  "multiply all `:x` and `:y` values in this `grid` by this `n`."
  [grid n]
  (map-world grid (fn [w c x] (assoc c :x (* (:x c) n) :y (* (:y c) n)))))



;; Each of the east-west curve and the north-south curve are of course two
;; dimensional curves; the east-west curve is in the :x/:z plane and the
;; north-south curve is in the :y/:z plane (except, perhaps unwisely,
;; we've been using :altitude to label the :z plane). We have a library
;; function `walkmap.edge/intersection2d`, but as currently written it
;; can only find intersections in :x/:y plane.
;;
;; TODO: rewrite the function so that it can use arbitrary coordinates.
;; AFTER TRYING: OK, there are too many assumptions about the way that
;; function is written to allow for easy rotation. TODO: think!

(defn interpolate-altitude
  "Return the altitude of the point at `x-offset`, `y-offset` within this
  `cell` having this `src-width`, taken from this `grid`."
  [cell grid src-width x-offset y-offset ]
  (let [c-alt (:altitude cell)
        n-alt (or (:altitude (get-cell grid (:x cell) (dec (:y cell)))) c-alt)
        w-alt (or (:altitude (get-cell grid (inc (:x cell)) (:y cell))) c-alt)
        s-alt (or (:altitude (get-cell grid (:x cell) (inc (:y cell)))) c-alt)
        e-alt (or (:altitude (get-cell grid (dec (:x cell)) (:y cell))) c-alt)]
    ;; TODO: construct two curves (arcs of circles good enough for now)
    ;; n-alt...c-alt...s-alt and e-alt...c-alt...w-alt;
    ;; then interpolate x-offset along e-alt...c-alt...w-alt and y-offset
    ;; along n-alt...c-alt...s-alt;
    ;; then return the average of the two

    0))

(defn interpolate-cell
  "Construct a grid (array of arrays) of cells each of width `target-width`
  from this `cell`, of width `src-width`, taken from this `grid`"
  [cell grid src-width target-width]
  (let [offsets (map #(* target-width %) (range (/ src-width target-width)))]
    (into
      []
      (map
        (fn [r]
          (into
            []
            (map
              (fn [c]
                (assoc cell
                  :x (+ (:x cell) c)
                  :y (+ (:y cell) r)
                  :altitude (interpolate-altitude cell grid src-width c r)))
              offsets)))
        offsets))))

(defn interpolate-grid
  "Return a grid interpolated from this `grid` of rows, cols given scaling
  from this `src-width` to this `target-width`"
  [grid src-width target-width]
  (reduce
    concat
    (into
      []
      (map
        (fn [row]
          (reduce
            (fn [g1 g2]
              (into [] (map #(into [] (concat %1 %2)) g1 g2)))
            (into [] (map #(interpolate-cell % grid src-width target-width) row))))
        grid))))

(defn excerpt-grid
  "Return that section of this `grid` where the `:x` co-ordinate of each cell
  is greater than or equal to this `x-offset`, the `:y` co-ordinate is greater
  than or equal to this `y-offset`, whose width is not greater than this
  `width`, and whose height is not greater than this `height`."
  [grid x-offset y-offset width height]
  (into
    []
    (remove
      nil?
      (map
        (fn [row]
          (when
            (and
              (>= (:y (first row)) y-offset)
              (< (:y (first row)) (+ y-offset height)))
            (into
              []
              (remove
                nil?
                (map
                  (fn [cell]
                    (when
                      (and
                        (>= (:x cell) x-offset)
                        (< (:x cell) (+ x-offset width)))
                      cell))
                  row)))))
       grid))))

(defn get-surface
  "Return, as a vector of vectors of cells represented as Clojure maps, a
  segment of surface from this `base-map` as modified by this
  `noise-map` at this `cell-size` starting at this `x-offset` and `y-offset`
  and having this `width` and `height`.

  If `base-map` and `noise-map` are not supplied, the bindings of `*base-map*`
  and `*noise-map*` will be used, respectively.

  `base-map` and `noise-map` may be passed either as strings, assumed to be
  file paths of PNG files, or as MicroWorld style world arrays. It is assumed
  that one pixel in `base-map` represents one square kilometre in the game
  world. It is assumed that `cell-size`, `x-offset`, `y-offset`, `width` and
  `height` are integer numbers of metres."
  ([cell-size x-offset y-offset width height]
   (get-surface *base-map* *noise-map* cell-size x-offset y-offset width height))
  ([base-map noise-map cell-size x-offset y-offset width height]
   (let [b (if (seq? base-map) base-map (scale-world (apply-heightmap base-map) 1000))
         n (if (seq? noise-map) noise-map (apply-heightmap noise-map))]
     (if (and (in-bounds? b x-offset y-offset)
              (in-bounds? b (+ x-offset width) (+ y-offset height)))
       b ;; actually do stuff
       (throw (Exception. "Surface out of bounds for map.")))
     )))


(ns cc.journeyman.the-great-game.world.mw
  "Functions dealing with building a great game world from a MicroWorld world."
    (:require [clojure.math.numeric-tower :refer [expt sqrt]]
              [mw-engine.core :refer []]
              [mw-engine.world :refer []]))

;; It's not at all clear to me yet what the workflow for getting a MicroWorld map into The Great Game, and whether it passes through Walkmap to get here. This file as currently written assumes it doesn't.

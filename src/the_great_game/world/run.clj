(ns the-great-game.world.run
  "Run the whole simulation"
  (:require [taoensso.timbre :as log]
            [the-great-game.gossip.gossip :as g]
            [the-great-game.merchants.merchants :as m]
            [the-great-game.merchants.markets :as k]
            [the-great-game.world.world :as w]))


(defn run
  "The pipeline to run the simulation each game day. Returns a world like
  this world, with all the various active elements updated."
  [world]
  (g/run
    (m/run
      (k/run
        (w/run world)))))

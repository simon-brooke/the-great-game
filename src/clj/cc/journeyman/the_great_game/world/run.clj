(ns cc.journeyman.the-great-game.world.run
  "Run the whole simulation"
  (:require [environ.core :refer [env]]
            [taoensso.timbre :as timbre]
            [taoensso.timbre.appenders.3rd-party.rotor :as rotor]
            [cc.journeyman.the-great-game.gossip.gossip :as g]
            [cc.journeyman.the-great-game.merchants.merchants :as m]
            [cc.journeyman.the-great-game.merchants.markets :as k]
            [cc.journeyman.the-great-game.world.world :as w]))

(defn init
  ([]
   (init {}))
  ([config]
   (timbre/merge-config!
     {:appenders
      {:rotor (rotor/rotor-appender
                {:path "the-great-game.log"
                 :max-size (* 512 1024)
                 :backlog 10})}
      :level (or
               (:log-level config)
               (if (env :dev) :debug)
               :info)})))

(defn run
  "The pipeline to run the simulation each game day. Returns a world like
  this world, with all the various active elements updated. The optional
  `date` argument, if supplied, is set as the `:date` of the returned world."
  ([world]
  (g/run
    (m/run
      (k/run
        (w/run world)))))
  ([world date]
  (g/run
    (m/run
      (k/run
        (w/run world date))))))

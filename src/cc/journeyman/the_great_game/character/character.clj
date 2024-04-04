(ns cc.journeyman.the-great-game.character.character
  "A character that can talk; either human or dragon (although very probably
   we won't do talking dragons until really well into this process). All
   characters have the news-passing abilities of a gossip, but we use `gossip`
   to mean a special character who is part of the news-passing network."
  (:require [cc.journeyman.the-great-game.gossip.gossip :refer [dialogue]]
            [cc.journeyman.the-great-game.agent.agent :refer [Agent]]))



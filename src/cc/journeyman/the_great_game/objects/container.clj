(ns cc.journeyman.the-great-game.objects.container
  (:require
    [cc.journeyman.the-great-game.objects.game-object :refer :all]))

(defprotocol ProtoContainer
  (contents
    [container]
            "Return a sequence of the contents of this `container`, or `nil` if empty.")
  (is-empty?
    [container]
    "Return `true` if this `container` is empty, else `false`."))

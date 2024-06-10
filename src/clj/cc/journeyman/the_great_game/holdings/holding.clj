(ns cc.journeyman.the-great-game.holdings.holding
  (:require [cc.journeyman.the-great-game.agent.agent :refer [ProtoAgent]]
            [cc.journeyman.the-great-game.objects.container :refer [ProtoContainer]]
            [cc.journeyman.the-great-game.objects.game-object :refer [ProtoObject]]
;;            [cc.journeyman.the-great-game.location.location :refer [OrientedLocation]]
            [cc.journeyman.the-great-game.world.routes :refer []]))

;;; A holding is a polygonal area of the map which does not
;;; intersect with any other holding, or with any road or water feature. For 
;;; the time being we'll make the 
;;; simplifying assumption that every holding is a rectangular strip, and that
;;; 'urban' holdings are of a reasonably standard width (see Viking-period 
;;; York) and length. Rural holdings (farms, ?wood lots) may be much larger.

(defprotocol ProtoHolding
  (frontage
    [holding]
    "Returns a sequence of two locations representing the edge of the polygon
    which defines this holding which is considered to be the front.")
  (building-origin
    [holding]
    "Returns an oriented location - normally the right hand end of the 
    frontage, for an urban holding - from which buildings on the holding
    should be built."))

(defrecord Holding [perimeter holder]
  ;; Perimeter should be a list of locations in exactly the same sense as a
  ;; route in `cc.journeyman.the-great-game.world.routes`. Some sanity checking
  ;; is needed to ensure this!
  ProtoContainer
  ProtoHolding
  (frontage 
    [holding]
   "TODO: this is WRONG, but will work for now. The frontage should
    be the side of the perimeter nearest to the nearest existing 
    route."
    [(first (perimeter holding)) (nth (perimeter holding) 1)])
  (building-origin 
   [holding]
   "TODO: again this is WRONG. The default building origin for rectangular 
    buildings should be the right hand end of the frontage when viewed
    from outside the holding. But that's not general; celtic-style circular
    buildings should normally be in the centre of their holdings. So probably
    building-origin becomes a method of building-family rather than of holding."
   (first (frontage holding)))
  ProtoObject)

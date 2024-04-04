(ns cc.journeyman.the-great-game.location.location)

;;; There's probably conflict between this sense of a reified location and
;;; the simpler sense of a location described in 
;;; `cc.journeyman.the-great-game.world.location`, q.v. This needs to
;;; be resolved!

(defprotocol ProtoLocation
  (easting [location]
   "Return the easting of this location")
  (northing [location] "Return the northing of this location")
  (altitude [location]
            "Return the absolute altitude of this location, which may be
             different from the terrain height at this location, if, for
             example, the location is underground or on an upper floor.")
  (terrain-altitude [location]
                    "Return the 'ground level' (altitude of the terrain)
                     at this location given this world. TODO: possibly
                     terrain-altitude should be a method of the world.")
  (settlement [location]
              "Return the settlement record of the settlement in this world
               within whose parish polygon this location exists, or if none
               whose centre (inn location) is closest to this location"))


(defrecord Location [^Double easting ^Double northing ^Double altitude world]
  ProtoLocation
  (easting [l] (:easting l))
  (northing [l] (:northing l))
  (altitude [l] (:altitude l))
  (terrain-altitude [l] 0.0) ;; TODO
  (settlement [l] :tchahua))

(defrecord OrientedLocation
  ;; "Identical to a Location except having, additionally, an orientation"
           [^Double easting ^Double northing ^Double altitude ^Double orientation world]
  ProtoLocation
  (easting [l] (:easting l))
  (northing [l] (:northing l))
  (altitude [l] (:altitude l))
  (terrain-altitude [l] 0.0) ;; TODO
  (settlement [l] :tchahua)) ;; TODO

 ;; (.settlement (OrientedLocation. 123.45 543.76 12.34 0.00 {}))


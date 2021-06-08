(ns cc.journeyman.the-great-game.buildings.rectangular
  (:require [cc.journeyman.the-great-game.holdings.holding :refer [ProtoHolding]]
            [cc.journeyman.the-great-game.location.location :refer [ProtoLocation]]
            )
  (:import [org.apache.commons.math3.random MersenneTwister]
           ))

;;; Right, the idea behind this namespace is many fold.
;;;
;;; 1. To establish the broad principle of genetic buildings, by creating a
;;;    function which reproducibly creates reproducible buildings at specified
;;;    locations, such that different buildings are credibly varied but a
;;;    building at a specified location is always (modulo economic change) the
;;;    same.
;;; 2. Create good rectangular buildings, and investigate whether a single 
;;;    function can be used to create buildings of more than one family (e.g.
;;;    can it produce flat roofed, north African style, mud brick houses as
;;;    well as pitch roofed, half timbered northern European houses?)
;;; 3. Establish whether, in my current state of fairly severe mental illness,
;;;    I can actually produce any usable code at all.
;;;
;;; ## Key factors in the creation of a building
;;;
;;; ### Holding
;;;
;;; Every building is on a holding, and, indeed, what I mean by 'building' here
;;; may well turn out to be 'the collection of all the permanent structures on
;;; a holding. A holding is a polygonal area of the map which does not 
;;; intersect with any other holding, but for the time being we'll make the 
;;; simplifying assumption that every holding is a rectangular strip, and that
;;; 'urban' holdings are of a reasonably standard width (see Viking-period 
;;; York) and length. Rural holdings (farms, ?wood lots) may be much larger.
;;;
;;; ### Terrain
;;;
;;; A building is made of the stuff of the place. In a forest, buildings will 
;;; tend to be wooden; in a terrain with rocky outcrops -- normally found on 
;;; steep slopes -- stone. On the flat lands where there's river mud, of brick,
;;; cob, or wattle and daub. So to build a building we need to know the 
;;; terrain. Terrain can be inferred from location but in practice this will 
;;; be computationally expensive, so we'll pass terrain in as an argument to
;;; the build function.
;;;
;;; For the time being we'll pass it in simply as a keyword from a defined set
;;; of keywords; later it may be a more sophisticated data structure.
;;;
;;; ### Culture
;;;
;;; People of different cultures build distinctively different buildings, even
;;; when using the same materials. So, in our world, a Japanese wooden house 
;;; looks quite different from an Anglo Saxon stave house which looks quite 
;;; different from a Canadian log cabin, even though the materials are much the
;;; same and the tools available to build with are not much different.
;;;
;;; Culture can affect not just the overall shape of a building but also its 
;;; finish and surface detail. For example, in many places in England, stone
;;; buildings are typically left bare; in rural Scotland, typically painted 
;;; white or in pastel shades; in Ireland, often quite vivid colours.
;;;
;;; People may also show religious or cultural symbols on their buildings.
;;; 
;;; For all these reasons, we need to know the culture of the occupant when
;;; creating a building. Again, this will initially be passed in as a keyword.
;;;
;;; ### Craft
;;;
;;; People in the game world have a craft, and some crafts will require 
;;; different features in the building. In the broadly late-bronze-age-to
;;; medieval period within which the game is set, residence and  workplace
;;; are for most people pretty much the same.
;;;
;;; So a baker needs an oven, a smith a forge, and so on. All crafts who do
;;; some degree retail trade will want a shop front as part of the ground 
;;; floor of their dwelling. Merchants and bankers will probably have houses
;;; that are a bit more showy than others.
;;;
;;; Whether the 'genetic buildings' idea will ever really produce suitable
;;; buildings for aristons I don't know; it seems more likely that significant
;;; strongholds (of which there will be relatively few) should all be hand
;;; modelled rather than procedurally generated.

(def ^:dynamic *terrain-types* 
  "Types of terrain which affect building families. TODO: This is a placeholder;
   a more sophisticated model will be needed."
  #{:arable :arid :forest :plateau :upland})

(def ^:dynamic *cultures*
  "Cultures which affect building families. TODO: placeholder"
  #{:ariston :coastal :steppe-clans :western-clans :wild-herd})

(def ^:dynamic *crafts*
  "Crafts which affect building types in the game. See 
   `Populating a game world`. TODO: placeholder"
  #{:baker :banker :butcher :chancellor :innkeeper :lawyer :magus :merchant :miller :priest :scholar :smith :weaver})

(def ^:dynamic *building-families* 
  {:pitched-rectangular {:terrains #{:arable :forest :upland}
                         :crafts *crafts*
                         :cultures #{:coastal :western-clans}
                         :modules []}
   :flatroof-rectangular {:terrains #{:arid :plateau}
                          :crafts *crafts*
                          :cultures #{:coastal}
                          :modules []}})

;; TODO: So, modules need to contain
;;
;; 1. Ground floor modules, having external doors;
;; 2. Craft modules -- workshops -- which will normally be ground floor (except
;; weavers) and may have the constraint that no upper floor module can cover them;
;; 3. Upper floor modules, having NO external doors (but linking internal doors);
;; 4. Roof modules
;; 
;; There also needs to be an undercroft or platform module, such that the area of 
;; the top of the platform is identical with the footprint of the building, and 
;; the altitude of the top of the platform is equal to the altitude of the 
;; terrain at the heighest corner of the building; so that the actual 
;; building doesn't float in the air, and also so that none of the doors or windows
;; are partly underground.
;;
;; Each module needs to wrap an actual 3d model created in Blender or whatever, 
;; and have a list of optional textures with which that model can be rendered. 
;; So an upper floor bedroom module might have the following renders:
;;
;; 1. Bare masonry - constrained to upland or plateau terrain, and to coastal culture
;; 2. Painted masonry - constrained to upland or plateau terrain, and to coastal culture
;; 3. Half-timbered - not available on plateau terrain
;; 4. Weatherboarded - constrained to forest terrain
;; 5. Brick - constrained to arable or arid terrain
;;
;; of course these are only examples, and also, it's entirely possible to have
;; for example multiple different weatherboard renders for the same module. 
;; There needs to be a way of rendering what can be built above what: for
;; example, you can't have a masonry clad module over a half timbered one, 
;; but you can have a half-timbered one over a masonry one

(defn building-family
  "A building family is essentially a collection of models of building modules
   which can be assembled to create buildings of a particular structural and
   architectural style."
  [terrain culture craft gene]
  (let [candidates (filter #(and
                             ((:terrains %) terrain)
                             ((:crafts %) craft)
                             ((:cultures %) culture))
                           (vals *building-families*))]
    (nth candidates (mod (Math/abs (.nextInt gene)) (count candidates)))))

(building-family :arable :coastal :baker (MersenneTwister. 5))

(defn build! 
  "Builds a building, and returns a data structure which represents it. In 
   building the building, it adds a model of the building to the representation
   of the world, so it does have a side effect."
  [holding terrain culture craft size]
  (if (satisfies? ProtoHolding holding)
  (let [location (.building-origin holding)
        gene (MersenneTwister. (int (+ (* (.easting location) 1000000) (.northing location))))
        family (building-family terrain culture craft gene)]
  (if 
   (and (instance? ProtoLocation location) (:orientation location))
    :stuff
    :nonsense
    ))
    :froboz))

;; (def ol (cc.journeyman.the-great-game.location.location/OrientedLocation. 123.45 543.76 12.34 0.00 {}))


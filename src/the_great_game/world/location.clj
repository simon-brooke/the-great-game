(ns the-great-game.world.location
  "Functions dealing with location in the world.")

;;   A 'location' value is a list comprising at most the x/y coordinate location
;;   and the ids of the settlement and region (possibly hierarchically) that contain
;;   the location. If the x/y is not local to the home of the receiving agent, they
;;   won't remember it and won't pass it on; if any of the ids are not interesting
;;   So location information will degrade progressively as the item is passed along.

;;   It is assumed that the `:home` of a character is a location in this sense.

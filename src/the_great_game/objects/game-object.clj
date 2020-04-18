(ns the-great-game.objects.game-object
  "Anything at all in the game world")

(defprotocol ProtoObject
  "An object in the world"
  (id [object] "Returns the unique id of this object.")
  (reify-object
    [object]
    "Adds this `object` to the global object list. If the `object` has a
    non-nil value for its `id` method, keys it to that id - **but** if the
    id value is already in use, throws a hard exception. Returns the id to
    which the object is keyed in the global object list."))

(defrecord GameObject
  [id]
  ;; "An object in the world"
  ProtoObject
  (id [_] id)
  (reify-object [object] "TODO: doesn't work yet"))

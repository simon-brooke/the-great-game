(ns the-great-game.agent.agent
  "Anything in the game world with agency"
  (:require [the-great-game.objects.game-object :refer [ProtoObject]]
            [the-great-game.objects.container :refer [ProtoContainer]]))

;;  hierarchy of needs probably gets implemented here
;;  I'm probably going to want to defprotocol stuff, to define the hierarchy
;;  of things in the gameworld; either that or drop to Java, wich I'd rather not do.

(defprotocol ProtoAgent
  "An object which can act in the world"
  (act
    [actor world circle]
       "Allow `actor` to do something in this `world`, in the context of this
       `circle`; return the new state of the actor if something was done, `nil`
       if nothing was done. Circle is expected to be one of

       * `:active` - actors within visual/audible range of the player
         character;
       * `:pending` - actors not in the active circle, but sufficiently close
         to it that they may enter the active circle within a short period;
       * `:background` - actors who are active in the background in order to
         handle trade, news, et cetera;
       * `other` - actors who are not members of any other circle, although
         I'm not clear whether it would ever be appropriate to invoke an
         `act` method on them.

       The `act` method *must not* have side effects; it must *only* return a
       new state. If the actor's intention is to seek to change the state of
       something else in the game world, it must add a representation of that
       intention to the sequence which will be returned by its
       `pending-intentions` method.")
  (pending-intentions
    [actor]
    "Returns a sequence of effects an actor intends, as a consequence of
    acting. The encoding of these is not yet defined."))

;; (defrecord Agent
;;   "A default agent."
;;   ProtoObject
;;   ProtoContainer
;;   ProtoAgent
;; )

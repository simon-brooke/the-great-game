(ns cc.journeyman.the-great-game.agent.agent
  "Anything in the game world with agency; primarily but not exclusively
   characters."
  (:require [cc.journeyman.the-great-game.objects.game-object :refer [ProtoObject]]
            [cc.journeyman.the-great-game.objects.container :refer [ProtoContainer contents is-empty?]]))

;;; hierarchy of needs probably gets implemented here
;;; I'm probably going to want to defprotocol stuff, to define the hierarchy
;;; of things in the gameworld; either that or drop to Java, wich I'd rather not do.

;;; attitudes - liking/disliking, attraction/repulsion, amity/hostility, trust/fear
;;; also need to live at this layer, even though dynamic change in attitudes belongs
;;; in the character layer.

(defprotocol ProtoAgent
  "An object which can act in the world"
  (act
    [actor world circle]
    "Allow `actor` to do something in this `world`, in the context of this
       `circle`; return the new state of the actor if something was done, `nil`
       if nothing was done. `Circle` is expected to be one of

       * `:active` - actors within visual/audible range of the player
         character;
       * `:pending` - actors not in the active circle, but sufficiently close
         to it that they may enter the active circle within a short period;
       * `:background` - actors who are active in the background in order to
         handle trade, news, et cetera;
       * `:other` - actors who are not members of any other circle.

       The `act` method *must not* have side effects; it must *only* return a
       new state. If the actor's intention is to seek to change the state of
       something else in the game world, it must add a representation of that
       intention to the sequence which will be returned by its
       `pending-intentions` method.")
  (hungry? [actor world circle] "True if this actor is hungry and has no 
                                 immediate access to food.")
  (pending-intentions
    [actor]
    "Returns a sequence of effects an actor intends, as a consequence of
     acting.")
  (pending-scheduled-action? [actor world circle]
    "True if there is a plan in this `actor`'s 
                              schedule which should be activated now. 
                              NOTE THAT plans in the `daily` schedule are
                              NOT activated when in circles `:background`
                              or `:other`")
  (plan-fight-or-flight [actor world circle]
    "Return a plan to resolve any active threat to this 
                         `actor` in this `world`.")
  (plan-find-food [actor workd circle]
    "Return a plan to find this `actor` food in this `world`.")
  (plan-find-rest [actor workd circle]
    "Return a plan to find this `actor` a safe place to rest, or
                  if in one, to actually rest, in this `world`.")
  (plan-goal [actor world circle] "Return a plan to advance this `actor` 
                                   towards their personal objective, in this
                                   world, or `nil` for default actors with no
                                   objective.")
  (plan-scheduled-action [actor workd circle]
    "Return a plan taken from the schedule of this actor
                          for the current date and time, if any, else `nil`.")
  (schedule [actor] "Return a map of scheduled actions for this `actor`.
                     TODO: work out the detailed format!")
  (threatened? [actor world circle] "True if this `actor` is threatened in this 
                                     `world`.")
  (tired? [actor world circle] "True if this `actor` needs rest."))

(defrecord Agent
  ;; "A default agent."
           [name craft home culture]
  ;; ProtoObject
  ;; ProtoContainer

  ;; (contents
  ;;   "The `contents` of an actor are the contents of their pack(s) (if any), where 
  ;;    a pack may be any sort of bag or container which the actor could reasonably 
  ;;    be carrying."
  ;;   [actor]
  ;;   (flatten
  ;;    (map contents (filter #(satisfies? ProtoContainer %)
  ;;                          (:burden actor)))))

  ;; (is-empty?
  ;;   [actor]
  ;;   (empty? (:burden actor)))

  ;; ProtoAgent

  ;; (act
  ;;   “Return a map in which :world is bound to a world like this `world `except that this `actor `has acted in it; and `:actor` is bound to an 
  ;;   actor like this `actor `except modified by the consequences of the action.
  ;;   ‘Circle’ indicates which activation circle the actor is in.

  ;;   Note that this implies that a `plan `is a function of three arguments, an
  ;;   actor, a world. and a circle, and returns exactly the sort of map this
  ;;   function returns.”
  ;;   [actor world circle]
  ;;   (let [urgent (case circle
  ;;                  :other (cond
  ;;                           (pending-scheduled-action? actor world circle)
  ;;                           (plan-scheduled-action actor world circle))
  ;;                  :background (cond
  ;;                                (threatened? actor world circle)
  ;;                                (plan-fight-or-flight actor world circle)
  ;;                                (pending-scheduled-action? actor world circle)
  ;;                                (plan-scheduled-action actor world circle))
  ;;                  ;; else
  ;;                  (cond
  ;;                    (threatened? actor world circle)
  ;;                    (plan-fight-or-flight actor world circle)
  ;;                    (hungry? actor world circle)
  ;;                    (plan-find-food actor world circle)
  ;;                    (tired? actor world circle)
  ;;                    (plan-find-rest actor world circle)
  ;;                    (pending-scheduled-action? actor world circle)
  ;;                    (plan-scheduled-action actor world circle)))
  ;;         next-action (cond urgent urgent
  ;;                           (empty? (:plans actor))
  ;;                           (plan-goal actor world circle)
  ;;                           :else (first (:plans actor)))
  ;;         consequences (apply next-action (list actor world circle))]
  ;;     ;; we return consequences of the action, except that, if the action
  ;;     ;; was on the plans of the actor, we remove it.
  ;;     (if-not (= next-action (first (:plans actor)))
  ;;       consequences
  ;;       (assoc consequences :actor
  ;;              (assoc (:actor consequences) :plans
  ;;                     (rest (-> consequences :actor :plans)))))))
                      
                     )

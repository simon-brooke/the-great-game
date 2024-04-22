OK, the basic idea is this

Everything (every game object, including the world) is a map.

Every object as an :id property; every :id property is distinct.

There is a master map - the `oblist` which contains every object, keyed by its :id.

Every object has a :run function, which returns either a new copy of itself or nil, and does not have side effects.

Every object has a :location function, which takes one argument, the object, and returns its location as a  coordinate pair (or coordinate triple, probably) (this may involve fetching the location from the container in which it is contained, which implies that a contained object must hold a handle to its container).

Every collection of things in the world is represented as a list of :id values, by which the actual objects can be fetched from the `oblist`.

## Circles

Among those collections are the circles. The circles include, at minimum

1. Those objects in audible/visual range of the player; these have their run method invoked avery game loop. Weather, is always in this circle. The sun and moon are in this circle from shortly becore they rise to shortly after they set.
2. Those objects which might come into audible/visual range within a short period; these have their run method invoked every N game loops, where N is probably variable depending on overall system load
3. Those objects (actors) which are necessary to maintain the gossip system, etc. These should each have their run method invoked once per game day, but that is done by invoking the run method of a share of them each game loop.

So `run` takes three arguments - the object, the world and the circle; and returns nil if it makes no change, or a new copy of itself; and probably each of the main functions that run calls have the same behaviour. So, for example, a hierarchy of needs can be represented by


    (defn run [character world circle]
      (first
        (handle-immediate-threat character world circle) ;; if being attacked, deal with it
        (complete-current-action character world circle) ;; otherwise, continue the current
                                                         ;; short-term unless completed
        (handle-thirst character world circle)           ;; perhaps adjust tactical plan to find water
        (handle-hunger character world circle)           ;; perhaps adjust tactical plan to find food
        (handle-fatigue character world circle)          ;; perhaps rest if safe to do so
        (advance-current-plan character world circle)    ;; select next step of current strategic plan
        (select-next-plan character world circle)        ;; plan new strategic objective
        (return-home character world circle)))           ;; if no other strategic objective, return
                                                         ;; to home location


Atoms? Background threads?

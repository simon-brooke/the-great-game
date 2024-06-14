# Sketch for an algorithm for a pure-functional physics engine

What a physics engine needs to do is, at each step

1. Take a collection of bodies;
2. For every pair of bodies, compute the change in velocity of each consequent on their interaction;
3. For each body, merge the changes in velocity found in step two into one change in velocity;
4. For each body, update the position of the body.

Now, in practice, for simulations in a terrestrial environment, the largest interaction any of the other bodies constantly has is that with the planet in the form of gravity; and, gravity of the planet is so much greater than that of any other body in the environment that the gravitational attraction of the other bodies to each other can be ignored; and finally, the difference in mass between the planet and any other of the bodies is such that changes to the velocity of the planet can effectively ignored. So we can modify that to

1. Take a collection of bodies excepting the planet;
2. For each pair of bodies *including the planet*, compute as vectors changes in velocity consequent on collisions between them (but the change in velocity of the planet will always be deemed to be zero);
3. For each body, compute as a vector the change in velocity consequent on the gravitational attraction of the planet;
4. For each body, compute as a vector the change in velocity consequent on the friction of the medium through which it is moving;
5. For each body, merge the vectors computed above with the body's initial velocity to produce a new velocity vector.

OK so far?

So that looks something like

```clojure
(require [clojure.math.combinatorics :refer [combinations]])

(defn physics-tick 
    "Compute the change of velocity to each of these `objects` in one tick."
    [objects]
    (pmap merge-vectors
        (pmap apply-friction
            (pmap apply-gravity
                (merge-collisions
                    (remove nil? (pmap collide (combinations objects 2)))
                    objects
                )
        )
    )
)
```
Where:

`collide` is a function which takes two objects, and if those objects are not in collision at this physics step, returns nil; if they are in collision, returns a pair of two objects like those passed, except that each has a 3d vector object added to its `:deltas` property.

Thus, `(pmap collide (combinations objects 2))` will return a flat list with potentially many copies of the same object, each with different deltas; but it will not contain any copies of any objects which weren't in collision at all.

Consequently, `merge-collisions` must, for every key in the objects, collect each of the copies of that object from the collisions and from the original objects list, and, for each key, return one object identical to the original object except that its `:deltas` property is a concatentation of the `:deltas` properties of each of the copies:

```clojure
(defn merge-collisions
    [collision-pairs objects]
    (pmap
        #(assoc (first %) :deltas (apply concat (map :deltas %)))
        (partition-by :id
            (sort-by :id
                (concat
                    (flatten collision-pairs)
                    objects)))))
```

`apply-gravity` is simply a function which returns an object like the object passed to it, but with a constant acceleration-under-gravity vector added to its `:deltas` property;

`apply-friction` is a fairly simple function which computes a friction vector in the direction opposite to the current velocity object passed to it, and returns an object like the object passed but with that fricton vector added to its `:deltas` property;

and finally,

`merge-vectors` is a function which takes an object with a `:position`, a `:velocity`, and a (possibly empty) sequence of `:deltas`, computes a new `:velocity` vector produced by applying each of the deltas in turn to the velocity, and a new `:position` produced by adding that new `:velocity` (times the duration of the tick) to the old position. It returns an object like the object passed but with the new `:position` and `:velocity` values. I'm too tired to confidently state the maths of that now, but I'm confident it's simple.

Which means, in fact, that the whole thing is simple (modulo the `collide` function, which I haven't even sketched above, and which is somewhat dependent on the models involved).

Note that, however

1. At this stage it considers only velocity deltas, not spin deltas; it ought to do that too, but, although that adds extra operations, it doesn't add complexity; and
2. This currently considers only rigid bodies. Soft bodies are a significantly more complex problem.
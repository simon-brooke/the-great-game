# A Canonical dictionary for this documentation

Where a word is used in the documentation for The Great Game and its related projects, this file describes the canonical meaning of that word. This is because a lot of the concepts in play are messy and ambiguous, so that at times even I am confused by what I mean. The presence of this file is an acknowledment of this difficulty, and an implicit admission that not all the documentation is, at this stage anyway, consistent.

#### Actor

An `actor` is a thing which performs actions within the game world. Thus a tree is (almost certainly) not an actor, and things like sheep and rabbits that run about are probably not actors, but an animal which may pro-actively interact with the player character (such as a predator, or a beast of burden, or even a prey species which may flee) is an actor. In [god mode](#God_mode), if implemented, the player can inhabit any actor within the game world.

#### Agent

`Agent` is probably just a synonym for `actor`. If it is different in any way, that way has not yet been determined.

#### Gossip

A `gossip` is an `actor` who exchanges news with other `actors`, even when the player character is not nearby. Thus `gossips` are the mechanism by which news propagates through the game world, and also the mechanism by which information degrades. Broadly:

1. `innkeepers` (and possibly some others) are `gossips` who do not move; rather, they gather information from gossips who do move, and all `non-player characters` local to the are deemed to know everything that their local `innkeeper` knows;
2. `merchants` (and possibly some others) are `gossips` who do move from place to place, and thus transfer news.

See [the spread of knowledge in a large game world](The-spread-of-knowledge-in-large-game.html).

#### Heightmap

A `heightmap` is a raster image of the world, such that the intensity in which an area is coloured represents the value of some variable, by default height, of that area.

#### Holding

A `holding` is a polygon 'owned' by an `actor` on which are built appropriate building units representing the `actors` craft and status.

#### Location

A `location` value is a sequence comprising at most the x/y coordinate location and the ids of the settlement and region (possibly hierarchically) that contain the location. If the x/y is not local to the home of the receiving agent, they won't remember it and won't pass it on; if any of the ids are not interesting, they won't be passed on. So location information will degrade progressively as the item is passed along.

It is assumed that the `:home` of a character is a location in this sense.

**Examples**

1. [{:x 5445678 :y 9684351}]
2. [{:x 5445678 :y 9684351} :karalin-palace :hanshua]

#### Merchant

A `merchant` is an `actor` and `gossip` who trades goods, and incidentally conveys news, between `markets`.

#### Non-player character

A `non-player character` is, for our purposes, an `actor` capable of engaging in conversation with the `player character`. Note, however, that, from a software point of view, the `player character` is just a special case of a `non-player character`.

#### Player character

The `player character` is the unique `actor` within the game currently controlled and inhabited by the player. 

#### Route

A `route` is a pre-prepared path through the game world that an `actor` may take. Most `actors` are not constrained to follow `routes`, but in general `routes` have lower traversal cost than other terrain.
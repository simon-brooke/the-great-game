# Pathmaking

**NOTE**: this file is called 'pathmaking', not 'pathfinding', because 'pathfinding' has a very specific meaning/usage in game design which is only part of what I want to talk about here.

**NOTE**: Work on this is being carried on in a separate library, [Walkmap](https://github.com/simon-brooke/walkmap), q.v.

## Stages in creating routes between locations

*see also [Baking the world](Baking-the-world.html)*

Towards the end of the procedural phase of the build process, every agent within the game world must move through the complete range of their needs-driven repertoire. Merchants must traverse their trading routes; soldiers must patrol routes within their employers domain; primary producers and craftspeople must visit the craftspeople who supply them; every character must visit their local inn, and must move daily between their dwelling and their workplace if different; and so on. They must do this over a considerable period - say 365 simulated days.

At the start of the baking phase, routes - roads, tracks and paths - designed by the game designers already exist.

The algorithmic part of choosing a route is the same during this baking phase as in actual game play **except** that during the baking phase the routemap is being dynamically updated, creating a new path or augmenting an existing path wherever any agent goes.

Thus the 'weight' of any section of route is a function of the total number of times that route segment has been traversed by an agent during this baking phase. At the end of the baking phase, routes travelled more than `R` times are rendered as roads, `T` times as tracks, and `P` times as footpaths, where `R`, `T` and `P` are all chosen by the game designer but generally `R > T > P`.

### Routing

Routing is fundamentally by [A\*](https://www.redblobgames.com/pathfinding/a-star/introduction.html), I think.

#### Algorithmic rules

1. No route may pass through any part of a reserved holding, except the holding which is its origin, if any, and the holding which is its destination, if any (and in any case we won't render paths or roads within holdings, although traversal information may be used to determine whether a holding, or part of it, is paved/cobbled;
2. No route may pass through any building, with the exception of a city gate;
3. We don't have bicycles: going uphill costs work, and you don't get that cost back on the down hill. Indeed, downhills are at least as expensive to traverse as flat ground;
4. Any existing route segment costs only a third as much to traverse as open ground having the same gradient;
5. A more used route costs less to traverse than a less used route.

#### Step costing:

Step cost is something like:

    (/
      (-
        (+ distance
          (expt height-gained height-gain-exponent)
          (reduce + (map crossing-penalty watercourses-crossed)))
        (reduce + (map bridge-bonus bridges-crossed)))
      (or road-bonus 1))

**Where**

* `distance` traversed is in metres;
* `height-gained` is in metres;
* `height-gain-exponent` is tunable;
* river `crossing-penalty` varies with a (tunable) exponent of the flow;
* `bridge-bonus` works as follows: bridge bonus for a bridge entirely cancels the river crossing penalty
for the watercourse the bridge crosses; bridge bonus for a ferry cancels a (tunable) fraction the river
crossing penalty.
* road-bonus for a road is substantial - probably about 8; for a track is less than road but greater than footpath, say 5; for a footpath has to be at least 3, to provide an incentive to
stick to paths. All these values are tunable. Road bonus ought also to increase a small amount with each traversal of the path segment, but that's still to be worked on.

A lot of this is subject to tuning once we have prototype code running.

Somewhere into all this I need to factor tolls charged by local aristons,
especially for bridges/ferries, and risk factors of hostile action, whether
by outlaws or by hostile factions. But actually, that is at a per actor
level, rather than at a pathmaking level: richer actors are less deterred
by tolls, better armed actors less deterred by threat of hostile action.

### River crossings

River crossings appear automatically when the number of traversals of a particular route across a watercourse  passes some threshhold. The threshold probably varies with an exponent of the flow; the threshold at which a ferry will appear is lower (by half?) than the threshold for a bridge. Of course river crossings, like roads, can also be pre-designed by game designers.

Where a river is shallow enough, (i.e. where the flow is below some threshold) then a path crossing will be rendered as stepping stones and a track crossing as a ford. Where it's deeper than that, a path crossing either isn't rendered at all or is rendered as a light footbridge. A track or road crossing is rendered as a bridge. However, the maximum length of a bridge varies with the amount of traffic on the route segment, and if the crossing exceeds that length then a ferry is used. Road bridges will be more substantial than track bridges, for example in a biome with both timber and stone available road bridges might be rendered as stone bridges while track bridges were rendered as timber. If the watercourse is marked as `navigable`, the bridge must have a lifting section. It is assumed here that bridges are genetic buildings like most other in-game buildings, and so don't need to be individually designed.

### Representation

At some stage in the future I'll have actual game models to work with and $DEITY knows what the representation of those will be like, but to get this started I need two inputs: a heightmap, from which gradients can be derived, and a route map. The heightmap can conventionally be a monochrome raster image, and that's easy. The route map needs to be a vector representation, and SVG will be as convenient as any. So from the point of view of routing during the baking phase, a route map shall be an SVG with the following classes:

* `exclusion` used on polygons representing e.g. buildings, or impassable terrain which may not be traversed at all;
* `openwater` used on polygons representing oceans and lakes, which may be traversed only by boat (or possibly swimming, for limited distances);
* `watercourse` used on paths representing rivers or streams, with some additional attribute giving rate of flow;
* `navigable` may be an additional class on a path also marked `watercourse` indicating that it is navigable by cargo vessels;
* `route` used on paths representing a path, track or road whose final representation will be dynamically assigned at the end of baking, with some additional attribute giving total traversals to date;
* `path` used on paths representing a path designed by the designers, which will certainly be rendered as a path no matter how frequently it is traversed;
* `track` used on paths representing a track designed by the designers, which will certainly be rendered as a track no matter how frequently it is traversed;
* `road` used on paths representing a road designed by the designers, which will certainly be rendered as a road no matter how (in)frequently it is traversed.

At the end of the baking process the routing engine should be able to write out an updated SVG. New routes should be splined curves, so that they have natural bends not sharp angles.

### The 'Walkmap'

Conventional game pathfinding practice is to divide the traversable area into a mesh of 'convex polygons', where a 'convex polygon' in this sense is, essentially, a polygon having no bays. Routes traverse from a starting point to the centre of a polygon ajacent to the polygon in which the starting point is located. I have reservations as to whether this will do what I need since I'm not convinced it will produce naturalistic paths; however, it's worth at least experimenting with.

There are existing utilities (such as [hmm](https://github.com/fogleman/hmm)) which convert heightmaps into suitable geometry files; however all I've found so far convert to [binary STL](https://en.wikipedia.org/wiki/STL_(file_format)). This isn't a format I find very useful; I'd prefer an XML dialect, and SVG is good enough for me.

`hmm` converts the heightmap into a tesselation of triangles, which are necessarily convex in the sense given above. Utilities (such as [binary-stl-toASCII](https://github.com/IsseiMori/binary-stl-toASCII)) exist to convert binary STL to an ASCII encoded equivalent, which may be easier to parse.

So the pipeline seems to be

1. heightmap to binary STL
2. (optional) binary STL to ASCII STL
3. STL to SVG (where 'SVG' here is shorthand for a convenient vector format)
4. Exclude holdings, buildings, open water, and other exclusions
5. Where we have excluded exclusions, ensure that any non-convex polygons we've created are divided into new convex polygons.

I shall have to write custom code for 4 and 5 above, and, looking at what's available, probably 3 as well.

I'm working on a separate library, [walkmap](https://simon-brooke.github.io/walkmap/), which will attempt to implement this pipeline.

### Pathmaking and scale

Dealing with large heightmaps - doing anything at all with them - is extremely compute intensive.

We cannot effectively do routing at metre scale - which is what we ultimately need in settlements - across the entire thousand kilometre square map in one pass. But also we don't need to because much of the continent is by design relatively unpeopled and relatively untracked. The basic concept of the Steppe is that there are two north/south routes, the one over the Midnight Pass into the Great Place and the one via Hans'hua down to the Cities of the Coast, and those can be part of the 'designed roads' map. So we can basically exclude most of the Steppe from routing altogether. We can also - for equally obvious reasons exclude the ocean. The ocean makes up roughly half of the 1000x1000 kilometre map, the steppe and plateau take up half of what's left, mountain massifs eat into the remainder and my feeling is that much of the eastern part of the continent is probably too arid to be settled. So we probably end up only having to dynamically route about 20% of the entire map.

However, this doesn't get round the main problem with scale, and pathmaking. If we pathmake at kilometre scale, then curves will be necessarily very long and sweeping - because each path segment will be at least a kilometre long. And, actually, that's fine for very long distance roads in unpopulated fairly flat territory. It's not so good for long distance roads in rugged terrain, but...

#### Phase one: hand-designed routes

While, given the bottlenecks of the few mountain passes and the one possible pass over the plateau, the caravan routes we want would almost certainly emerge organically out of dynamic routing. But, actually, I know more or less where they need to be and it's probably easiest to hand design them. It will certainly save an enormous amount of brute-force compute time.

I think I have to accept that if I want Alpe d'Huez-style switchbacks up the Sunset and Midnight passes, they're going to have to be hand designed. The same applies to where the Hans'hua caravan road ascends the plateau.

#### Phase two: route segments 'for free' out of settlement activity

If we start by pathmaking around settlements, we can make a first start by giving the template for a holding a segment of track parallel to and just in front of its frontage, and a segment of path along its left hand and rear edges. That, actually, is going to provide 90% of all routing within a settlement, and it's done for us within the [[Settling-a-game-world]] phase.

#### Phase three: metre scale routing around settlements

So if we then collect groups of contiguous 100x100 metre zones each of which has at least one settled holding, we can route at one metre scale over that and what it will essentially do is join up and augment the route segments generated by settlement. Areas of dense settlement do not make up a great deal of the map. Note that experience may show that the metre scale routing is superflous.

#### Phases four, five and six: increasing granularity

Taking the augmented route map comprised of

1. The hand-designed, mainly long distance or plot-important routes;
2. The route segments bordering holdings;
3. The metre scale routing

we can then collect contiguous groups of zones each having at least one holding, where in phase four each zone is a kilometre square and divided into 100x100 grid so that we route at ten metre scale; in phase five we use ten kilometre by ten kilometre zones and we route at 100 metre scale; in phase six, 100 km by 100 km zones and we route at kilometre scale. This process should automatically link up all settlements on the south and west coasts, all those on the north coast, and all in the Great Place; and seeing that the posited pre-designed caravan roads already join the south coast to the north, the north to the Great Place and the Great Place to the south coast, we're done.

At least one of phases three, four, five and six is probably redundant; but without trying I'm not sure which.

#### Relevant actor classes by phase

Craftspeople and primary producers do travel between settlements, but only exceptionally. They mainly travel within at most a few kilometres of home; so they are primarily relevant in phases four and five, and need not be activated during phase six. Similarly, merchants primarily travel between settlements, and rarely within settlements; therefore, they need not be activated in phase four, and probably not even in phase five; but they must do a lot of journeys - substantially their full repertoire - in phase six.

### Tidying up

After the full set of increasing-scale passes is complete, we should automatically cull any route segments generated in the settlement phase which have never actually been traversed.

Following that, there may be scope for some final manual tweaking, if desired; I think this is most likely to happen where roads routed at kilometre scale cross rugged terrain.

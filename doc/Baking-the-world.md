# Baking the world

#### Wednesday, 8 May 2019

![Devogilla's Bridge in Dumfries, early foourteenth century](https://2.bp.blogspot.com/-qxkySlJNmtY/XNKvJksmSjI/AAAAAAAAnXU/z1Zv2LmjydMmi_1q2mWdwVALmdfi9OItwCLcBGAs/s1600/Devorgillas-Bridge.jpg)

 In previous posts, I've described algorithms for dynamically [populating](Populating-a-game-world.html) and dynamically [settling](Settling-a-game-world.html) a game world. But at kilometre scale (and I think we need a higher resolution than that - something closer to hectare scale), settling the British Isles using my existing algorithms takes about 24 hours of continuous compute on an eight core, 3GHz machine. You cannot do that every time you launch a new game.

 So the game development has to run in four phases: the first three phases happen during development, to create a satisfactory, already populated and settled, initial world for the game to start from. This is particularly necessary if hand-crafted buildings and environments are going to be added to the world; the designers of those buildings and environments have to be able to see the context into which their models must fit.

## Phase one: proving - the procedural world

 I'm going to call the initial phase of the game run - the phase which takes place before the quest team write their quests and the art department adds their hand-crafted models - 'proving', as when dough has been been made and set aside to rise.

 Then, when the landscape has developed - the areas of forest, scrub, open meadow, moorland, savanah and desert are determined, the rivers plotted, the settlers moved in, their trades determined and their settlements allocated, the roadways which link settlements routed, river crossings and ports defined - the proving process ends, and the world is turned over to the plot-writers, quest builders and designers, for a process we can see as analogous to kneading.

 But, before going there, to summarise the proving stage. The inputs are:

1. A raster height map (although this could be randomly generated using any one of many fractal algorithms) - this probably uses ideas from [tessellated multi-layer height map](../../2013/07/tessellated-multi-layer-height-map.html);
1. Optionally, a raster rainfall map at 1km resolution (although my personal preference is that this should be generated procedurally from the height map).

 The outputs are

1. A vector drainage map (rivers);
1. A raster biome map at roughly 1 km resolution (it might be anything between hectare  resolution and 1Km resolution,  but obviously higher resolution takes  more storage);
1. A database of settlers and their settlements, such that the settlements have x,y co-ordinates;
1. A vector road map.

 In this sense, the 'biome map' is just the end state of a [Microworld](../../2014/08/modelling-settlement-with-cellular.html) run. The 'biomes' include things like 'forest', 'scrub', 'heath', 'pasture', but they may also include human settlement, and even settlement by different cultural groups.

 This gives us all we need to vegetate and furnish the world. When rendering each square metre we have

1. The x,y coordinates, obviously;
1. The altitude, taken from the height map;
1. The biome, taken from the biome map;
1. The biomes of adjacent cells in the biome map;
1. The proximity of the nearest watercourse;
1. The proximity of the nearest road or pathway;
1. Whether we are inside, or outside, a settlement (where for these purposes, 'settlement' includes enclosed field), and if inside, what type of settlement it is.

 Given these parameters, and using the x, y coordinates as seed of a deterministic pseudo-random number generator, we can generate appropriate vegetation and buildings to render a believable world. The reason for pulling adjacent biomes into the renderer is that sharp transitions from one biome to another - especially ones which align to a rectangular grid - rarely exist in nature, and that consequently most transitions from one biome to another should be gradual.

 Note that proving, although extremely compute intensive, is not necessarily a one-time job. If the designers aren't satisfied with the first world to emerge from this process, they can run it again, and again, to generate a world with which they are satisfied. It's also possible to hand-edit the output of proving, if needed.

 But now, designers and story-writers can see the world in which their creations will be set.

## Phase two: kneading - making the world fit our needs

 Enough of proving, let's get on to kneading.

 Hand-designed buildings and environments are likely to be needed, or at least useful, for plot; also, particularly, very high status buildings are probably better hand designed. I'm inclined to think that less is more here, for two reasons:

 You cannot hand design a very large world, it's just impossible. How CD Project Red managed with Witcher 3 I don't know, since I understand that is largely hand designed; but that was a very large team, and even so it isn't a world on the scale I'm envisaging.

 Procedurally generated models take a wee bit of compute power to reify, but not a huge amount, and they're trivial to store - you need one single birch leaf model and one single birch-bark texture generator to make every birch tree in the game, and probably a single parameterised tree function can draw every tree of every species (and quite a lot of shrubs and ground-cover plants, too). But once reified, they take no longer to render than a manually crafted model.

 By contrast, a manually crafted model will take a very great deal more space to store, such that being able to render a large world from hand crafted models, without excessive model re-use, isn't going to be possible.

 So it's better in my opinion to put effort into good procedural generation functions, not just for foliage but also for buildings. My reason for using a picture of a medieval bridge at the head of the essay is to illustrate exactly this point: even in the medieval period, bridges comprise a series of repeating modules. Take one arch module and one ramp module from Devorgilla's bridge as models, add texture skins for several different stone types, stretch the modules a little in whatever dimension is needed, and repeat the arch module as many times as needed, and you can create a range of bridges to span many different rivers - which will all be visibly similar, but that's fine, that's the nature of a traditional culture - but each slightly different.

 Take half a dozen sets of models - timber bridges for forested biomes, brick bridges for biomes without stone or timber - and you can build procedural bridges across a whole continent without ever exactly repeating yourself.

 However, in some places the designers and story writers will want, for plot reasons and to create iconic environments, to add models. I'm inclined not to over do this, both for reasons of development effort and for reasons of storage cost, but they will. Very high status buildings may need to be unique and distinctive, for example. These need to be designed and their locations and spatial dimensions added to the database, so that the models can be rendered in the right positions (and, critically, procedurally generated models can be omitted in those positions!)

 Story and quest writers will also want characters for their plots. While there's no reason why writers cannot add entirely new characters to the database, there's no reason why they cannot incorporate characters generated in the settlement phase into the story; for this reason, characters need to be able to be tagged in the database as plot characters, and with what quests/elements of the plot they're associated.

 This allows a mechanism to prevent a plot character from being killed by another non-player character, or dying of disease or starvation, before the plot elements in which they feature have been completed.

## Phase three: baking - making it delicious

 Once the world has been populated, settled, vegetated, the story has been written, the models built, the quests designed, there is probably a process of optimisation - stripping out things which aren't needed at play time, streamlining things that are - before you have a game ready to ship; but really I haven't yet given that much thought.

## Phase four: eating!

 At the end, though, you have a game, and a player plays it. How much of the dynamic, organic life that brought the game through proving continues on into the playing phase? If the [gossip](The-spread-of-knowledge-in-a-large-game.html) ideas are to work, if unscripted, non-plot-related events (as well as scripted, plot related events) are to happen while the player plays, if news of these events is to percolate through the world and reach the player in organic, unscripted ways, if a lot of the emergent gameplay I'm imagining is to work, then quite a lot of the dynamic things must be happening.

 Of course, part of this depends on the length of 'game world time' is expected to elapse in the course of one play through of the game. If it's less than a year, then you don't need children dynamically being born, and characters dynamically growing older; but if more, then you do. Similarly, you don't need a real simulation of trading to dynamically drive prices in markets, but for a fun trading sub-game to emerge, you probably do, and if you are using merchants as news spreading agents the additional compute cost is not high.

 And I understand that many game writers will shudder at the thought that a war might (or might not) start in the middle of their plot, that a battle might, one time in a thousand, take place right where they've plotted some significant encounter. Most modern video games are essentially just very complicated state machines: if you make this sequence of choices, this outcome will happen, guaranteed. Or else they're puddles of random soup, where everything that happens is more or less driven by a random number generator. What I'm envisaging is something quite different: a world in which traders gonna trade, robbers gonna rob, lovers gonna love, scandal-mongers gonna make scandal, organically and dynamically whether the player is there or not, and news of these events will filter through to the player through the gossip network also organically and dynamically.

 A world, in short, through which no two runs will ever be the same, in which interesting bits of story will happen with no-one directing or scripting them. And for that to work, some of the same dynamic processes that drove the proving phase have to continue into the eating phase.

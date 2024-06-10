# Worlds and flats [obsolete]

*This essay is from 2008, and is now at least partly obsolete; but there's useful stuff in it which is worth holding onto.*

## Of Compartmented Worlds

Playing The Witcher has got me thinking again about an algorithm for rendering a world which I first thought of twenty-five years ago. Then, it was a hack for dealing with the fact that the computers of the day didn't have much memory or horsepower. Now, it's a hack for dealing with the fact that &mdash; when considered against the complexity of a world &mdash; the computers of today still don't have enough memory and horsepower. Mind you, today I'm contemplating photorealistic scenes, whereas then simple line and wash would have been good enough, but...

The algorithm for rendering I'll call 'flats'. But before we get to discussing flats, lets discuss worlds.
The world of The Witcher (and other games based on the Aurora engine) is composed of areas. One area is loaded into memory at a time; when the player reaches an area boundary, the area is unloaded in toto, and the next area loaded, also in toto. The result is a noticeable interruption in game play. There's also, normally, a noticeable visual disjunction at the boundary; the new area uses a different 'tileset', which is to say, set of bits of scenery. When you look across a boundary, the scenery often appears different from what you find when you cross the boundary and arrive at the other side. 

Finally, you can only cross boundaries at specific gateway points. For example, Chapter Four of The Witcher takes place in a continuous rural space composed of three main areas: the lakeside, the village, and the fields. Between the lakeside and the other two regions there's a wooded escarpment, which provides some logical justification for the fact that there are actually only two places you can actually cross it &mdash; from the lake shore, either up the road to the village or else through a series of glades to the fields. But between the village and the fields there's no such logic. There are a pair of gateposts, and you must cross between those gateposts &mdash; but the landscape appears continuous, with no visible barrier. It's an artifact of the game engine.

So, how to deal with this?

My interest, let's be frank, is in story telling; and the nature of story telling is moderately constrained plots. In computer mediated story telling the reader/player can and should be able to explore the plot in his own way, make his own choices, take his own path through the environment, encounter the elements of the plot as he encounters them on that path, and it's the job of the story teller to make that engaging whatever path the reader takes. But if the reader chooses to ignore your hints and wander out of the area you've populated with plot altogether, there's two things you can do. One is put up physical barriers which stop him (although the silly field fences in Chapter One of The Witcher do *not* count, as it's obvious that Geralt could simply hurdle them; they are just another artifact).

## Of Finite and Infinite Worlds

But an infinite world is not required for the sort of stories I'm interested in; the sort of stories I'm interested in take place in, at best, regions of infinite worlds. Just because I don't choose to use all of it, of course, is not a reason that a world should not be very large.

There are plenty of fractal mathematical equations which map an infinite three dimensional surface with landscape like features. If such an equation gives you land heights, then altitude, steepness and orientation will give you soil type and vegetation cover. There is no need to store a whole world in order to be able to reproduce it exactly when the player follows the same route through it for a second time; it is sufficient to start with the same seed. So a world need not take up vast amounts of disk space for pre-mapped scenery; scenery need only be mapped as it comes in sight. This is fundamentally the trick used by Elite to pack a large, reproducible universe into less than 32K of RAM, and it still works today.

### Of River Systems

However, there are reasons to prefer that a world be pre-mapped, at least at coarse grain. One example of why is river systems. It's trivial that we render a river at the bottom of a valley, but it isn't trivial to compute how wide and deep that river should be. To calculate that we have to explore the extent of the watershed upstream of any given point, and sum the rainfall on it, which in turn is a factor of exposure to prevailing airflow and the proximity of ocean to windward. It's computable, but it's much more efficient to compute it once and cache the results, especially since for any given river system it's a recursive function. 

Furthermore, rivers cause erosion, changing the landscape through which they pass, cutting gorges on steep slopes (especially if soft), building up flood-plains in flatter areas downstream. Some fractals are naturally extremely landscape-like, but it seems to me &mdash; better mathematicians might prove me wrong &mdash; to realistically render river systems requires some degree of post processing, and post processing which would be expensive to do repeatedly in realtime.

### Of Human Settlement

Human settlement is a separate issue. Many years ago I wrote a program which modelled the spread of human settlement over a landscape. 

Human settlement is not random. Human settlement follows rules and patterns. Pioneers settle in places which have a sufficient spread of resources to meet their year round needs; they settle near to easy routes from their parent settlement. Pastoralists need grazing land and water; they spread up river systems, but avoid marshy areas. They settle where there is open grazing, but often close to a forest edge for access to timber. Second wave settlers prefer to settle close to existing settlers, for mutual protection and help. Cereal growers join these settlements where the depth of soil is optimal for crops. As the settlement grows and pressure on land increases, the forest edge is cut back both for building material and to increase the available agricultural land. If a settlement fails, the forest may reclaim this land

Road networks develop. People travel between settlements by the easiest route &mdash; but the very fact a route is travelled makes it easier. A path gets cleared; later, people fill in boggy bits and bridge streams, to make their own passage easier or to encourage trade through their lands. Because as a road grows more important, so the settlements along it grow more important, and as the settlements along it grow more important so the roads between them grow more important. The road network, then, is a dynamic fractal which interacts with another dynamic fractal, the distribution of human settlement.
 
The program I wrote was a cellular automaton which modelled human settlement in only thirty states. It did a remarkably good job. Settlement would spread across a landscape; settlements in strategically beneficial areas would grow faster, develop temples and markets sooner, and thus become important foci of the roads system; other settlements would wax and wane, some falling into ruin; new waves of settlers might settle in slightly different areas.

More states would be better, give a richer, more subtle model, But this demonstrates that it's easy to design a program which will settle a landscape in a realistic way automatically. Once again, though I think it can be done more realistically if it is precomputed and cached rather than if it is generated from the landscape fractal.

In summary: yes, I think it's possible to have a near-infinite world which is satisfying and can be reproduced at will from a seed, but the stories I want to tell do not call for infinite worlds and if the world is finite I believe it can be made still more satisfyingly realistic by pre-computing and caching things like river systems, afforestation and settlement patterns. Either way, though, the world can be very large &mdash; much larger than the worlds of current near-photo-realistic games. The world of The Witcher, for example, is a few hundred hectares; I'm envisaging storing hundreds or thousands of square kilometres in similar data size and with a similar expenditure of artist's effort.

## Rendering, and the Flats idea

 Rendering a convincing distant view in computer-generated virtual environment is hard. There's an enormous amount of data in a distant view, and if the viewer is moving in real time it becomes computationally unaffordable, even on machines with a great deal of horsepower. Games typically work around this problem by either angling the camera downwards, or else rendering a high degree of atmospheric haze &mdash; it's always slightly foggy &mdash; so there is never a distant horizon.

 Movies shot in studios often have wonderful, detailed backgrounds to their sets. Vistas of far mountains and great cities... of course, the far mountains and great cities don't exist in the set. They're painted on large canvases called 'flats'. The flats illusion depends on the camera not moving too much, because of parallax &mdash; nearer things should appear to move relative to further things, and on a flat they don't.

 But. But.

 For a player moving in a computer game the field of view is quite restricted &mdash; it's no more than thirty degrees, typically straight ahead as he moves. Parallax movements are less significant straight ahead. A single flat still isn't going to work, but in many animated films a system of multiple flats is used, with the nearer flat moved relative to the further flat to give an illusion of parallax. This can work very well.
 Suppose one projects onto the world a hexagonal grid &mdash; it doesn't have to be hexagonal, but I think that is likely to work best &mdash; with a cell size of about 100 metres (the exact cell size depends a bit on the speed of movement of the player, for reasons which will become apparent). Cells are grouped into metacells of seven cells (if square, then metacells of nine cells). For each cell, there are six inner flats. Each inner flat consists of a rendering from the centre of the cell of everything more than one cell distant, but less than five cells distant, over a 60 degree arc of view.

 For a given area of the world the distant view doesn't change very much. We don't, therefore, have to compute a set of outer flats for every cell, just for every metacell. The outer flats each consist of a rendering of the scenery more than one whole metacell away, from the centre of the metacell.
 To render a scene, then, we first paint the outer flat for the metacell the player is in, in the direction the player is looking. Over that we paint the inner flat for the cell the player is in. Over that we render the actual objects in the adjacent cells which fall within the viewing area. Over that we render the objects in the current cell. Thus we only have to render in real time certainly no more objects than can already be rendered by systems which clip for distance either by angling the camera down or by using fog, and yet still manage a realistic distant view.

## Rendering the Flats

 OK, so when do the flats get rendered? After all, if you're going to pre-render six full colour full screen resolution flats for every hundred metre cell in the world, then either your data volume is going to get enormous or your world is going to get pretty constrained &mdash; which was just what we were trying to get away from. And if you're going to multiply that with flats rendered for every time of day and every weather condition &mdash; well, it's not feasible.

 You cannot realistically pre-render the flats, in my opinion. Or if you can, you're going to have to give them so much real time post processing that you will lose the benefit. Pre-rendering the flats is not the idea. But if they are rendered in real time, where is the benefit...?

 There's a middle way. Running straight forward at top speed a player crosses a hundred metre cell in about a minute, during which to give an illusion of continuous movement at least nine hundred full screen frames must have rendered. But the flats don't change in a minute. The flats don't change in five minutes. They don't need to. Even if rain clouds are sweeping across the landscape, it's OK for the distant view still to be sunny five minutes after the rain reaches you, or vice versa. If you can render a high proportion of the detail in a view only once every nine hundred frames, you've saved a lot of processing.

 So there is a continuous background process running which renders flats. It does it all the time. It prioritises making sure that a flat exists for every direction the player may look in in the next minute &mdash; that is, every direction from the cells and metacells he's currently heading towards. Having done that, it renders flats for cells to either side which he might turn to. It maintains in memory a small stock of flats from recently visited cells, so that if the player turns back they don't have to be repainted in a hurry; and if a flat is more than about five minutes &mdash; 4,500 frames &mdash; old, it may repaint it to update time-of-day lighting or weather effects.

 Obviously, quite a lot of the time the join between two adjacent flats will be in view. I don't see this as a problem. Just naturally, the rendering of the flats should essentially form segments of a hoop, so the join between two adjacent flats should not be perceptible.

## Artifacts

 Inevitably, there will be undesired artifacts of this system. Significantly, mobile objects &mdash; 'non-player characters', the avatars of other players, monsters and computer mediated creatures in the landscape &mdash; more than two cells away will not be visible. The flat is static, so it can't have moving characters on it. There may be some algorithmic way round this, since one hundred and fifty metres away is rather close for people to suddenly vanish; but it is not a problem I have a solution for.

 Again, if the player is looking sideways as they cross a metacell boundary, there will be a jarring sudden shift in parallax. I acknowledge that and think it just can't be helped; that the benefits in terms of quality of view for given computer power, will render it acceptable.    
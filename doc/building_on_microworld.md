# Building on Microworld

In [Settling a Game World](Settling-a-game-world.html) I intended that a world should be populated by setting agents - settlers - to explore the map and select places to settle according to particular rules. In the meantime, I've built [MicroWorld](https://github.com/simon-brooke/mw-ui), a rule driven cellular automaton which makes a reasonably good job of modelling human settlement. It works, and I now plan to use it, as detailed in this note; but there are issues.

First and foremost, it's slow, and both processor and memory hungry. That means that at continent scale, a cell of one kilometre square is the minimum size which is really possible, which isn't small enough to create a settlement map of the density that a game will need. Even with 1 km cells, even on the most powerful machines I have access to, a continent-size map will take many days to run.

Of course it would be possible to do a run at one km scale top identify areas which would support settlement, and then to do a run on a ten metre grid on each of those areas to more precisely plot settlement. That's an idea which I haven't yet explored, which might prove fruitful.

Secondly, being a cellular automaton, MicroWorld works on a grid. This means that everything is grid aligned, which is absolutely not what I want! So I think the way to leverage this is to use Microworld to establish which kilometre square cells om the grid should be populated (and roughly with what), and then switch to ad hoc code to populate those cells.

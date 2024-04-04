# Genetic Buildings

### Building selection based on location

 The objective of this note is to create a landscape with varied and believable buildings, with the minimum possible data storage per instance.
 
 Like plants, buildings will 'grow' from a seed which has northing and easting attributes. These locate a position on the map. Again, like trees, some aspects of the building type selector are location based. Aspects of the location which are relevant to building type are
 
* **elevation** &mdash; derived from the map location by interpolation from grid. The actual interpolation algorithm is probably some form of spline, but in any case it's the same one as for everything else. 
* **orientation of slope** &mdash; derived by taking altitude at four corners of a 100 metre square centred on the seed point, and then taking the highest and lowest of these. If highest is northwest, lowest is southeast, the slope is considered to be oriented southeast; if highest is northwest and lowest southwest, the orientation is considered to be south, and so on. Eight orientation values are sufficient. 
* **gradient of slope** &mdash; derived from the difference in altitude across the same 100 metre square 
* **neighbours** &mdash; number of other buildings in 500 metre square centred on seed point. 

 The reason orientation is relevant is exactly the same as the reason it's relevant to trees. West facing slopes are assumed wetter (coriolis winds), so grow trees better, so better availability of better quality timber, so a higher probability of timber as a primary building material. But also, in areas of higher rainfall, rain shedding is an important consideration, so a higher value is placed on pitched roofs.
 
 So you have the following general relationships
 
* west (or southwest or northwest) facing, moderate gradient, moderate altitude: high probability of timber construction; construction techniques involving large timbers (e.g. cruck frame); greater probability of shingled roofs; 
* west (or southwest or northwest) facing, moderate gradient, higher altitude or northern latitude: high probability of building styles adapted to straight-trunk conifers, e.g. log cabins, stave buildings; greater probability of shingled roofs; 
* east facing, generally: greater probability of flat roofs; 
* steeper gradients: greater probability of stone buildings (steeper gradients = shallower topsoil and greater ease of quarrying = access to stone); greater probability of slate roofs; 
* shallower gradients: greater probability of mud, cobb, brick or wattle-and-daub as building materials; greater probability of thatch or turf roofs; 
* Higher number of neighbours: higher probability of two or more stories; 

 These factors allow classes of building to be selected. Having got past that point, we need to consider how classes of genetic building can work.
 
### Rectangular genetic buildings

 Some genetic buildings will have cells with rectangular plan. This doesn't mean that genetic buildings are required to have rectangular cells, but they provide a starting point for discussion. For a given class of building (for example, timber frame), a number of prototype models of cells exist. These models are fully realised three dimensional models. Possibly all cells belonging to the building class have two open ends, and end walls exist as separate models; equally possibly, some cells have only one extensible end. In any case, a building will not normally comprise a single cell. Normally it will comprise multiple cells. So the cells belonging to a particular building class will be designed to 'plug together'. Multi story building classes will have some cells which are specifically ground floor only (flat ceiling, no roof), and such cells will always have an upper floor cell added above them. Where an upper floor cell has an outside door, an outside stair will automatically be added.
 
### Cell mutability

 Although cell models are repeatedly reused they don't have to look the same every time they are reused. Within limits, every cell can be stretched along any of its three axes. Obviously, the degree of stretch on a given axis for every cell in a given building must be the same, otherwise they won't line up. Another mutable area is skinning &mdash; it may be possible to have alternate skins for cells, and even if there are not alternate skins, it will be possible to mutably darken, lighten or otherwise tint the skins used, within ranges which are appropriate to the materials represented. Obviously there are limits to stretching &mdash; timber comes in only such a length, stone lintels will only support such a span.
 
### Functional cells

 Some trade functions require cells of particular kinds. Thus a smith needs a working building with one cell which is explicitly a forge. A water mill must have one cell which explicitly houses the mill gear. A forge cell or a waterwheel cell should never appear in weavers workshop. But most cells are not dedicated in this way. A bedroom cell is a bedroom cell, more or less; wealth may alter how it is furnished, but it may appear in any dwelling. Similarly, except for the very wealthy, a living cell is pretty much a living cell. And any building may incorporate a storage cell. If a given building class has twelve distinct 'generic' cells' and half a dozen distinct functional cells, and if buildings in the class average four cells each, then ignoring variance caused by skin mutability, a street of fifty buildings could have every one different.
 
### Reproducibility

 It's critical that if a player visits a location, leaves it, and then returns, the buildings should not all have changed. So it must be possible to repeatedly reproduce the building at the location (this, of course, applies to other procedural scene dressing, such as trees, roads, boundaries, bridges and so on). This is possible if a deterministic random number generator is used which is seeded from the latitude and longitude attributes of the location. Other attributes which should be cached on the seed even though they are determined procedurally when the building is first instantiated include building class, purpose, and wealth. Using these attributes and the deterministic random number generator, the same building can be reproduced on the same site each time it is visited, with a very small amount of data stored.
 
 Buildings will normally be built at the edge of the associated land holding. If an edge of the land holding adjoins a road, then the building will be built with one long side aligned to the road. Otherwise, the building will be built at right angles to the orientation of the slope. The orientation will be 'frozen' once the building has been instantiated and will be cached on the seed.
 
 So, to build a building, use the following algorithm:
 
 Seed the random number generator with latitude and longitude
 
 ```
 while ( building value is less than wealth) {
    select a cell selected from the building class using the next number from the random number generator modulo the number of generic cells in the class;
    if the selected cell is not inappropriate to the building's function {
        fit the cell to the building at the point determined by a deterministic algorithm
        furnish cell using the random number generator to determine
        furnishing types and locations from a selection appropriate to the cell
        if the selected cell was not a top story cell {
             add a requirement that the next cell selected must be an upper story cell}
    }
 }
 ```
 

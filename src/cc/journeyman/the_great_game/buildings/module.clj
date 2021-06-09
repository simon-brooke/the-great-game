(ns cc.journeyman.the-great-game.buildings.module

  "A module of a building; essentially something like a portacabin, which can be
   assembled together with other modules to make a complete building.
 
   Modules need to include

   1. Ground floor modules, having external doors;
   2. Craft modules -- workshops -- which will normally be ground floor (except
      weavers) and may have the constraint that no upper floor module can cover them;
   3. Upper floor modules, having NO external doors (but linking internal doors);
   4. Roof modules
   
   **Role** must be one of:
   
   1. `:primary` a ground floor main entrance module
   2. `:secondary` a module which can be upper or ground floor
   3. `:upper` a module which can only be on an upper floor, for example one
      with a projecting gallery, balcony or overhang.
   
   Other values for `role` will emerge.
   
   **Exits** must be a sequence of keywords taken from the following list:
   
   1. `:left` an exit in the centre of the left wall
   2. `:left-front` an exit in the centre of the left half of the front wall
   3. `:front` an exit in the centre of the front wall
   4. `:right-front` an exit in the centre of the right half of the front wall
   5. `:right` an exit in the centre of the right wall
   6. `:right-back` an exit in the centre of the right half of the back wall
   7. `:left-back` an exit in the centre of the back wall
      
   A module placed on an upper floor must have no exit which opens beyond the 
   footprint of the floor below - no doors into mid air! However, it is allowable 
   (and indeed is necessary) to allow doors into roof spaces if the adjacent
   module on the same floor does not yet exist, since otherwise it would be 
   impossible to access a new room which might later be built there.
   
   **Load** must be a small integer indicating both the weight of the module and 
   the total amount of weight it can support. So for example a stone-built module
   might have a `load` value of 4, a brick built one of 3, and a half-timbered one 
   of 2, and a tent of 0. This means a stone ground floor module could support one 
   further floor of stone or brick, or two further floors of half timbered 
   construction; while a brick built ground floor could support a single brick or 
   half-timbered upper floor but not a stone one, and a half-timbered ground floor
   could only support a half timbered upper floor.
   
   There also needs to be an undercroft or platform module, such that the area of 
   the top of the platform is identical with the footprint of the building, and 
   the altitude of the top of the platform is equal to the altitude of the 
   terrain at the heighest corner of the building; so that the actual 
   building doesn't float in the air, and also so that none of the doors or windows
   are partly underground.

   Each module needs to wrap an actual 3d model created in Blender or whatever, 
   and have a list of optional **textures** with which that model can be rendered. 
   So an upper floor bedroom module might have the following renders:

   1. Bare masonry - constrained to upland or plateau terrain, and to coastal culture
   2. Painted masonry - constrained to upland or plateau terrain, and to coastal culture
   3. Half-timbered - not available on plateau terrain
   4. Weatherboarded - constrained to forest terrain
   5. Brick - constrained to arable or arid terrain

   of course these are only examples, and also, it's entirely possible to have
   for example multiple different weatherboard renders for the same module. 
   There needs to be a way of rendering what can be built above what: for
   example, you can't have a masonry clad module over a half timbered one, 
   but you can have a half-timbered one over a masonry one.")

(defrecord BuildingModule
  [model
   ^Double length
   ^Double width
   ^Double height
   ^Integer load 
   ^clojure.lang.Keyword role
   ^clojure.lang.IPersistentCollection textures
   ^clojure.lang.IPersistentCollection exits
   ]
  )
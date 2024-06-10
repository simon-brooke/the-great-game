# Tree library evaluation

This is a comparative evaluation of open source tree libraries that I have found that I could make use of. It's entirely personal and subjective!

## [SimArboreal](https://github.com/Simsilica/SimArboreal)

### Overview 

Library by Paul Speed, who's a senior and prolific developer in the jMonkeyEngine community; designed to work with jMonkeyEngine, and consequently integrates well with it.

### Licence

[BSD-style licence](https://github.com/Simsilica/SimArboreal/blob/master/src/main/resources/license.txt).

### Assessment

Produces trees which are quite realistic but only for a limited range of broad-leaved species. The trees do have modelled roots, which is relatively unusual. The trees sway in virtual winds, although to my eye trunks sway too much and thinner twigs too little. A limited number of textures are supplied, but new textures can easily be added so this isn't an issue. Handles Level of Detail automatically within the context of the jME3 libraries, which is a real bonus. 

Does not provide the range of species, or of stages of growth, I want. To add convincing coniferous trees, or trees with more than one primary branching point on the main trunk, would require extra coding. The way leaves are handled is crude, but looks surprisingly persuasive. The codebase is reasonably well written and understandable, but there is virtually no documentation. If I have to build on someone else's library, this wouldn't be a bad choice. The one to beat.

## [Tree3D](https://github.com/SnailBones/Tree3D)

### Overview

Looks like a student project, or something done fairly quickly as an exercise.

### Licence

None specified, not even 'public domain'. So might be a bit sketchy to copy from.

### Assessment

This is definitely not as complete or ready to use as SimArboreal; although it does build, the user interface doesn't work with modern Java. Whether it ever worked I don't know, but I wouldn't be confident. Code is reasonably well written, but almost completely undocumented. There is no rendering of bark or of leaves; although the branching structure is excellent and the variety of morphologies available is good, boughs do not bend.

However, algorithmically, this has a much better understanding of how trees grow, and of tree morphology, than SimArboreal. A hybrid taking growth algorithms from this and texture, wind, level of detail and jME3 integration from SimArboreal might be relatively to do.

## [Proctree.js](https://gltf-trees.donmccurdy.com/)

Very polished demo project by Paul Brunt, author of a key JavaScript WebGL 3D library; but in JavaScript, so not directly usable.

### Licence

None specified, not even 'public domain'. So might be a bit sketchy to copy from.

### Assessment

Very beautiful, algorithmically elegant, excellent variety of morphology. But

1. No textures;
2. No wind sway;
3. No roots;
4. No level of detail handler;
5. No leaves;
6. No modelling of bough curvature;
7. Wrong language;
8. Relatively poorly structured code;
8. No documentation.

This would be great to steal inspiration from for a new library of my own, and could possibly be used to inspire a significant extension to SimArboreal, but in its current state it isn't usable.

## What none of the candidates offer

None of the candidates have

1. Seasonal change;
2. Species prototypes;
3. Broken boughs;
4. Leaning or nonstraight primary trunks;

These are all features I want. The same cherry tree encountered in spring should be bedecked with blossom; in summer, green leaved; in autumn, red leaved and heavy with berries; in winter, bare. As seasons change as the player explores the game world, the trees need to change with them.

Cherries need to have green leaves tapering to a point at both ends, pink flowers in late spring, large red berries (and flocks of birds) in autumn. They need to have moderately tall trunks with upward pointing, quite stiff boughs. Pines need to have the same dark green needles all the way through the year, and, typically, tall straight trunks with a few irregular coronets of living branches near the top and occasional dead and probcably broken boughs lower down. And so on. I need the same procedural code to be able to generate thousands to millions of recognisably distinct individual trees of a biome-appropriate variety of recognisable species across the map. 

They need to be individual and distinct at least partly because users are likely to recognise some of them as landmarks. And they need to be reproducably generatable from mininal seeds, because there's no way you can store that many models at anything like the level of detail I need.

## Conclusion

At this point it's a choice between rolling my own, or using Paul Speed's SimArboreal and enhancing it (hopefully with his approval and co-operation). In the short term, if I finalise the engine decision on jME3 (and I think I'm fairly close to doing that), SimArboreal will do as a placeholder. However, I will continue to look for other possibilities, because I have so many other things to build, and although I do want good quality forests I don't have that much time to invest in them just now.
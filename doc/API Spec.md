# API Spec

If the Gossip system is ever to be deployed in practice at all, it will need to be deployed as a library add-on to someone else's game, since in practice The Great Game will never be even nearly finished. The game engine already knows many of the things the Gossip system needs to know; that we need to define is an interface which allows Gossip, considered as a subsystem, to query the game engine.

My preference is still that Gossip should be written in a Lisp-like language - and, for now, in Clojure - simply because that is most comfortable to me. It needs bidirectional socket communication with the game engine, over which it sends either [extensible data notation](https://github.com/edn-format/edn) or [JavaScript Object Notation](https://www.json.org/json-en.html), with a preference for the former.

## Tracking what happens in the world

Existing game engines don't tend to track in convenient form things which have happened off-camera - indeed, mostly, things don't happen at all when the player isn't present. They don't even track much that happens when the player is present, and they usually track what they do track in fairly ad-hoc ways. So generally Gossip-as-library will have to maintain its own history of what has happened, and who knows what about what has happened; and will have to model the major life events of non-player characters happening off-camera (if this is done at all) itself.

## Interrogating lore

Many games have a great deal of lore and many lore texts. It's reasonable to expect each non-player character to know a certain amount of lore, certainly lore which is local to their home location, or relevant to their trade. In order to make that available to Gossip, you probably need to construct a searchable corpus of all the lore, which can be simply queried.

That obviously then needs to be filtered by what the respondent can be expected to know, but that's a problem Gossip has to handle anyway.

## Interrogating the map

### get-character-location *id*

Returns the player location in the world of the character with the specified id, as at minimum a three dimensional coordinate tuple, with heading; optionally with hierarchical region ids.

### get-potential-auditors *id*

### get-potential-auditors *id*, *volume*

Return an ordered list of ids of characters spatially close to the character with the specified id, ordered by their likelihood of being the character addressed (i.e. preferring characters in front of the character with the specified id to those off to the side or behind, on a sort of cardioid pattern). The set is bounded by the distance at which speech is deemed to be intelligible, which may be a constant, or maybe modified by some modelling of ambient noise, or the volume of the character's speech act.

### get-potentially-aware *id*

### get-potentially-aware *id*, *volume*

As above, but return a list of ids of characters within a distance in which speech may be heard but not intelligibly.
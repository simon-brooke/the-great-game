# Division of tasks between server and client

An alternative nomentclature I may use for this dichotomy would be _planner_ and _performer_; it would be the same dichotomy. 'Planner' and 'server' are synonyms; 'performer' and 'client' are synonyms.

## What do I mean by the 'server'?

There is something which manages game state and things like the gossip network, merchant network, and major world events. This something is almost certainly written in some form of Lisp; I'd prefer Clojure but I don't think it's performant enough so probably Common Lisp. This means that it has inevitable pauses for garbage collection. Underneath this is a database which handles persistent storage of game state, which is probably an SQL database and quite likely [SQLite](https://www.sqlite.org/index.html). 

The initial idea of The Great Game is that it is a single player game, but it actually doesn't need to be and it would be quite possible for one server to support multiple clients, each being used by a different player. 

The server/planner decides what each actor does, models what each character knows, plans and records all actions and transactions. It plans speech acts, and handles conversations which happen off screen, but hands speech texts over to the client/performer layer for actual performance. It also plans journeys as described in [Pathmaking](Pathmaking.html), but it doesn't deal with movement within a polygon or with collision avoidance. It deals with fights which happen off screen, but not those that happen on screen.

## What do I mean by the client?

There is something that renders an interesting and lively display of the part of the game world that the player can see from their current position. This display has to run without significant pauses &mdash; it's not OK, for example, for all conversation to stop suddenly in a market place just because the server is garbage collecting.

The client is written in some high level game engine system, possibly Unreal Engine (although for ideological reasons I'd prefer an open source one). 

The client/performer renders and animates everything the player character can see, and performs every sound the player character can hear. In doing this it is responsible for

1. The rendering of landscape, vegetation, buildings, furniture, and everything else that is fixed within the visible scene;
2. The animation of everything which moves within the visible scene, and, to facilitate this, detailed route planning and collision avoidance;
3. The performance of all speech acts and gestures, all musical performance, and the playing of all [foley](https://en.wikipedia.org/wiki/Foley_(filmmaking)) sounds;
4. Combat which happens in the field of view, including specifically all combat (including sparring) involving the player character. This means that the client/performer is the bit of the system which decides what blows are struck and whether they hit their targets, and consequently which character wins each fight. It reports this information back to the server.




# Division of tasks between server and client

## What do I mean by the 'server'?

There is something which manages game state and things like the gossip network, merchant network, and major world events. This something is almost certainly written in some form of Lisp; I'd prefer Clojure but I don't think it's performant enough so probably Common Lisp. This means that it has inevitable pauses for garbage collection. Underneath this is a database which handles persistent storage of game state, which is probably an SQL database and quite likely [SQLite](https://www.sqlite.org/index.html). 

The initial idea of The Great Game is that it is a single player game, but it actually doesn't need to be and it would be quite possible for one server to support multiple clients, each being used by a different player. 

## What do I mean by the client?

There is something that renders an interesting and lively display of the part of the game world that the player can see from their current position. This display has to run without significant pauses &mdash; it's not OK, for example, for all conversation to stop suddenly in a market place just because the server is garbage collecting.

The client is written in some high level game engine system, possibly Unreal Engine (although for ideological reasons I'd prefer an open source one). 




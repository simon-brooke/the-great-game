# Roadmap

This document outlines a plan to move forward from where I am in June 2021.

# JMonkeyEngine

[JMonkeyEngine](https://jmonkeyengine.org/) is not, at this time, an AAA game engine. But at the same time I'm never, really, going to build an AAA game. It is a working game engine which can display characters on screen in scenery and have them move around, and, actually, they can be fairly sophisticated. It will be resaonably easy to integrate Clojure code with JMonkeyEngine - easier than it would be to integrate either Clojure or Common Lisp with [Unreal Engine](https://www.unrealengine.com/) or [Unity 3D](https://unity.com/). As a significant added bonus, JMonkeyEngine is open source.

Consequently I plan to stop agonising about what game engine to use, and seriously focus on getting something working in JMonkeyEngine.

# Not Reinventing Wheels

JMonkeyEngine already has working code for walking animated characters, which is entirely adequate to proof-of-concept what I want to do. Rather than try to implement them myself, I just intend to use existing JMonkeyEngine code as far as possible.

# The 1Km World

I propose to build a 1Km square world, containing one settlement, as a proof of concept for

1. Procedural (genetic) buildings;
2. Procedural settlement planning;
3. Procedural characters, probably based on [MakeHuman 'Mass Produce' plugin](https://youtu.be/jRHnJX-TdT4), using walk animation based on [TestWalkingChar](https://github.com/jMonkeyEngine/jmonkeyengine/blob/master/jme3-examples/src/main/java/jme3test/bullet/TestWalkingChar.java);
4. Characters with their own hierarchy of needs, and their own means of planning to fulfil these;
5. Characters with individualised knowledge about the world;
6. Characters who can parse typed questions, and produce either a textual or audio response;
7. Characters with procedurally generated accents (very stretch goal)!
8. Characters who can listen to spoken questions, and produce audio responses.

At that stage, I have a technology demonstrator that will be interesting. It still leaves the big procedural world builder still to do, but it would be enough technology to get other people interested in the project.
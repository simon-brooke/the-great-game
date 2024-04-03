# Not my problem

## Introduction

This document is essentially a catalogue of side-tracks which I do not have to go down when implementing The Great Game. Solved problems; or problems which are common to many other games, so if I don't solve them someone else will. The object of doing this is to work down to a constrained set of problems which are genuinely things I'm trying to innovate, which I should focus on; which essentially come down to

1. Gossip
2. Reputation
3. Dynamic character motivation and action, and hence
4. Dynamic economy, and
5. Dynamic plot
6. Procedural ('genetic') buildings.

(Note that although procedural vegetation is in principle a solved problem and so I don't need to solve it, I need repeatable procedural vegetation so I need to be a bit careful about the procedural vegetation library I pick).

## Animation

I envisage a well rendered three dimensional world in which many non-player characters interact with one another and with the player character. All of my characters are either human, quadrupeds, or birds (my dragons animate like very large birds). The humans are all just human; there are infants, children, adolescents, youths, adults, elderly; there are multiple racial types &mdash; but they're all human. Systems for creating varied distinct human models exist; systems for animating them exist; systems for applying and animating clothing exist. Even systems for animating continuous speech exist.

## Rendering

Ideally I'd like a stylised, not-quite-photorealistic render, because such things age, in my opinion, better than things which do seek to be photorealistic. But actually that does not matter very much; the game won't be made or broken by its rendering. Photorealistic renders are sort of the current default, that most game engines.

## Continuous Open World

I've done a great deal of thinking about how to render a continuous open world over the years, and I think at least some of it is reasonably good; but I haven't actually written any code, and in the same time period other people have written continuous open world libraries which do work, so I'd be much better choosing an existing one.

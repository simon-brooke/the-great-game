# Minimum Viable Product, and a road map

Right, I'm bogged down thinking about the immensity of what I want to build, so I'm achieving nothing. So the first thing I need to state is what the Minimum Viable Product is, and the second is to outline a rough road map which takes us forwards a few steps from the MVP.

The core idea here is to have a game world in which you can just say anything you like to game characters, and they can say sensible things back.

But actually, I know that speech to text can be reasonably effectively done; and I believe with a slightly lower degree of confidence that text to convincing speech can also be done.

I also know that the movement of a character around a convincing three dimensional representation of a world can be done, but that a great deal of effort is needed to build that world.

The minimum viable product does not need to demonstrate features which people have reasonable confidence can be done. What I need to demonstrate is the things which people haven't seen done, or haven't seen done well.

## Prototype one: the minimum viable product

The minimum viable product can have just a text adventure style interface:

> You are in the market square. It is mid morning. To the north is the guild hall; to the east there are market stalls; to the south is the residence; to the west is the bridge gate.

> There is a merchant here; there is a guardsman here.

To which the user can type (for example)

> Say to the guardsman, "Can you direct me to Master Dalwhiel's house?"

Within that interface, you should be able to interact with characters who:

1. have different levels of knowledge of the world, partly driven by their age, trade and personal history;
2. move about and exchange gossip, even when the player is not present to see/hear this;
3. have different attitudes towards the player and other characters, which will be modified by what they learn in gossip;
4. have their own hierarchies of needs, which they make plans to satisfy;
5. have homes and trades;
6. will respond to speech addressed to them by the player depending on their attitude to the player, how busy they are and their knowledge of the world; and
7. as a stretch goal, will have different dialects in which they will express their responses to the player.

There should be one or two multiple decision point quests in this world which can be resolved by talking to characters.

## Prototype two: adding organic quests

Extends prototype one only by adding [organic quests](Organic_Quests).

## Prototype three: voice interaction

Extends prototype two by adding speech to text, so that the player can directly talk (via a microphone) to characters, and text to speech, so that the system can voice the characters' responses.

Different characters should have different voices.

## Prototype four: performative speech

This one is hard because I'm not absolutely sure how I can do it, but I need characters' voices to convey emotion; the player needs to know from their voice whether they are angry, or frightened, or impatient, or bored. 

There is a [W3C specification](https://www.w3.org/TR/speech-synthesis11/) for an XML markup for speech performance, and I can certainly generate that, but I'd need to find a text-to-speech library which could consume it. There's also a separate [specification](https://www.w3.org/TR/pronunciation-lexicon/) to associate pronunciations with lexical tokens, which is also potentially useful, especially for names.

Google has a '[Cloud Text-to-Speech](https://cloud.google.com/text-to-speech/docs/ssml)' service which understands SSML and might be good enough for a demo but is more likely just embarrassingly bad.

## Prototype five traversible world

Now, a small section of a three dimensional open world, with at this stage simple block buildings that the player cannot enter, within which the characters act out their lives.

Stretch goal, [JALI](https://www.youtube.com/watch?v=uFIxiz0jwRE)-like lip sync.
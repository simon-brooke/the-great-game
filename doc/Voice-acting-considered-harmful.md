# Voice acting considered harmful

#### Wednesday, 25 February 2015

![The Witcher: Conversation with Kalkstein](https://3.bp.blogspot.com/-ZI90HLjEcuo/VO4f-yXP3sI/AAAAAAAAZt4/C0hQ7hScWyM/s1600/witcher_conversation.jpg)

 Long, long, time ago, I can still remember when... we played (and wrote) adventure games where the user typed at a command line, and the system printed back at them. A Read-Eval-Print loop in the classic Lisp sense, and I wrote my adventure games in Lisp. I used the same opportunistic parser whether the developer was building the game
  Create a new room north of here called dungeon-3 the player was playing the game
  Pick up the rusty sword and go north or the player was talking to a non-player character
  Say to the wizard 'can you tell me the way to the castle' Of course, the parser didn't 'understand' English. It worked on trees of words, in which terminal nodes were actions and branching nodes were key words, and it had the property that any word it didn't recognise at that point in sentence was a noise word and could be ignored. A few special hacks (such as 'the', 'a', or 'an' was an indicator that what came next was probably a noun phrase, and thus that if there was more than one sword in the player's immediate environment the one that was wanted was the one tagged with the adjective 'rusty'), and you ended up with a parser that most of the time convincingly interpreted most of what the player threw at it.

 Text adventures fell into desuetude partly because they weren't graphic, but mainly because people didn't find typing natural, or became dissatisfied with the repertoire of their parsers. Trying to find exactly the right combination tokens to persuade the game to carry out some simple action is not 'fun', it's just frustrating, and it turned people off. Which is a shame because just at the time when people were abandoning text adventures we were beginning to have command parsers which were actually pretty good. Mine, I think, were good - you could have a pretty natural conversation with them, and in 'building' mode, when it hit a 'sorry I don't understand' point, it allowed you to input a path of keywords and a Lisp function so that in future it would understand.

 So much, so [Eliza](http://www.csee.umbc.edu/courses/331/papers/eliza.html).

 Modern role-playing games - the evolutionary successors of those high and far off text adventures - don't have text input. Instead, at each stage in a conversation, the user is offered a choice of three or four canned responses, and can pick one; very often what the player's character actually says then differs from the text the user has chosen, often with differences of nuance which the user feels (s)he didn't intend. And the non-player-character's response is similarly canned. Indeed, the vast majority of non-player characters in most games have a 'repertoire', if one may call it that, of only one sentence. Others will have one shallow conversational tree, addressing one minor quest or plot-point.

 If you want to talk to them about anything else - well, you just can't.

 Only a very few key non-player characters will have a large repertoire of conversational trees, relevant to all parts of the plot. And even those trees are not deep. You soon exhaust them; the characters' ability to simulate real agency just isn't there.

 I first wrote about the limiting effects of voice acting in [my review of the original Witcher game](../../2008/02/the-witcher-story-telling-of-high-order.html), back in 2008; things haven't got better.


## On phones: speaking

 In my pocket I carry a phone. It's not big: 127 x 64.9 x 8.6mm. A small thing.

 When I first used Android phones for navigation, I used to delight in their pronunciation of Scots placenames - pronouncing them phonetically, as spelled, and as though their spelling were modern English. What's delightful about Scots placenames is that they are linguistically and orthographically so varied - their components may be Brythonic, Goidaelic, Anglian, Norn, French, English, or even Latin; and very frequently they combine elements of more than one language (Benlaw Hill, anyone? Derrywoodwachy?).

 Yes, gentle reader, this does seem a long way from game design; be patient, I'm getting there. But I'm going to digress even further for first...

 There have been orthographic changes, and pronunciation changes consequent on orthographic changes. For example, medieval Scots used the letter [Yogh](http://en.wikipedia.org/wiki/Yogh) (ȝ), which isn't present in the English alphabet. So when Edinburgh printers in the early modern period bought type for their printing presses from England, there was no Yogh in the font. So they substituted Zed. So we get names like Dalȝiel, Kirkgunȝeon, Menȝies, Cockenȝie. How do you pronounce them?

 The letter that looks like a 'z' is pronounced rather like a 'y'; so

* Deeyell
* Kirkgunyeon
* Mingis

 and... drumroll...

* Cockenzie.

 What happened?

 Well, Dalȝiel and Menȝies are personal names, and people are protective of their own names. Kirkgunȝeon is a small, unimportant place, and all the locals know how it is pronounced. Scots folk, are, after all, used to Scots orthography and its peculiarities. So those names haven't changed.

 But at Cockenȝie, another small, unimportant place, a nuclear power station was built. The nuclear power station was built by people (mostly) from England, who didn't know about Yogh or the peculiarities of Scots orthography - and were possibly too arrogant to care. So they called it 'Cockenzie'. And as there were so many more of them and they had so much higher status than the locals, their name stuck, and nowadays even local people mostly say 'Cockenzie', as though it were spelled with a Zed. Because, of course, it is spelled with a Zed. Because, as any British schoolchild knows, there's no Yogh in the alphabet.

 Except, of course, when there is.

 Another more interesting example of the same thing is '[Kirkcudbright](http://www.journeyman.cc/placenames/place?id=153)'. It's a town built around the kirk (church) of saint Cuthbert. So how does it come to have a 'd' in it? And why is it pronounced 'Kirkoobry'? Well, the venerable Cuthbert pronounced his name in a way which would be represented in modern English as 'Coothbrecht', but he spelled it 'Cuðbrecht'. See that 'ð'? That's not a 'd', it's an Eth. Because Cuðbrecht was Anglian, and the Anglian alphabet had [Eth](http://en.wikipedia.org/wiki/Eth); it's pronounced as a soft 'th', and Icelandic still has it (as well as Thorn, þ, a hard 'th' sound). Medieval scribes didn't know about Eth, so in copying out ð they wrote the more familiar d. The local people, however, mostly couldn't read, so the pronunciation of the name didn't change with the change in spelling (although the pronunciation, too, has drifted a little with time).

 So, in brief, pronouncing Scots placenames is hard, and there are a lot of curious rules, and consequently it's not surprising that five years ago, listening to Android's pronunciation of Scots placenames was really funny.

 But what's really curious is that now it isn't. Now, it rarely makes a mistake. Now, Android can do text to speech on unusual and perverse orthography, and get it right better than 95% of the time - and manage a reasonably natural speaking voice while doing so. On a small, low power machine which fits in my pocket.


## On phones: listening

 But navigation is not all I can do with my phone. I can also dictate. By which I don't mean I can make a voice recording, play it back later and type what I hear, although, of course, I can. I mean I can dictate, for example, an email, and see it in text on my phone before I send it. It quickly learned my peculiarities of diction, and it now needs very little correction. On a small, low power machine which fits in my pocket.


## And breathe

 Right, so where am I going with all this? Well, we interact with modern computer role playing games through very restricted, entirely scripted dialogues. Why do we do so? Why, on our modern machines with huge amounts of store, do our non-player characters - and worse still, our player character, our own avatar - have such restricted repertoires?

 Because they are voice acted. Don't get me wrong, voice acting makes a game far more engaging. But for voice acting to work, the people doing the acting have to know not only the full range of sentences that their character is going to speak, but also roughly how they feel (angry? sad? excited?) when they say it. Ten years ago, voice acting was probably the only way you could have got this immediacy into games, because ten years ago, text-to-speech systems were pretty crude - think of Stephen Hawking's voice synthesiser. But now, Edinburgh University's [open source synthesiser](http://www.cstr.ed.ac.uk/projects/festival/morevoices.html) is pretty good, and comes with twenty-four voices (and seeing it's open source, you can of course add your own). Speech to text was probably better ten years ago - think of [Dragon Naturally Speaking](http://en.wikipedia.org/wiki/Dragon_NaturallySpeaking) - but it was proprietary software, and used a fair proportion of a machine's horsepower. Now there's (among others) Carnegie Mellon's open source [Sphinx](http://cmusphinx.sourceforge.net/) engine, which can quickly adapt to your voice.

 So, we have text-to-speech engines which can generate from samples of many different voices, and speech to text engines which can easily be tuned to your particular voice. There's even a program called [Voice Attack](http://www.voiceattack.com/), built on top of Microsoft's proprietary speech to text engine, which already allows you to [control games with speech](https://www.youtube.com/watch?v=8dnJ--pSjdE). Where does that take us?

 Well, we already know how to make sophisticated natural language parsers for text, given moderately limited domains - we don't need full natural language comprehension here.


## You may think it's a long way down the road to the chemist

 There are things one needs to know in a game world. For example: I need a sword, where's the nearest swordsmith? In a real quasi-medieval world, certainly every soldier would be able to tell you, and everyone from the swordsmith's town or village. Very celebrated swordsmiths would be known more widely.

 And the thing is, the game engine knows where the nearest swordsmith is. It knows what potion will heal what wound, and what herbs and what tincture to use to make it. It knows which meats are good to eat, and which inns have rooms free. It knows good campsites. It knows where there be dragons. It knows where the treasure is hid. It knows - as far as the game and its plot are concerned - everything.

 So to make an in-game Siri - an omniscient companion you could ask anything of - would be easy. Trivial. It also wouldn't add verisimilitude to the game. But to model which non-player characters know what is not that much harder. Local people know what's where in their locality. Merchants know the prices in nearby markets. They, and minstrels, know the game-world's news - major events that affect the plot. Apothecaries, alchemists and witches know the properties of herbs and minerals.

 And to model which non-player characters are friendly, and willing to answer your every question; which neutral or busy, and liable to answer tersely; and which actively hostile, and likely, if they answer at all, to deliberately mislead - that's not very much harder.

 I'm not arguing that voice acting, and scripted dialogue trees, should be done away with altogether. They still have a use, as cutscenes do, to advance plot. And I'm not suggesting that we use voice to control the player characters movements and actions - I'm not not suggesting that we should say 'run north; attack the troll with the rusty sword'. Keyboards and mice may be awkward ways to control action, but they're better than that. Bur I am suggesting that one should be able to talk to any (supposedly sentient) character in the game, and have them talk reasonably sensibly back. As one can already do physically in wandering an open world, a full voice interaction system would allow one to go off piste - to leave the limited, constrained pre-scripted interaction of the voice-acted dialogue tree. And that has got to make our worlds, and our interactions with them, richer, more surprising, more engaging.

 A hybrid system needn't be hard to achieve, needn't be jarring in use. You can record the phonemes of your voice actor's voice, so that the same character will have roughly the same voice - the same timbre, the same vowel sounds, the same characteristics of  pronunciation - whether in a voice acted dialogue or in a generated one.

 We don't need to let voice acting limit the repertoires of our characters any more. And we shouldn't.

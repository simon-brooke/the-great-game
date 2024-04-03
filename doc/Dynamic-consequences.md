# On the consequences of a dynamic game environment for storytelling

First, a framing disclaimer: in [Racundra's First Cruise](https://books.google.co.uk/books?id=Ol1-DwAAQBAJ&lpg=PP1&pg=PT77#v=twopage&q&f=false), Arthur Ransome describes coming across a half built - and by the time he saw it, already obsolete - wooden sailing ship, in a Baltic forest. An old man was building it, by himself. He had been building it since he had been a young man. It's clear that Ransome believed the ship would never be finished. It's not clear whether the old man believed that it would, but nevertheless he was building it.

I will never build a complete version of The Great Game; it will probably never even be a playable prototype. It is a minor side-project of someone who

1. Is old and ill, and consequently has inconsistent levels of energy and concentration;
2. Has other things to do in the real world which necessarily take precedence.

Nevertheless, in making design choices I want to specify something which could be built, which could, except for the technical innovations I'm trying myself to build, be built with the existing state of the art, and which if built, would be engaging and interesting to play.

The defining characteristic of Role Playing Games - the subcategory of games in which I am interested - is that the actions, decisions and choices of the player make a significant difference to the outcome of the plot, significantly affect change in the world. This already raises challenges for the cinematic elements in telling the game story, and those cinematic elements are one of the key rewards to the player, one of the elements of the game's presentation which most build, and hold, player engagement. These challenges are clearly expressed in two very good videos I've watched recently: [Who's Commanding Shepard in Mass Effect?](https://youtu.be/bm0S4cn_rfw), which discusses how much control the player actually has/should have over the decisions of the character they play as; and [What Happened with Mass Effect Andromeda’s Animation?](https://youtu.be/NmLPpcVQFJM), which discusses how the more control the player has, the bigger the task of authoring animation of all conversations and plot events becomes.

There are two key innovations I want to make in The Great Game which set it apart from existing Role Playing Games, both of which make the production of engaging cinematic presentation of conversation more difficult, nd I'll handle each in turn. But before I do, there's something I need to make clear about the nature of video games themselves: what they are for. Video games are a vehicle to tell stories, to convey narrative. They're a rich vehicle, because the narrative is not fixed: it is at least to some degree mutable, responsive to the input of the audience: the player.

Clear? Let's move on.

The innovations I am interested in are

## Unconstrained natural speech input/output

I want the player to be able to interact with non-player characters (and, indeed, potentially with other player characters, in a multi-player context) simply by speaking to them. This means that the things the player character says cannot be scripted: there is no way for the game designer to predict the full repertoire of the player's input. It also means that the game must construct, and put into the mouth of the non-player character being addressed, an appropriate response, given

1. The speech interpretation engine's interpretation of what it is the player said;
2. The immediate game and plot context;
3. The particular non-player character addressed's knowledge of the game world;
4. The particular non-player character's attitude towards the player;
5. The particular non-player character's speech idiosyncracies, dialect, and voice

and it must be pretty clear that the full range of potential responses is extremely large. Consequently, it's impossible that all non-player character speech acts can be voice acted; rather, this sort of generated speech must be synthesised. But a consequence of this is that the non-player character's facial animation during the conversation also cannot be motion captured from a human actor; rather, [it, too, must be synthesized](https://youtu.be/fa3_Mfqu8KA).

This doesn't mean that speech acts by non-player characters which make plot points or advance the narrative can't be voice acted, but it does mean that the voice acting must be consistent with the simulated voice used for that non-player character - which is to say, probably, that the non-player character must use a synthetic voice derived from the voice performance of that particular voice actor in that role.

## Dynamic game environment

Modern Role Playing Games are, in effect, extremely complex state machines: if you do the same things in the same sequence, the same outcomes will always occur. In a world full of monsters, bandits, warring armies and other dangers, the same quest givers will be in the same places at the same times. They are clockwork worlds, filled with clockwork automata. Of course, this has the advantage that is makes testing easier - and in a game with a complex branching narrative and many quests, testing is inevitably hard.

Interestingly, [Kenshi](https://lofigames.com/) &mdash; a game I'm increasingly impressed and influenced by &mdash; is not quite clockwork in this sense. As the player upsets the equilibrium of the game's political economy, factions not impacted negatively will move against competing factions which are impacted negatively, in a way which *may* be scripted, but it's so well done it's hard to tell.

My vision for The Great Game is different. It is that the economy - and with it, the day to day choices of non-player characters - should be modelled. This means, non-player characters may unexpectedly die. Of course, you could implement a tag for plot-relevant characters which prevents them being killed (except when required by the plot).

## Plot follows player

As Role Playing Games have moved towards open worlds - where the player's movement in the environment is relatively unconstrained - the clockwork has become strained. The player has to get to particular locations where particular events happen, and so the player has to be very heavily signposted. Sometimes the mark you have to hit to trigger the next advance of the plot can be extremely awkward; [an example from Cyberpunk 2077](https://youtu.be/GEYkuctBUYE?t=2990) is finding the right spot, in the quest 'They Won't Go When I Go', to trigger the button which raises the cross. 

Another solution - which I'd like to explore - is 'plot follows character'. The player is free to wander at will in the world, and plot relevant events will happen on their path. And by that I don't mean that we associate a set of non-player characters which each quest - as current Role Playing Games do - and then uproot the whole set from wherever they normally live in the world and dump them down in the player's path; but rather, for each role in a quest or plot event, we define a set of characteristics required to fulfil that role, and then, when the player comes to a place where there are a set of characters who have those characteristics, the quest or plot event will happen.

## Cut scenes, cinematics and rewarding the player

There's no doubt at all that 'cut scenes' - in effect, short movies spliced into game play during which the player has no decisions to make but can simply watch the scene unroll - are elements of modern games which players enjoy, and see to some extent as 'rewards'. And in many games, these are beautifully constructed works. It is a very widely held view that the quality of cutscenes depends to a large degree on human authorship. The choices I've made above:

1. We can't always know exactly what non-player characters will say (although perhaps we can in the context of cut scenes where the player has no input);
2. We can't always know exactly which non-player characters will speak the lines;
3. We can't predict what a non-player character will say in response to a question, or how long that will take;
4. We can't always know where any particular plot event will take place;

each, make the task of authoring an animation harder. The general summary of what I'm saying here is that, although in animating a conversation or cutscene what the animator is essentially animating is the skeletons of the characters, and, provided that all character models are rigged on essentially similar skeletons, substituting one character model for another in an animated scene isn't a huge issue, with so much unknowable it is impossible that hand-authoring will be practicable, and so a lot will depend on the quality of the conversation system not merely to to produce convincingly enunciated and emoted sound, but also appropriate character animation and attractive cinematography. As you will have learned from the Mass Effect analysis videos I linked to above, that's a big ask.

Essentially the gamble here is that players will find the much richer conversations, and consequent emergent gameplay, possible with non-player charcaters who have dynamic knowledge about their world sufficiently engaging to compensate for a less compelling cinematic experience. I believe that they would; but really the only way to find out would be to try.

Interestingly, an [early preview](https://youtu.be/VwwZx5t5MIc?t=327) of CD Project Red's [Cyberpunk 2077](https://www.cyberpunk.net/us/en/cyberpunk-2077) has relatively few cutscenes, suggesting that these very experienced storytellers don't feel they need cutscenes either to tell their story or maintain player engagement.

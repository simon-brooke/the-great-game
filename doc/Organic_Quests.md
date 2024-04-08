# Organic Quests

The structure of a modern Role Playing Came revolves around 'quests': tasks that the player character is invited to do, either by the framing narrative of the game or by some non-player character ('the Quest Giver'). Normally there is one core quest which provides the overarching narrative for the whole game. [Wikipedia](https://en.wikipedia.org/wiki/Quest_(gaming)) offers a typology of quests as follows:

1. Kill quests
2. Combo quests
3. Delivery quests
4. Gather quests
5. Escort quests
6. Syntax quests
7. Hybrids

'Gather quests' are more frequently referred to in the literature as 'fetch quests', and 'kill quests' are simply a specialised form of fetch quest where the item to be fetched is a trophy of the kill. And the trophy could be just the knowledge that the kill has happened. A delivery quest is a sort of reverse fetch quest: instead of going to some location or NPC and getting a specific item to return to the quest giver, the player is tasked to take a specific item from the quest giver to some location or NPC.

Note, however, that if we consider a delivery quest to have four locations, where some of these locations may be coincident, then a delivery quest and a fetch quest become the same thing. Thus

1. The location of the quest giver at the beginning of the quest;
2. The location from which the quest object must be collected;
3. The location to which the quest object must be delivered;
4. The location of the quest giver at the end of the quest.

This characterisation assumes that at the end of each quest, the player must rendezvous with the quest giver, either to report completion or to collect a reward. Obviously, there could be some quests where this fourth location is not required, because there is no need to report back (for example, if the quest giver was dying/has died) and no reward to be collected.

Note that a location is not necessarily a fixed x/y location on the map; in a kill quest, for example, location 2 is the current location of the target, and moves when the target moves; location 3 and 4 are both normally the current location of the quest giver, and move when the quest giver moves.

Hybrids are in effect chains of quests: do this task in order to get this precondition of this other task, in order to get the overall objective; obviously such chains can be deep and involved - the 'main quest' of every role playing game I know of is a chain or hybrid quest.

My understanding is that what Wikipedia means by a 'syntax quest' is what one would normally call a puzzle.

An escort quest is typically a request to take a specified non-player character safely through a dangerous area. It is thus very similar to a delivery quest, except that you are escorting a character rather than delivering an item.

Combo quests are not, in my opinion, particularly relevant to the sorts of game we're discussing here.

So essentially quests break down into three core types

1. Fetch and deliver quests
2. Escort quests
3. Puzzles

which are combined together into more or less complex chains, where the simplest chain is a single quest.

Given that quests are as simple as this, it's obvious that narrative sophistication is required to make them interesting; and this point is clearly made by some variants of roguelike games which procedurally generate quests: they're generally pretty dull. By contrast, the Witcher series is full of fetch-quests which are made to really matter by being wrapped in interesting character interaction and narrative plausibility. Very often this takes the form of tragedy: as one reviewer pointed out, the missing relatives that Geralt is asked to find generally turn out to be (horribly) dead. In other words, creative scripting tends to deliver much more narratively satisfying quests than is usually delivered by procedural generation.

But, if we're thinking of a game with much more intelligent non-player characters with much more conversational repertoir, as I am, can satisfying quests emerge organically? In space trading games such as [Elite](https://www.telegraph.co.uk/games/11051122/Elite-the-game-that-changed-the-world.html), a primary activity is moving goods from markets with surplus (and thus low prices) to markets with shortage (and thus high prices). This is, in effect, a game made up of deliver quests - but rather than deliver quests which are scripted, they are deliver quests which arise organically out of the structure of the game world.

I already have working code for non-player character merchants, who move goods from city to city based on market information available to them. For player characters to join in this trading is an organic activity emerging from the structure of the world. But moving merchants provides a market opportunity for bandits, who can intercept and steal cargoes, and so for mercenaries, who can protect cargoes from bandits, and so on. And because I have an architecture that allows non-player characters to change occupation to fill economic niches, there will be non-player characters in all these niches.

Where a non-player character can act, so can a player character: when a (non-player character) merchant seeks to hire a caravan guard and a player character responds, that's an organic escort quest.

The key idea behind organic quests is that the circumstance and requirements for quests emerge as an emergent behaviour out of the mechanics of the game world. A non-player character doesn't know that there is a player character who is different from them; rather, when a non-player character needs something they can't readily achieve for themselves, they will ask other characters to help, and that may include the player character.

This means, of course, that characters need a goal-seeking planning algorithm to decide their actions, with one option in any plan being 'ask for help'. Thus, 'asking for help' becomes a mechanism within the game, a normal behaviour. Ideally non-player characters will keep track of quite complex webs of loyalty and of obligation - debts of honour, duties of hospitality, collective loyalties. So that, if you do a favour for some character in the world, that character's tribe, friends, obligation circle, whatever, are now more likely to do favours for you.

Obviously, this doesn't stop you doing jobs you get directly paid/rewarded for, but I'd like the web of obligation to be at least potentially much richer than just tit for tat.

Related to this notion is the notion that, if you are asked to do a task by a character and you do it well, whether for pay or as a favour, your reputation for being competent in tasks of that kind will improve and the more likely it is that other characters will ask you to do similar tasks; and this will apply to virtually anything another character can ask of you in the game world, from carrying out an assassination to delivering a message to finding a quantiy of some specific commodity to having sex.

So quests can emerge organically from the mechanics of the world and be richly varied; I'm confident that will work. What I'm not confident of is that they can be narratively satisfying. This relates directly to the generation of speech.

## Stuff to consider

The games [Middle Earth: Shadow of Mordor](https://en.wikipedia.org/wiki/Middle-earth:_Shadow_of_Mordor), and [Middle Earth: Shadow of War](https://en.wikipedia.org/wiki/Middle-earth:_Shadow_of_War) have a procedural story system called [Nemesis](https://youtu.be/Lm_AzK27mZY), which is worth a look.

There's an interesting [critique of Red Dead Redemption 2](https://www.youtube.com/watch?v=MvJPKOLDSos&feature=emb_logo) which is relevant to what I'm saying here.
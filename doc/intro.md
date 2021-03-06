# Introduction to the-great-game

# The Great Game

In this essay I'm going to try to pull together a number of my architectural
ideas about the Great Game which I know I'm never actually going to build -
because it's vastly too big for any one person to build - into one overall
vision.

So, firstly, how does one characterise this game?

It has strong elements of a Role Playing Game, as currently understood; some
elements of a Simulation Game; some elements of a God Game. But what I see
it as is fundamentally a sandbox in which the player(s) can explore ideas
about human conflicts and how to resolve them, without immediate
real-world consequences. It's also a sandbox in which story tellers can tell
stories, but that's essentially a side-effect - a consequence of the fact
that I need to be able to use it to tell stories, in order to create initial
threads of narrative from which players can start their exploration.

Note that, by 'conflict', here, I explicitly do not mean 'killing people',
or even 'killing non-player characters'. I have [written extensively](Voice-acting-considered-harmful.html)
about the problem in many current video games that all too often the only
way of interacting with non-player characters is to kill them. Killing
people should be one of the potential ways of resolving conflicts, because
that is reality, but negotiation must be another.

So this is a game in which rich interaction with non-player characters is
possible. The NPCs have individual knowledge and individual agency: they
have intentions, aspirations and desires. They also have a wide dynamic
repertoire of speech.

## Previous essays that are relevant

* [The spread of knowledge in a large game world](The-spread-of-knowledge-in-a-large-game-world.html) (2008) discusses what individual non-player characters know, and how to model dynamic updates to their knowledge;
* [Settling a game world](https://blog.journeyman.cc/2009/12/settling-game-world.html) (2009) gives rough outline of ideas about creating the environment, including modelling things like soil fertility, local building materials, and consequently local architecture;
* [Tessellated multi-layer height map](https://blog.journeyman.cc/2013/07/tessellated-multi-layer-height-map.html) (2013) gives ideas for how a designed geography for a very large world could be stored relatively economically;
* [Genetic Buildings](https://blog.journeyman.cc/2013/07/genetic-buildings.html) (2013) sketches algorithms which would allow procedurally-generated buildings to be site-appropriate, broadly variable and reproducable;
* [Populating a game world](Populating-a-game-world.html) (2013) provides outline algorithms for how a world can be populated, and how organic mixes of trades and crafts can be modelled;
* [Modelling the change from rural to urban](https://blog.journeyman.cc/2013/07/modelling-change-from-rural-to-urban.html) (2013) describes the idea of procedurally modelling settlements, but it is grid-based and not particularly satisfactory and has largely been superceded in my thinking;
* [Of pigeons, and long distance messaging in a game world](https://blog.journeyman.cc/2013/10/of-pigeons-and-long-distance-messaging.html) (2013) builds on ideas about flows of information;
* [Modelling rural to urban, take two](https://blog.journeyman.cc/2013/10/modelling-rural-to-urban-take-two.html) (2013) revisited the idea of modelling organic settlement structures, trying to find algorithms which would naturally produce more persuasive settlement models, including further ideas on the procedural generation of buildings;
* [More on modelling rivers](https://blog.journeyman.cc/2014/09/more-on-modelling-rivers.html) (2014) talks about modelling hydrology, with implications for soil fertility;
* [Modelling settlement with cellular automata](https://blog.journeyman.cc/2014/08/modelling-settlement-with-cellular.html) (2014) talks about successful implementation of algorithms to model vegetative environment, human settlement and the impact of human settlement on the environment;
* [Voice acting considered harmful](https://blog.journeyman.cc/2015/02/voice-acting-considered-harmful.html) (2015) outlines the ideas behind full speech interaction with non-player characters, and modelling what those non-player characters should be able to speak about;
* [Baking the world](Baking-the-world.html) (2019) an outline of the overall process of creating a world.

## Organic and emergent game-play

If a world is [dynamically populated](https://blog.journeyman.cc/2014/08/modelling-settlement-with-cellular.html), with [dynamic allocation of livelihoods](https://blog.journeyman.cc/2013/07/populating-game-world.html) then several
aspects of gameplay will emerge organically. First, of course, is just
exploring; in a dynamically changing world there will always be more to
explore, and it will be different in each restart of the game.

### Trading

Second is trading. If there are markets, and merchants moving between them,
then the prices in the markets can be modelled dynamically. Markets in out
of the way places which produce otherwise scarce primary products will have
low prices for those products, and the player can make profit by carrying
product to more urban markets where the prices are higher. Yes, that isn't
in itself a satisfying game, but if the player is in any case travelling
from place to place it may be a useful side-game.

### Dynamic quests

Third is aiding. If dynamic events happen in the world, then, for some
non-player characters, some of those events will be negative. A merchant
who is robbed, a shepherd whose sheep have been driven off by wolves, a
family whose child has been abducted, will seek help. These requests for
aid can become dynamically generated quests. Dynamic quests have a bad
reputation, but with better modelling of NPC behaviour and better
repertoire, I think that satisfying dynamic quests shouldn't be too
difficult to do - they are, after all, just another instance of procedural
generation.

So in the 'abducted child' quest, for example, there are a range of things
which may be going on:

Did the child go willingly? If so, were they fleeing an abusive home, or
seeking a better life, or eloping with a lover, or seduced by a bad actor?
Do they want to go back, or do they want to stay away? If they want to stay
away, can you force them to go back? Alternatively, can you negotiate a
state of peace between the abductor and the family?

So there are a range of possible outcomes:

1. The player talks to the child and decides to take no further action;
2. The player abducts/liberates the child from the abductors and returns the child to their parents;
3. The player abducts/liberates the child from the abductors and allows the child to choose which to stay with;
4. The player kills off the abductors and returns the child to their parents;
5. The player kills off the abductors and allows the child to choose where to go;
6. The player negotiates a ransom and returns the child to their parents;
7. The player negotiates a reconcilliation between the parents and the abductor, and the child chooses which to stay with.

Generally, (1) and (2) above will enhance the player's reputation for getting
stuff done, (4) or (5) enhance their reputation as a fighter, and (6) or (7)
as a negotiator. However, while (2), (4) and (7) will increase the player's
favour with the parents and consequently with their wider circly, returning
an unwilling child to its parents will have a much more uncertain outcome.
Allowing the child to choose will increase the player's favour with the child,
but probably decrease it with the parents.

Thus there is a fairly rich dynamic quest graph for this general quest, and
it's conditioned by many factors. For example, if the player is already viewed
favourably by the abductor, then negotiation will be much easier; if
negatively, negotiation may be impossible. How favourably the child views the
player will affect how willing the child is to go with the player (and, of
course, if the abductor was the child's lover, then killing the abductor will
sharply reduce the child's favour towards the player).

There's a similarly rich quest graph regarding goods in contested ownership,
e.g. they've been stolen from a merchant (or the merchant claims that they
have) but are now held by someone who claims that they bought them honestly.
And so on: rich abstract quest graphs can be derived for quite a lot of c
onflicts which will arise dynamically between non-player characters.

To make dynamic quests work, of course, you need a dynamic world; a world in
which conflicts can arise. A world in which traders trade, robbers rob, lovers
love, haters hate, scandal-mongers make scandal, organically and dynamically
whether the player is there or not, and where news of these events will filter
through to the player through the [gossip network](https://blog.journeyman.cc/2008/04/the-spread-of-knowledge-in-large-game.html) also organically and dynamically.

## Extending the story

Dynamic quests do not mean that there should not be, or cannot be, scripted
quests, and, indeed, I'm sure that there should be; which brings us to the
next point.

In a very large world, writing enough story to have satisfyingly constructed
narratives everywhere the player goes is a very large job. But I became
excited by role playing games, not so much by the original Neverwinter Nights
itself, but by the authoring tools which came free with it. There were
thousands of user-contributed add-on 'mods', mostly stories and quests,
for Neverwinter Nights, of which I wrote several - and I greatly enjoyed
doing so.

Later on, with Carrol Dufault and others (including James Semple who wrote
[wonderful original music](https://www.youtube.com/watch?v=1afAIojPZ1w)), I went on to write a [new story for The Witcher](https://www.moddb.com/mods/birth-virgins) which won CD Project's prize for best mod.

For me, writing story is every bit as compelling an activity as playing it,
and I know I'm not alone in that. So I feel strongly that if **The Great Game**
were ever released, it wouldn't be necessary to fully populate the map with
scripted story; rather, the release should include authoring tools which
would allow players to extend story, and documentation which set the overall
framework within which story could be extended. And then there should be a
mechanism by which extensions of sufficient quality could be merged into the
overall game for all players, and the authors of those stories rewarded with
some degree of recognition.

## Aspirations and goals

(In what follows I shall use the word 'agent' to cover both player characters
and - but more particularly - non-player characters) In order to build a
dynamic world in which we can play with ideas about human conflict, we need
to model those things which give rise to conflict, which means we need to
model not merely short term aspirations and goals but also long term
aspirations and goals; agents have a [hierarchy of needs](https://en.wikipedia.org/wiki/Maslow%27s_hierarchy_of_needs) and to achieve
those needs they must have a hierarchy of goals.

Not everyone has the same 'top level goal'; different agents should have
different top level goals. The following are some plausible top level goals:

1. **Ancestor**: To be succeeded by children and grandchildren, to found a dynasty;
2. **Master**: To become highly esteemed as a master of your craft;
3. **Explorer**: To be widely travelled, to see the world;
4. **Climber**: To climb as high as possible in the social hierarchy, to become Tyrranos or Imperator;
5. **Conqueror**: To build the largest possible realm under your control;
6. **Citizen**: To build a secure and comfortable home, able to feed and protect those within it;

In this typology, a hero is a master soldier, and a sage a master scholar.
Each of these types should be capable of being 'scored' by a real function
which can be written over real parameters which exist in the game. Some are
easy:

1. **Ancestor**: how many living descendants does this agent have now?
2. **Master**: what is the sum of (or average of) the esteem held for this agent by other agents of the same craft?
3. **Explorer**: (e.g.) what is the sum of the distance between the most northerly and most southerly, and the most easterly and most westerly, locations this agent has visited?
4. **Climber**: how many 'promotions' has this agent had in the game?
6. **Conqueror**: how many total vassales, recursively, has this agent?
6. **Citizen**: really, really tricky. Probably what is the average esteem for this agent among all agents within a specified radius? - although this will score more highly for agents who have taken part in notable events, and what I'm really thinking of for my ideal 'good citizen' is someone who really hasn't.

So each agent is assigned - by the dreaded random number generator - one top
level goal when they are instantiated. I don't think it's necessary to model
change of top level goals, although of course that does happen in real life;
however, although each agent has one top level goal, they will have lower
level 'stretch goals' also taken from this list: so at each decision point in
an agent's planning loop, if base level needs are satisfied and progress on
the top level goal is blocked, actions should be chosen which progress one
of the lower goals. Indeed, it's possible that all agents could have all
goals, but randomly ordered.

At the lowest level there are immediate needs goals every agent must satisfy:
food for tonight, a safe place to stay tonight, food for next year, a safe
place to stay next year.

### On screen and off screen

If we're going to have a very large world with a very large number of
characters (as an order of magnitude number, say 100,000), then obviously we
cannot plan in detail every time each character lifts a cup to their lips to
drink. When a character is on screen we must represent small actions, and at
some level these must be planned for. But when they're off screen, that's
just wasted computation. The only actions we need to plan are life altering
actions, such as:

* Killings and deaths;
* Raids, feuds, abductions and significant thefts;
* Marriages, especially among the high status;
* Treaties and wars;
* Scandalous liaisons.

How far away should these actions be planned? Well, that's driven by gossip.
If news of an action is likely to reach the player through the gossip network,
then that action needs to be planned; if it's extremely unlikely, then not so
much. So we need to have a moving bubble around the player in which every
actor is woken for one iteration of their planning loop each game day; a
larger bubble in which only Masters, Captains, Outlaw Leaders and Aristons and
above are woken; and an outermost bubble comprising the whole world in which
only Tyrannos and Generals are woken.

Note that, if there's a 'fast travel' mode (which likely there will be), 'game
days' also happen while fast travelling, so this 'wake loop' has to be one
of the things which are still computed during fast travel.

Of course, gossip, markets and merchants are slightly different. For gossip
to work, merchants (and other gossip agents) need to be woken every time
they arrive at a new location, to exchange news; for trade simulation to work,
the buying and selling decisions of merchants at markets need to be modelled,
including modelling which markets the merchant will visit next. Also, there
needs to be some sort of thing about at the end of each game day, if there's
an Outlaw Leader within a certain distance of a merchant, then that outlaw
leader has to be woken for a decision on whether to attack (although this may
simply be within the second bubble, in which case the outlaw leaders will be
woken anyway).

### Planning

### Esteem and favour

Note that for all this work there need to be several dimensions to reputation,
and here I'd like to clearly discriminate between two: **esteem**, and
**favour**. Suppose, for a moment, that you are a Captain, engaged in a battle
with another Captain who wins the battle through what you regard as a sneaky
trick. You're not going to like your opponent much: your favour for them will
go down. But you're going to think they're pretty effective at their job: your
esteem for them will go up.

In general, an agent will treat more favourably another agent for whom they
have more favour, whether that's sharing more information (gossip), sharing
(or offering more favourable prices for) goods, or whatever.

This isn't the same thing as esteem. If there are two weapon smiths in town,
and you need a high quality sword, you'll go to the one you esteem more, not
the one you favour more. Esteem itself may have multiple dimensions: that
weaponsmith whom you esteem as a weaponsmith, you may not esteem as a fighter.
Obviously, the more dimensions you give it the harder it is to model, but at
least for player characters I'd like several dimensions of esteem, since a
player who has been successful in negotiation, for example, should be offered
more diplomacy quests, where one who has been more successful in battle should
be offered more warlike quests.

Generally, an agent will not employ another agent for whom they hold negative
esteem or negative favour; so losing either esteem or favour with your
employer will lead to vagrancy.

## One class of agent

But that brings me to a separate point: I feel strongly that every agent in
the game world should be essentially the same, in as much as whichever agent
the player inhabits, the game will still work. In some games, for example
the Witcher series, the player has no choice at all of which character they
play; in others, such as for example Dragon Age, you can choose your character
within some broad parameters, although this choice won't greatly affect the
story.

What I envisage is a game in which the player can choose to inhabit any
character at all in the game world, and the game will still work. Of course,
scripting a narrative with that sort of flexibility is a very big job, but
actually everything about this proposal is a very big job; I'm not going to
veer away from the view that the toolkit ought to at least support this (it
ought also to support the player choosing not to inhabit any character at
all, and just watch the stories unfold as with a movie, without taking part
at all). Having every agent 'the same' also makes the software more orthogonal
and thus simpler. Obviously, agents must differ, but that difference should
be in parameterisation - data - rather than anything else.

## More about merchants

Having said that there's one class of agent, there is something special about
merchants (and also minstrels and diplomats, see below); they are travellers
who make aspects of the dynamic game world work, and to do so they have to
have some behaviours to a greater degree than other agents.

A merchant has:

* A home base at a particular location - in a town with a market;
* A caravan of pack animals, or else a ship, in either case of finite capacity;
* A line of credit with some banking network, again finite.

At each market, a merchant must decide

1. Whether, and what, to sell;
2. Whether, and what, to buy.

In any case, the merchant can't carry more than the capacity of their
ship/caravan. The judgements are based on knowledge of likely prices in
reachable markets, the likely costs of the routes to those markets, and thus
the likely profitability of the trade. When away from their home market,
there will be a slight bias in selecting trades which will take them closer
to their home market. At each market, when goods are bought, the price of that
commodity in that market will rise slightly; when goods are sold, the price of
that commodity in that market will fall slightly. For some commodities it may
be reasonably possible to fully model price movements; for others, they will
probably need to be fudged. More modelling is better, but it costs compute,
and thus it may be necessary to fudge. In any given market, the 'buy' price
of any commodity is higher than the 'sell' price, by some markup, which is
maybe around 10%.

### Cost of movement

A caravan or ship costs so much per day to run, irrespective of whether full
or empty. So the base cost of a journey is a function of the time taken, which
is essentially a function of the distance.

### Outlawry and merchants

Outside the domains of aristons, outlaws may intercept caravans; when this
happens the following outcomes are possible:

1. The merchant (together with any mercenaries the merchant has hired to protect the caravan) successfully fights off the outlaws;
2. The outlaws steal the entire cargo (and may kill the merchant);
3. The merchant pays protection money to the outlaws, typically around 5%-10% of the value of cargo carried;
4. The merchant employs the outlaws as caravan guards (see below);
5. The outlaws allow the caravan to pass unmolested;
6. There is no contact.

Why should a merchant hire outlaws a guards? In this world, all fighter are
more or less mercenaries, and there's a fluidity between outlaws and
mercenaries; a fighter is employed by their sergeant, and if their sergeant
is employed by an ariston (or a captain employed by an ariston), they're a
soldier, and that's respectable. If, however, the sergeant isn't employed,
then at some point they become outlaws. At the point they become outlaws,
they start to raid unprotected farms, villages and caravans. Every time they
raid, the favour in which they are held by all other agents who hear of it
through the gossip network will decline.

A captain or ariston will choose to employ sergeants whom they view with
favour over equally esteemable sergeants they don't, so once you've become
an outlaw you're on a downward spiral. However, if you work for a merchant
and the merchant is happy with your work (i.e. he completes his journey
without being hit up by any other outlaws), then positive favour towards
you will propagate through the gossip network, and this is the outlaw's way
out of the downward spiral.

The fee for being a caravan guard is X per fighter per day, where X varies
a bit but is essentially a known rough amount.

### Aristons and merchants

Generally, if a merchant buys goods in an ariston's market, or sells goods
in the ariston's market, then the economy benefits and the ariston benefits
from that; so the 'tax' element is part of the market markup. But if a
caravan passes through an ariston's territory without stopping at a market,
there's probably a tax of about 5% of value.

Generally, an ariston's army will control outlawry within the ariston's
domain, so outlaw encounters within a domain are unlikely. Soldiers could
be able seek bribes, but that would bring a strongly negative impact on
favour and I'm not sure it's work modelling.

## Other habitual travellers: gossipers

Apart from merchants, the habitual travellers are diplomats (who, in the
craft tree, are similar to chancellors) and minstrels (who aren't on the
craft tree but should be); and vagrants. However, vagrants almost certainly
don't have positive favour, so aren't likely to be useful gossip agents.
Each game day, every habitual traveller within the 'local' gossip bubble
exchanges some items of gossip with the nearest innkeeper to their current
location. In the second and third gossip bubbles, it's probably only more
favoured gossip agents who do this. See
[The spread of knowledge in a large game world](The-spread-of-knowledge-in-a-large-game-world.html)

# Sandbox

Up to now I've been thinking of the Great Game as essentially an RPG with some sandbox-like elements; but I think it may be better to think of it as a sandbox game with some RPG like elements.

Why?

The core of the game is a world in which non-player characters have enough individual knowledge of the world and their immediate surroundings that they can sensibly answer questions like

* Where is the nearest craftsman of this craft?
* What price can I expect to get for this item in the local market?
* What news have you heard recently?
* Where does this person from your village live?

and where there's a sufficiently sophisticated and robust economy simulation that buying goods in one market and selling them in another is viable.

The original BBC Micro space trading game Elite had very little more in terms of game mechanics than a sandbox with a means to navigate it and an economy simulation, which wasn't even nearly as sophisticated as the one I have working now. Yet that combination resulted in engaging game play.

## Main sandbox roles

The idea of a sandbox is that the player character should be able to do pretty much anything they like within the mechanics of the game. From that, it seems to me reasonable that the player ought to be able to do more or less everything a non-player character can do. But creating the game mechanics to make each additional task doable takes time and investment, so there's a need to prioritise.

So, as Elite did, I propose to make the first available sandbox roles

### Merchant

Someone who travels from city to city, buying goods cheap in one and selling them for more in another; and

### Outlaw

Someone who intercepts and steals from merchants (and may also attack outlying farms and villages)

## Second tier playable roles

The next tier of playable roles rotates around issues arising from the mercantile ecosystem.

### Aristocracy

Aristocrats are basically settled outlaws who seek to establish a monopoly on extracting taxes from inhabitants and travellers in a particular region by driving out all other outlaws. Within the domain of an aristocrat, you have to pay tax but you're reasonably safe from being attacked by other outlaws and losing everything. Aristocrats may also maintain and improve roads and bridges and do other things to boost the economy of their territory, may expand into adjoining territory with no current aristocratic control, and may wage war on other aristocrats.

An outlaw ought to be able to become an aristocrat, by dominating an ungoverned area or by defeating an existing aristocrat.

### Soldiery

Soldiers, like aristocrats, are basically on the same spectrum as outlaws. Outlaws may hire themselves out to merchants as caravan guards, or to aristocrats as soldiers. Soldiers or guards, falling on bad times, may revert to outlawry.

## Routine, Discretion and Playability

There's a term that's used in criticism of many computer games which is worth thinking about hard here: that term is 'farming'. 'Farming', in this sense, is doing something repetitive and dull to earn credits in a game. Generally this is not fun. What makes roles in a game-world fun is having individual discretion &mdash; the ability to choose between actions and strategies &mdash; and a lack of routine.

Most craft skills &mdash; especially in the learning phase &mdash; are not like this, and crafts which are sophisticated enough to be actually engaging are very hard to model in a game. Learning a craft is essentially, inherently, repetitive and dull, and if you take that repetition out of it you probably don't have enough left to yield the feeling of mastery which would reward success; so it doesn't seem to me that making craft roles playable should be a priority.

## Cruise control

One of the most enjoyable aspects of The Witcher 3 &mdash; still my go-to game for ideas I want to improve on &mdash; is simply travelling through the world. Although fast travel is possible I find I rarely use it, and a journey which takes fifteen minutes of real world wall clock time can be enjoyable in and of itself. This is, of course, a credit to the beautiful way the world is realised.

(It's worth noting that [Kenshi](https://lofigames.com/), a game I'm coming to greatly admire, does not allow fast travel at all, but has an equivalent of 'cruise control' &mdash; you can set a destination and then accelerate time and simply watch as your characters journey).

But nevertheless, in The Witcher 3, a decision was made to pack incident fairly densely &mdash; because players would find just travelling boring. This leads to a situation where peaceful villages exist two minutes travel from dangerous monsters or bandit camps, and the suspension of disbelief gets a little strained. Building a world big enough that a market simulation is believable means that for the individual, the travel time to a market where a particular desired good is likely to be cheaper becomes costly in itself. Otherwise, there's no arbitrage between markets and no ecological niche for a merchant to fill. The journey time from market to market has to be several in-game days.

An in-game day doesn't have to be as long as a wall clock day, and, indeed, typically isn't. But nevertheless, doing several game days of incident-free travel, even in beautiful scenery, is not going to be engaging &mdash; which implies a fast-travel mechanic.

I don't like fast travel, I find it a too-obvious breaking of immersion. Also, of course, one of the interesting things about a game in a merchant/outlaw ecosystem is the risk of interception on a journey. The Dragon Age series handled interrupted travel in 'fast travel' by randomly interrupting the loading screen you get when moving from location to location in Dragon Age's patchwork worlds by dumping you into a tiny arena with enemies. That's really, really bad &mdash; there's no other way to say this. Everything about it shouts artifice.

So I'm thinking of a different mechanism: one I'm calling cruise control.

You set out on a task which will take a long time &mdash; such as a journey, but also such as any routine task. You're shown either a 'fast forward' of your character carrying out this task, or a series of cinematic 'shots along the way'. This depends, of course, on there being continuous renderable landscape between your departure and your destination, but there will be. This fast-forward proceeds at a substantially higher time gearing than normal game time &mdash; ten times as fast perhaps; we need it to, because as well as doing backgound scenery loading to move from one location to another, we're also simulating lots of non-player agents' actions in parts of the world where the player currently isn't. So a 'jump cut' from one location to another isn't going to work anyway.

The player can interrupt 'fast forward' at any time. But also, the game itself may bring you out of fast forward when it anticipates that there may be action which requires decision &mdash; for example, when there are outlaws in the vicinity. And it will do this **before** the player's party is under immediate attack &mdash; the player will have time to take stock of the situation and prepare appropriately. Finally, this will take place in the full open world; the player will have the option to choose *not* to enter the narrow defile, for example, to ask local people (if there are any) for any news of outlaw activity, or, if they are available, to send forward scouts.

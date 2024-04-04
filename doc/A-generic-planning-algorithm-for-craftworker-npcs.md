# A Generic Planning Algorithm for craftworker NPCs

## Preamble

The Great Game requires a number of different crafts to be performed, both because the economy depends on the products of those crafts and to provide verisimilitude and set dressing. Some of those crafts, the relations between them, and the progression within them are set out in [Populating a game world](Populating-a-game-world.html).

For the purposes of planning work, only Master craftspeople are considered.

A Master craftsperson has

1. a house and appropriate workshop, within a settlement;
2. zero or more apprentices;
3. zero or more journeyman;
4. a spouse, who is usually of lower status;
5. zero of more coresident children;
6. zero or more coresident non-working parents/elders.

There are limits to the number of apprentices and journeymen a master may take on, essentially based on demand in the local market. The master is responsible for housing and feeding all of the household including apprentices and journeymen, and for obtaining sufficient craft supplies. All craft work done in the household belongs to the master.

Apprentices are definitely not paid. Journeymen should be paid, but this is a detail to ignore until we have other things working.

Journeymen will move on from master to master from time to time &mdash; infrequently, but it will happen; and may be dismissed by masters when markets are tight. Journeymen probably learn their craft recipes &mdash; which is to say, the items and qualities of item they are able to craft &mdash; from the masters they work with. Consequently, journeymen will seek out masters with higher reputation; masters will prefer journeymen with more experience.

Apprentices do not move on until the end of their period of apprenticeship (16th birthday?) when they become journeymen.

The master will plan work in four hour sessions - essentially, a morning session and an afternoon session each day.

All craftspeople have regular schedules covering mealtimes, sleep, and festivals. A lower status person within the household will have regular schedules covering each of fetching water, fetching fuel wood, taking out night soil, feeding chickens, washing dishes and laundry, and so on.

When the master works in the workshop, all the apprentices and journeymen will also work in the workshop; when the master is engaging in recreation, they're also engaging in recreation. What they do when the master is e.g. going to market, I haven't yet decided.

## Commodity items and special commissions

In principle all craftspeople may make both commodity items and special commission items, but in practice many crafts will be mostly commodity and a few will be almost entirely special commission (for example a diplomat doesn't produce peace treaties pr&egrave;t-&agrave;-porter); but I don't yet have a good model of how I'm going to handle special commissions, so I'm just doing some hand waving here to say they will exist and must be handled.

## The algorithm

A master craftsperson needs to keep stock of a number of things

1. Sufficient food for the household;
2. Sufficient craft materials for immediate production;
3. Sufficient funds to buy more food/craft materials when needed;
4. Commodity craft items produced;
5. Craft items work in progress.

### Choosing tasks

So in planning a period of work, the master has to decide:

1. Do I need to go to market?
    1. Is there news of a travelling merchant who buys what I produce arriving at my nearest market? -> go to market;
    2. Is the household running low on food? -> go to market;
    3. Is the household running low on craft materials? -> go to market;
2. Do I have any commissioned items to produce? -> produce commissioned items;
3. Should I work on commodities or take the day off?
    This is a throw-of-the-dice decision, influenced by
    1. Cash on hand (if there's little, greater incentive to work);
    2. Weather (if it's especially good, less incentive to work);
    3. Gossip (if there's interesting news, less incentive to work)

### Commodity production

If the decision is to work on commodities, the next decision is what commodity item to produce.

For each craft recipe the master knows there will be

1. A list of quantities of different craft materials needed per item, for example a sword might need two kilograms of steel of a particular quality, ten kilograms of charcoal, one kilogram of timber, half a square metre of leather;
2. An amount of craftsperson time - for example, a standard infantry sword might take ten hours;
3. Memory of prices achieved by item to that recipe in the local market.

The master will choose a recipe for which there are sufficient materials on hand, and which is profitable to make &mdash; the more profitable, the more likely to be selected (but I think there's probably some furtive dice rolling under the table here too; you don't want all the smiths in town producing infantry swords at the same time, because that would swamp the market and drive prices down).

When an item is started, the materials for it are removed from stock and assigned to the item, which is added to the work in progress list. The number of items that can be produced in a work session is

```clojure
    (/ (* hours-in-session people-in-team) 
        hours-to-produce-one-item)
```

At the end of the session, the integer number of items produced is removed from the work in progress queue and added to stock, and the modulus is added as `:work-done` to the remaining item, which is left in the work in progress queue.

Obviously items in the work in progress queue may need to be completed at the start of the next commodity work session.

Obviously, none planned at sufficient granularity to be animated unless the workplace is in the `:active` circle, and none of it gets actually animated unless it's actually on camera, but the book-keeping in terms of food and craft materials consumed and of items produced must be done.

This implies that at least many master craftspeople must be in the `:background` circle, i.e. woken up once every game day to plan a work session, no matter how far away the player character is.

# Populating a game world

#### Saturday, 6 July 2013

 *(You might want to read this essay in conjunction with my older essay, [Settling a game world](../../2009/12/settling-game-world.html), which covers similar ground but which this hopefully advances on)*

 For an economy to work people have to be able to move between occupations to fill economic niches. In steady state, non player character (NPC) males become adult as 'vagrants', and then move through the state transitions described in this document. The pattern for females is different.

## Basic occupations

 The following are 'unskilled' occupations which form the base of the occupation system. Generally a male character at maturity becomes a 'Vagrant' and wanders though the world until he encounters a condition which allows him to advance up the occupation graph. If an occupation wholly fails, the character can revert to being a 'Vagrant' and start again.



| Occupation | Dwelling | condition | New trade | Notes |
| --- | --- | --- | --- | --- |
| Vagrant | None | land available and animals available | Herdsman |  |
| Vagrant | None | arable land available | Farmer | See crops |
| Vagrant | None | has weapons | Outlaw |  |
| Herdsman | None | Insufficient food | Vagrant |  |
| Farmer | Farm | Insufficient food | Vagrant |  |
| Outlaw | None | loses weapons | Vagrant |  |
| Vagrant | None | craftsman willing to take on apprentice | Apprentice |  |
| Herdsman | None | arable land available | Farmer |  |
| Outlaw | None | Battle hardened | OutlawLeader |  |
| Apprentice | (craftsman's) | Qualified | Journeyman |  |
| Journeyman | None | Unserviced customers available | Craftsman | See crafts |
| Craftsman | See crafts | Too few customers | Journeyman |  |
| Journeyman | None | arable land available | Farmer |  |
| Vagrant | None | Lord with vacancies available | Soldier | See military |
| OutlawLeader | None | Unprotected farms available | Laird | See nobility |


### Gender dimorphism

 In the paragraph above I said 'a male character'. It may seem unfair to create a game world in which the sexual inequality of the real world is carried over, and for that reason it seems sensible that female children should have the same opportunities as male children. But games work on conflicts and injustices, and so it seems reasonable to me to have a completely different occupation graph for women. I haven't yet drawn that up.

### Wandering

 Vagrants wander in a fairly random way. While vagrants are wandering they are assumed to live off the land and require no resources. Solitary outlaws similarly wander until they find a leader, although they will avoid the areas protected by nobles. Herdsmen also wander but only over unenclosed pasture. They visit markets, if available, periodically; otherwise, they live off their herds. Journeymen wander from market to market, but are assumed to trade skills with farmers along the way.

## Crafts

 Crafts are occupations which require acquired skills. In the initial seeding of the game world there are probably 'pioneers', who are special vagrants who, on encountering the conditions for a particular craft to thrive, instantly become masters of that craft.


| Craft | Dwelling | Supplies | Perishable? | Customer types | Needs market? | Customers | Supplier | Suppliers | Recruits |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| |  |  |  |  |  | Solo | Per journeyman | Per apprentice |  |  |  |
|     |     |     |     |     |     | --- | --- | --- |     |     |     |
| |  |  |  |  |  | Min | Max | Min | Max | Min | Max |  |  |  |
| --- |     |     |     |     |     | --- | --- | --- | --- | --- | --- |     |     |     |
| Smith | Forge | Metal Items | no | Farmer, Soldier | No | 6 | 10 | 4 | 6 | 1 | 3 | Miner | 1 | Vagrant |
| Baker | Bakery | Bread | yes | All NPCs | No | 20 | 30 | 12 | 18 | 6 | 10 | Miller | 1 | Vagrant |
| Miller | Mill | Flour, meal | no | Baker, Innkeeper | No | 2 | 3 | 1 | 2 | 1 | 1 | Farmer | 6 | Vagrant |
| Weaver | Weaver's house | Cloth | no | All NPCs | Yes | 6 | 10 | 4 | 6 | 1 | 3 | Herdsman | 2 | Vagrant |
| Innkeeper | Inn | Food, hospitality | yes | Merhant, Soldier, Farmer, Lord | No | 10 | 20 | 5 | 10 | 2 | 4 | Farmer,Herdsman | 2 | Vagrant |
| Miner | Mine | Ores | no | Smith | Yes | 2 | 3 | 1 | 2 | 1 | 1 | Farmer | 1 | Vagrant |
| Butcher | Butchery | Meat | yes | All NPCs | No | 10 | 20 | 4 | 8 | 2 | 4 | Farmer, Herdsman | 2 | Vagrant |
| Merchant | Townhouse | Transport, logistics | n/a | Craftsmen, nobility | Yes | 10 | 20 | 4 | 8 | 2 | 4 | n/a | n/a | Vagrant |
| Banker | Bank | Financial services | yes | Merchant | Yes | 10 | 20 | 4 | 8 | 2 | 4 | n/a | n/a | Merchant |
| Scholar | Academy | Knowledge | n/a | Ariston, Tyrranos, General, Banker | No | 1 | 4 | 1 | 2 | 0.25 | 0.5 | n/a | n/a | Vagrant |
| Priest | Temple | Religion | n/a | All NPCs | No | 50 | 100 |  |  |  |  |  |  | Scholar |
| Chancellor | Chancellory | Administration | n/a | Ariston, Tyrranos | No | 1 | 1 | 0 | 0 | 0 | 0 |  |  | Scholar |
| Lawyer | Townhouse | Legal services | n/a | Ariston, Merchant, Banker | No | 4 | 6 | 2 | 3 | 1 | 2 |  |  | Scholar |
| Magus | Townhouse | Magic | n/a | Tyrranos, General | No | 3 | 4 | 1 | 2 | 0.25 | 0.5 |  |  | Scholar |


 A craftsman starts as an apprentice to a master of the chosen crafts. Most crafts recruit from vagrants, A character must be a journeyman merchant before becoming an apprentice banker, while various intellectual crafts recruit from journeyman scholars.

 It's assumed that a journeyman scholar, presented with the opportunity, would prefer to become an apprentice magus than a master scholar.

 A journeyman settles and becomes a master when he finds a location with at least the solo/min number of appropriate customer type who are not serviced by another master craftsman of the same craft; he also (obviously) needs to find enough free land to set up his dwelling. The radius within which his serviced customers must live may be a fixed 10Km or it may be variable dependent on craft. If there are unserviced customers within his service radius, the master craftsman may take on apprentices and journeymen to service the additional customers up to a fixed limit – perhaps a maximum of four of each, perhaps variable by craft. If the number of customers falls, the master craftsman will first dismiss journeymen, and only in desperate circumstances dismiss apprentices. Every apprentice becomes a journeyman after three years service.

 The list of crafts given here is illustrative, not necessarily exhaustive.

## Aristocracy

 As in the real world, aristocracy is essentially a protection racket, and all nobles are originally outlaw leaders who found an area with rich pickings and settled down.


| Rank | Follower rank | Client type | Clients protected | Trade in market | Followers per client |
| --- | --- | --- | --- | --- | --- |
| |  |  | Min | Max | Min | Max | Min | Max |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Bonnet Laird | Private | Farmer | 6 | 20 | 0 | 100 | 0.25 | 0.5 |
| Ariston | Captain | Bonnet Laird | 10 | 30 | 25 | 1000 | 0.5 | 1 |
| Tyrranos | General | Ariston | 10 | unlimited | 250 | unlimited | 0.1 | 0.5 |


 Every noble establishes a market and, if he employs a chancellor, taxes trade in it. Crafts which 'need a market' can only be established in the vicinity of a market, irrespective of whether there are sufficient customers elsewhere. All non-perishable goods are traded through the markets, and merchants will transfer surpluses between markets if they can make a profit from it.

 My world has essentially three ranks of nobility. The title of the lowest rank will probably change to something vaguely italianate. An aristocrat advances to the next rank when either the requisite number of clients become available in the locality to support the next rank, or the trade in his market becomes sufficient to support the next rank.

 Obviously when a province has eleven unprotected bonnet lairds, under the rules given above any of them may become the ariston, and essentially it will be the next one to move after the condition becomes true. If the number of available clients drops below the minimum and the market trade also drops below the minimum, the noble sinks to a lower level – in the case of the bonnet laird, to outlaw leader.

## Military

 The aristocracy is supported by the military. An outlaw becomes a soldier when his leader becomes a noble. Otherwise, vagrants are recruited as soldiers by bonnet lairds or sergeants who have vacancies. Captains are recruited similarly by aristons or generals, and generals are recruited by tyrranos. If the conditions for employment no longer exist, a soldier is allowed a period of unemployment while he lives off savings and finds another employer, but if no employer is found he will eventually become an outlaw (or, if an officer, an outlaw leader). A private is employed by his sergeant or bonnet laird, a sergeant by his captain, a captain by his arison or general, a general by his tyrranos.


| Rank | Follower rank | Followers |  | Condition | New rank |
| --- | --- | --- | --- | --- | --- |
| |  | Min | Max |  |  |
| --- | --- | --- | --- | --- | --- |
| Private | None | 0 | 0 | Battle hardened, unled privates | Sergeant |
| Sergeant | Private | 5 | 15 | More battle hardened, unled sergeantts | Captain |
| Captain | Sergeant | 5 | 15 | More battle hardened, unled captains | General |
| General | Captain | 5 | unlimited |  |  |


 Soldiers have no loyalty to their employer's employer.

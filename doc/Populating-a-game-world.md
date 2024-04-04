# Populating a game world

#### Saturday, 6 July 2013

 *(You might want to read this essay in conjunction with my older essay, [Settling a game world](Settling-a-game-world.html), which covers similar ground but which this hopefully advances on)*

 For an economy to work people have to be able to move between occupations to fill economic niches. In steady state, non player character (NPC) males become adult as 'vagrants', and then move through the state transitions described in this document. The pattern for females is different.

## Basic occupations

 The following are 'unskilled' occupations which form the base of the occupation system. Generally a male character at maturity becomes a 'Vagrant' and wanders though the world until he encounters a condition which allows him to advance up the occupation graph. If an occupation wholly fails, the character can revert to being a 'Vagrant' and start again.



| Occupation | Dwelling | Condition | New trade | Notes |
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

<table>
<tr>
<th rowspan="3"> Craft <th rowspan="3"> Dwelling <th rowspan="3"> Supplies <th rowspan="3"> Perishable? <th rowspan="3"> Customer types <th rowspan="3"> Needs market? <th colspan="6"> Customers <th rowspan="3"> Supplier <th rowspan="3"> Suppliers <th rowspan="3"> Recruits </th></tr>
<tr><th colspan="2"> Solo <th colspan="2"> Per journeyman <th colspan="2"> Per apprentice</tr>
<tr><th> Min <th> Max <th> Min <th> Max <th> Min <th> Max </tr>
<tr><td>Smith <td>Forge <td>Metal Items <td>no <td>Farmer, Soldier <td>No <td>6 <td>10 <td>4 <td>6 <td>1 <td>3 <td>Miner <td>1 <td>Vagrant 
<tr><td>Baker <td>Bakery <td>Bread <td>yes <td>All NPCs <td>No <td>20 <td>30 <td>12 <td>18 <td>6 <td>10 <td>Miller <td>1 <td>Vagrant 
<tr><td>Miller <td>Mill <td>Flour, meal <td>no <td>Baker, Innkeeper <td>No <td>2 <td>3 <td>1 <td>2 <td>1 <td>1 <td>Farmer <td>6 <td>Vagrant 
<tr><td>Weaver <td>Weaver's house <td>Cloth <td>no <td>All NPCs <td>Yes <td>6 <td>10 <td>4 <td>6 <td>1 <td>3 <td>Herdsman <td>2 <td>Vagrant 
<tr><td>Innkeeper <td>Inn <td>Food, hospitality <td>yes <td>Merhant, Soldier, Farmer, Lord <td>No <td>10 <td>20 <td>5 <td>10 <td>2 <td>4 <td>Farmer,Herdsman <td>2 <td>Vagrant 
<tr><td>Miner <td>Mine <td>Ores <td>no <td>Smith <td>Yes <td>2 <td>3 <td>1 <td>2 <td>1 <td>1 <td>Farmer <td>1 <td>Vagrant 
<tr><td>Butcher <td>Butchery <td>Meat <td>yes <td>All NPCs <td>No <td>10 <td>20 <td>4 <td>8 <td>2 <td>4 <td>Farmer, Herdsman <td>2 <td>Vagrant 
<tr><td>Merchant <td>Townhouse <td>Transport, logistics <td>n/a <td>Craftsmen, nobility <td>Yes <td>10 <td>20 <td>4 <td>8 <td>2 <td>4 <td>n/a <td>n/a <td>Vagrant 
<tr><td>Banker <td>Bank <td>Financial services <td>yes <td>Merchant <td>Yes <td>10 <td>20 <td>4 <td>8 <td>2 <td>4 <td>n/a <td>n/a <td>Merchant 
<tr><td>Scholar <td>Academy <td>Knowledge <td>n/a <td>Ariston, Tyrranos, General, Banker <td>No <td>1 <td>4 <td>1 <td>2 <td>0.25 <td>0.5 <td>n/a <td>n/a <td>Vagrant 
<tr><td>Priest <td>Temple <td>Religion <td>n/a <td>All NPCs <td>No <td>50 <td>100 <td> <td> <td> <td> <td> <td> <td>Scholar 
<tr><td>Chancellor <td>Chancellory <td>Administration <td>n/a <td>Ariston, Tyrranos <td>No <td>1 <td>1 <td>0 <td>0 <td>0 <td>0 <td> <td> <td>Scholar 
<tr><td>Lawyer <td>Townhouse <td>Legal services <td>n/a <td>Ariston, Merchant, Banker <td>No <td>4 <td>6 <td>2 <td>3 <td>1 <td>2 <td> <td> <td>Scholar 
<tr><td>Magus <td>Townhouse <td>Magic <td>n/a <td>Tyrranos, General <td>No <td>3 <td>4 <td>1 <td>2 <td>0.25 <td>0.5 <td> <td> <td>Scholar 
</table>

A craftsman starts as an apprentice to a master of the chosen crafts. Most crafts recruit from vagrants, A character must be a journeyman merchant before becoming an apprentice banker, while various intellectual crafts recruit from journeyman scholars.

It's assumed that a journeyman scholar, presented with the opportunity, would prefer to become an apprentice magus than a master scholar.

### Related crafts

There are groups of crafts which should probably be seen as related crafts, where apprenticeship in one should serve as qualification to serve as journeyman in another. For example, there is a family of woodworking crafts, whose base is probably `Joiner`.

Crafts within this family include

* Boatwright
* Cabinetmaker
* Cartwright
* Cooper
* Lutanist
* Military Artificer
* Millwright
* Turner

So although I think these are separate crafts, all are Joiners; all can undertake construction joinery work; and a journeyman who has served as apprentice to any can serve as journeyman to any other. Since journeymen will typically serve under more than one master before settling down, it will be possible for one person to have served under masters in two different related trades and therefore be qualified to set up as a master of either.

A journeyman settles and becomes a master when he finds a location with at least the solo/min number of appropriate customer type who are not serviced by another master craftsman of the same craft; he also (obviously) needs to find enough free land to set up his dwelling. The radius within which his serviced customers must live may be a fixed 10Km or it may be variable dependent on craft. If there are unserviced customers within his service radius, the master craftsman may take on apprentices and journeymen to service the additional customers up to a fixed limit – perhaps a maximum of four of each, perhaps variable by craft. If the number of customers falls, the master craftsman will first dismiss journeymen, and only in desperate circumstances dismiss apprentices. Every apprentice becomes a journeyman after three years service.

The list of crafts given here is illustrative, not necessarily exhaustive.

## Aristocracy

As in the real world, aristocracy is essentially a protection racket, and all nobles are originally outlaw leaders who found an area with rich pickings and settled down.

<table>
<tr><th rowspan="2"> Rank <th rowspan="2"> Follower rank <th rowspan="2"> Client type <th colspan="2"> Clients protected <th colspan="2"> Trade in market <th colspan="2"> Followers per client 
<tr><th> Min <th> Max <th> Min <th> Max <th> Min <th> Max 
<tr><td>Bonnet Laird <td>Private <td>Farmer <td>6 <td>20 <td>0 <td>100 <td>0.25 <td>0.5 
<tr><td>Ariston <td>Captain <td>Bonnet Laird <td>10 <td>30 <td>25 <td>1000 <td>0.5 <td>1 
<tr><td>Tyrranos <td>General <td>Ariston <td>10 <td>unlimited <td>250 <td>unlimited <td>0.1 <td>0.5 
</table>

Every noble establishes a market and, if he employs a chancellor, taxes trade in it. Crafts which 'need a market' can only be established in the vicinity of a market, irrespective of whether there are sufficient customers elsewhere. All non-perishable goods are traded through the markets, and merchants will transfer surpluses between markets if they can make a profit from it.

My world has essentially three ranks of nobility. The title of the lowest rank will probably change to something vaguely italianate. An aristocrat advances to the next rank when either the requisite number of clients become available in the locality to support the next rank, or the trade in his market becomes sufficient to support the next rank.

Obviously when a province has eleven unprotected bonnet lairds, under the rules given above any of them may become the ariston, and essentially it will be the next one to move after the condition becomes true. If the number of available clients drops below the minimum and the market trade also drops below the minimum, the noble sinks to a lower level – in the case of the bonnet laird, to outlaw leader.

## Military

The aristocracy is supported by the military. An outlaw becomes a soldier when his leader becomes a noble. Otherwise, vagrants are recruited as soldiers by bonnet lairds or sergeants who have vacancies. Captains are recruited similarly by aristons or generals, and generals are recruited by tyrranos. If the conditions for employment no longer exist, a soldier is allowed a period of unemployment while he lives off savings and finds another employer, but if no employer is found he will eventually become an outlaw (or, if an officer, an outlaw leader). A private is employed by his sergeant or bonnet laird, a sergeant by his captain, a captain by his arison or general, a general by his tyrranos.

<table>
<tr><th rowspan="2"> Rank <th rowspan="2"> Follower rank <th colspan="2"> Followers <th rowspan="2"> Condition <th rowspan="2"> New rank 
<tr><th> Min <th> Max
<tr><td>Private <td>None <td>0 <td>0 <td>Battle hardened, unled privates <td>Sergeant 
<tr><td>Sergeant <td>Private <td>5 <td>15 <td>More battle hardened, unled sergeantts <td>Captain 
<tr><td>Captain <td>Sergeant <td>5 <td>15 <td>More battle hardened, unled captains <td>General 
<tr><td>General <td>Captain <td>5 <td>unlimited <td> <td> 
</table>

Soldiers have no loyalty to their employer's employer.

# The Red Company: modelling democracy and morale (unfinished)

## Background

The Great Game exists as a project on two levels. One one level, it's a framework for building algorithms to build much more vibrant, and thus enjoyable game worlds; at another level, it's about building a particular world, in which I want to tell stories.

The world in which I want to tell stories is a world which is based roughly on late bronze age to medieval Europe. It's a world in which the region known as 'The Coast' &mdash; the southern littoral of the continent &mdash; had been a mostly-peaceful matrideic dispersed agrarian tribal society, which had been invaded some hundreds of years past by a warrior tribe with substantially better military technology.

These warrior tribesmen have settled down as local tyrants or robber barons, parasitising on the indigenous communities, and have evolved into an aristocratic ('Ariston') class. In the meantime, a mercantile class has grown up and established important long distance overland trade routes; and significant towns (called 'cities', but of only at most a few tens of thousand inhabitants) have grown up around markets. 

These mercantile cities have been under the governance of powerful aristons known as tyrranoi, and the cities under their tyrranoi have competed militarily as well as commercially for control of strategic features on trade routes, such as bridges, fords, oases, mountain passes, and so on.

In the very earliest days of the warrior invasion, the warriors themselves fought against the indigenous peoples, who had very limited military equipment and tactics. Later, as they settled into Aristons, they fought by leading feudal levies of partially-trained peasants. Over the past hundred years or so, mercenary companies have emerged of specialist, trained warriors, and because these have more fighting experience (and often better equipment) they tend to beat feudal levies. These mercenary companies are base loosely on the condottierri of fourteenth century Italy.

So more and more, tyrranoi, rather than leading their own feudal levies, instead tax their peasantry and mercantile class more and hire condottierri to fight their wars.

Mercenary companies evolve out of feudal levies, and in the period of The Great Game, are mostly owned and led by aristons who employ their soldiers by paying them a wage.

One company, the Red Company, has become essentially a workers' co-op, after its former ariston leader fled in the course of a battle which looked like an inevitable defeat (but which the company, without him, won). In this company, soldiers are paid a salary, probably lower than salaries in other companies, but also at the end of the year get a share in the profits. The soldiers are organised into squads of eight who elect their own sergeants; squads are organised into companies of eight squads, and the sergeants elect the captain; companies are organised into legions of eight companies, and the captains elect the captain-general. 

However, while in combat this represents a chain of command, out of combat it is much more a delegate structure; when making significant decisions, the captains general will consult with the captains who will consult with the sergeants who will consult with the soldiers.

One of the themes of the stories I want to tell is that this more democratic structure contributes to higher morale and hence to greater military success. I could model this by just making membership of the Red Company a factor in the function which computes morale. However...

## Modelling democracy

If each individual character has a hierarchy of needs, and plans actions based on that hierarchy of needs, then they have the mechanism in place to choose which of two options better conforms to their hierarchy of needs.

This implies that soldiers are likely to vote for the people (or ideas presented by the people) they consider most competent and/or most trustworthy. Which comes back to modelling reputation; which comes back to the [gossip](the-great-game.gossip.gossip.html).
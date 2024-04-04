# The spread of knowledge in a large game world

#### Saturday, 26 April 2008

### Note

*This version of this essay has been adapted to use the code in `the-great-game.gossip.news-items`, [q.v.](the-great-game.gossip.news-items.html). The original version of the essay is [still available on my blog](https://www.journeyman.cc/blog/posts-output/2008-04-26-the-spread-of-knowledge-in-a-large-game-world/).*

-----

 These days we have television, and news. But in a late bronze age world there are no broadcast media. News spreads by word of mouth. If non-player characters are to respond effectively to events in the world, knowledge has to spread.

 How to model this?

 Some non-player characters &mdash; doesn't need to be many &mdash; are news-spreaders. News-spreaders need to travel. They have to travel even when there are no player characters in the vicinity. But, they don't have to travel very often &mdash; once or twice every game day. When a news-spreader is in the immediate vicinity of another character, the pair may (with some degree of randomness) exchange news. There needs to be a hierarchy in the exchange of news, so that 'I-saw' events need to be more likely to be passed on than 'I-heard' events; there needs to be a counter which counts the number of times a knowledge item has been passed on, and also an age counter so that knowledge items are less likely to be passed on as they get older.

 One obvious class of news-spreader is a merchant. Merchant agents can either shuttle mechanically between a fixed group of markets or else possibly respond intelligently to supply and demand. Provided that there is a mesh of merchant routes covering the markets of the game world, and that a useful subset of non-merchant characters are required to visit a market every few game days, this should give a reasonably realistic framework for news spreading.

 What else? What things qualify as news items? I think at least the following:

* Deaths of sentient characters, especially if violent
* Commodity prices
* Changes of rulers in cities
* Marriages of sentient characters
* Plot events, flagged as events by the game designer

 Obviously, news is more valuable if the people involved are important or notorious: the significance of a story is probably the product of the significance of the people concerned.

 So a news item becomes a map with keys similar to

    [:verb :actor :other :location :nth-hand :time-stamp]

 The [exact keys for each verb are specified here](the-great-game.gossip.news-items.html#var-news-topics).

 for example

     {:verb :kill,
     :actor {:id :fred :name "Fred"},
     :other {:id :joe :name "Joe"},
     :location [{45467 78613} :hanshua :plateau],
     :nth-hand 3,
     :time-stamp 17946463}

 meaning 'I spoke to a man who'd spoken to a man who said he saw fred kill joe at the game time represented by the time stamp 17946463, at the coordinates {45467 78613} in Hans'hua on the Plateau'. Obviously, the non-player character must be able to construct a natural language sentence from the tuple when speaking within the hearing of a player character, but there's no need for a non-player character to produce a natural language sentence for another non-player character to parse; instead they can just exchange tuples.

 But if we're exchanging knowledge between agents, then agents must have a means of representing knowledge. This knowledge is an association between subjects and sets of statement, such that when the agent learns the statement

     {:verb :kill,
     :actor {:id :fred :name "Fred"},
     :other {:id :joe :name "Joe"},
     :location [{45467 78613} :hanshua :plateau],
     :nth-hand 3,
     :time-stamp 17946463}

 it adds this statement (with the 2 incremented to 3) to the set of statements it knows about fred and also to the set of statements it knows about joe. It's possible that the receiving agent could then challenge for further statements about fred and/or joe, the automated equivalent of a 'who's joe?' question.

 There could be feedback in this. Fred's and joe's significance scores could be incremented for each character to whom the statement is passed on, increasing the likeliness that fred, at least, would feature in more news stories in future. There needs also to be some means of managing how the non-player character's attitude to the subjects of the statement are affected. For example, If fred kills joe, and the character (say bill) receiving the news feels positively towards joe, then bill's attitude to fred should become sharply more hostile. If bill feels neutral about joe, then bill's attitude to fred should still become a bit more hostile, since killing people is on the whole a bad thing. But it bill feels very hostile towards joe, then bill's attitude to fred should become more friendly.

But also, the added knowledge is *degraded*. If the recipient isn't from Hans'hua, the exact location isn't meaningful to them, for example. If the recipient isn't interested in Joe, precisely who was killed may be forgotten. So what is stored could become:

     {:verb :kill,
     :actor {:id :fred :name "Fred"},
     :location [:hanshua :plateau],
     :nth-hand 4,
     :time-stamp 17946463}

The timestamp could also be degraded, or lost altother &mdash; although how exactly this is represnted I'm not certain. Someone interested in the incident may remember 'it was exactly 17 days ago', whereas someone else may remember that it was 'this month, I think'.

 Obviously the rate of decay, and the degree of randomness, of the news-passing algorithm would need to be tuned, but this schema seems to me to describe a system with the following features:

* Non-player characters can respond to questions about significant things which happen in the world &mdash; without it all having to be scripted
* If you travel fast enough, you can keep ahead of your notoriety
* Characters on major trade routes will know more about what is happening in the world than characters in backwaters

 This seems to me a reasonably good model of news spread.

### Scaling of the algorithm

 Let's work around the idea that a 'game day' equates to about two hours of wall clock time. Let's work around the idea that there are of the order of fifty markets in the game world, and that for each market there are two or three merchants whose 'home base' it is.

 Obviously non-player characters who are within the vicinity of a player character have to be 'awake', in order that the player can see them interacting with their world and can interact with them. Those characters have to be in working memory and have to be in the action polling loop in any case. So there's no extra cost to their gossiping away between each other &mdash; around the player there's a moving bubble of gossip, allowing each character the player interacts with to have a high probability of having some recent news.

 But the merchants who aren't in the vicinity of a player don't have to be in working memory all the time. Each merchant simply requires to be 'woken up' &mdash; loaded into memory &mdash; once per game day, move a day's journey in one hop, and then, if arriving at an inn or at a market, wake and exchange news with one resident character &mdash; an innkeeper or a gossip. So the cost of this algorithm in a fifty-market game is at worst the cost of loading and unloading two non-player characters from memory every minute, and copying two or three statements from the knowledge set of one to the knowledge set of the other. If you're dynamically modifying significance scores, of course, you'd need to also load the characters about whom news was being passed on; but this still doesn't seem unduly onerous.

 Obviously, if memory is not too constrained it may be possible to maintain all the merchants, all the innkeepers and all the characters currently being talked about in memory all the time, further reducing the cost.

# Modelling trading cost and risk

In a dynamic pre-firearms world with many small states and contested regions, trade is not going to be straightforward. Not only will different routes have different physical characteristics - more or less mountainous, more or fewer unbridged river crossings - they will also have different political characteristics: more of less taxed, more or less effectively policed.

Raids by outlaws are expected to be part of the game economy. News of raids are the sort of things which may propagate through the [[gossip]] system. So are changes in taxation regime. Obviously, knowledge items can affect merchants' trading strategy; in existing prototype code, individual merchants already each keep their own cache of known historical prices, and exchange historical price data with one another; and use this price data to select trades to make.

So: to what extent is it worth modelling the spread of knowledge of trade cost and risk?

Obviously the more we model, the more compute power modelling consumes. If the core objective is a Role Playing Games as currently understood, then there is no need to model very complex trade risk assessment behaviour.

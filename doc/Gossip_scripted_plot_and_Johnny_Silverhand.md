# Gossip, scripted plot, and Johnny Silverhand

I've been writing literally for years -- since [Voice acting considered harmful](Voice-acting-considered-harmful.md) in 2015 -- about game worlds in which the player speaks to non-player characters just by speaking the words they choose in their normal voice, and the non-player character replies using a pipeline that goes, essentially,

1. Alexa/Siri style speech interpretation;
2. A decision on whether to co-operate based on the particular NPC's general demeanor and particular attitude to the player;
3. A search of the game state and lore for relevant information;
4. A filtering of the results based on what the particular NPC can be expected to know;
5. Generation of a textual response from those results based on a library of templates which defines the particular NPC's dialect and style of speech;
6. Production of audio using a [Lyrebird](https://www.descript.com/overdub?lyrebird=true) style generated voice.

As I've argued before, the game engine necessarily knows everything about the lore, and the current state, of the game world. It would be possible for any non-player character to answer literally any question about the game world, from who was mayor of Night City in 2020 to who lives in the apartment one floor up from yours, to what the weather is like in North Oaks just now.

What individual characters know should, of course, be more limited. People who live in Japantown or Heywood are unlikely to know who lives in a particular apartment in Watson; only real old timers, like Rogue, are likely to remember who was mayor fifty years ago. That's the reason for filtering; but the filtering really isn't a big deal.

Again, the generation of distinct voices for hundreds of non-player characters isn't any longer a big deal. Distinct social groups -- the corpos, and the different gangs such as Maelstrom or the Mox, will have their own argot, their own slang, their own habitual figures of speech which can be encoded into template libraries, while technologies like Lyrebird can produce an infinite range of realistic-sounding voices.

In particular, they can mimic real voices. They can mimic the voices of real actors. They can mimic [Keanu Reeves](https://cyberpunk.fandom.com/wiki/Keanu_Reeves). (Interestingly, since I first wrote this note, CD Projekt Red have used Lyrebird-like technology to [resurrect a voice actor](https://www.theverge.com/2023/10/13/23915535/cyberpunk-2077-phantom-liberty-polish-voice-actor-ai-ripperdock-viktor-vektor) in Phantom Liberty, proving that the technology is good enough).

So: how do you integrate this free form 'you can say anything to any character' style of play with scripted plot?

Obviously, my vision -- as I've set out in [Organic Quests](Organic_Quests.md) -- is that many quests should emerge organically from modelling the lives, activities and motivations of non-player characters. But that's a radical vision and not one you can really expect many people to buy into until it has been demonstrated to work. I think that investors are still going to want to have confidence that there's something exciting in the game for players to engage with, and I think directors are still going to want to tell the stories they want to tell.

So if I'm to sell the idea of free-form speech interaction with characters in the game world, I need an account of how it works with scripted characters voiced by high value actors in a scripted plot. I'm picking Johnny Silverhand as a core example, here, because I think he presents particular challenges.

But I also think these challenges can be addressed very easily.

In [Cyberpunk 2077](https://www.cyberpunk.net/), the player can't just go and find Johnny Silverhand, to speak to him. On the contrary, Johnny will just appear when the script calls for him to appear, and when he does he'll always initiate conversation. When a plot NPC initiates conversation with the player, the game could show -- as it does now -- a menu of things the player can say, with the implicit promise that selecting any one of these things will at least bring an interesting response which will expand one's knowledge of that character or of the lore.

Just as the player does now, the player in a game with free form speech interaction could choose to say one of the things presented in the menu, and the implicit contract -- that this would lead to a new revelation, or would advance the plot -- would remain unchanged. But the player could also choose to go off script, to take the conversation in an unscripted direction, or just to end it.

It should be said that in Cyberpunk 2077, unlike some other games, the player already has the choice to abruptly break off conversations, even with plot characters, so how the game handles breaking off the conversation does not need to change.

How should the game handle unscripted responses in scripted dialogues?

Well, the first and obvious thing is to parse the unscripted response to see whether it's a variant of one of the scripted responses, and if it seems that it might be, perhaps ask the player to verify that:

> **V**: Just get on with it already.
>
> **Panam**: You mean, go to the shiv camp?
>
> **V**: Yes, dammit.

But the second thing is to respond to the response exactly as the non-player character would if the player had initiated the conversation, using the pipeline given at the beginning of this essay. Of course, in the special case of Johnny Silverhand, he is -- at least initially -- decidedly hostile and extremely selfish, so his response will typically come at step two in the pipeline:

> **V**: Hey, Johnny, what's the quickest way from here to Jig Jig Street?
>
> **Johnny**: What am I now, your fucking tour guide?
>
> **V**: Oh, come on, Johnny, help me out a bit here, Where's the nearest gun dealer?
>
> **Johnny**: How the fuck should I know? I haven't been here for fifty years, all I know is ancient history.

The benefit of this interaction style is that these responses could be real acted responses by the voice actor (in this case, Keanu Reeves), which avoids the 'uncanny valley' risk that generated speech from a character the player has become used to interacting with may not sound quite natural enough.

But, if we've used Lyrebird technology to capture and mimic Reeves' voice, and if Johnny is for some reason uncharacteristically mellow, then generated voice responses should be used. So suppose the player asks something which Johnny ought reasonably to know:

> **V:** Hey, Johnny, what's between you and Rogue?

That's lore. It's in at least one of the in-game 'shard' texts. The game engine knows it. A text can be generated for Johnny to respond:

> **Johnny**: We were lovers, back in the day.

In any of these cases, in order for the scripted plot to proceed, the non-player character can circle back to the thing they said that the player hasn't yet made an appropriate response to:

> **Johnny**:But you didn't answer my question. *Repeats question*.

or

> **Johnny**: As I said before, *Repeats what he said before*.

Again, for key plot characters, the voice actors can actually record multiple different canned texts of this form, so that, when played, they don't sound excessively repetitious.

In short, it doesn't seem to me that it would be at all hard to integrate free form voice interaction with a modern scripted video game. The advantage is that player interaction with non-player characters would become far richer and more engaging, and consequently it would be much easier to allow the player to progress through the plot without the default outcome of every encounter having to be a blood-bath.
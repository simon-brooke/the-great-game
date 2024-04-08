# On Sex, and Sexual Violence, in Games

For me the purpose of games is to provide worlds in which players can explore moral actions, and the consequences of moral actions. Sexual violence is something that happens in the real world, and which happens, even within the real world, more frequently in areas of poor governance and open conflict; and those are areas in which there are important moral actions, and important moral consequences, so they are areas in which it is interesting to set games.

It would be ludicrous to argue 'sexual violence is wrong, therefore we should not represent it in games.' Killing people is also wrong, yet it is extremely common in games. However, sexual violence &mdash; and in particular the representation of sexual violence &mdash; does pose some specific problems that need to be addressed.

Firstly, sexual violence is extremely gendered. Yes, male people are sometimes subjected to sexual violence, but nevertheless the overwhelming majority of victims of sexual violence are female. Yes, female people are sometimes &mdash; extraordinarily rarely, but sometimes &mdash; perpetrators of sexual violence, but nevertheless perpetrators of sexual violence are almost exclusively male.

Secondly, it is extremely tricky to represent sexual violence in visual media without eroticising it. There's a [very famous scene in Last Tango in Paris](https://www.independent.co.uk/arts-entertainment/films/news/last-tango-in-paris-butter-scene-b2270513.html) which the director might claim is consented in context, but which appears to me to be a clear case of anal rape, which is nevertheless highly erotic. There's a scene in [High Plains Drifter](https://en.wikipedia.org/wiki/High_Plains_Drifter#Plot) where no part of the rape is actually represented &mdash; it happens off screen &mdash; but it is nevertheless perceived by many people (including me) as eroticised. Many people &mdash; I suspect more men than women, but certainly including many women &mdash; do find the idea of rape erotic. It seems to me highly undesirable that a game should be seen to be rewarding immoral action.

(Yes, I know many modern games do quite explicitly reward killing, including of characters whose culpability is at best trivial, but &mdash; surely &mdash; this is something we should be seeking to move away from.)

## Subtlety and Nuance

A final issue here is that sexual interactions between people are subtle, and are subtle even around issues of consent. A less powerful person (normally a woman) &mdash; alone or as a member of a weak party, a party of perhaps older people, other women, children &mdash; may submit to sex with more powerful others without protest in order to protect others in their party, or to avoid death or serious injury, or to avoid starvation, or to escape debt. Do any of these things truly count as consent?

Again, a less powerful person may submit to sex with more powerful others transactionally in return to protection, or shelter, or food, or other resources. In modern society we might see this as sex work, and we might argue that sex work falls into the same moral category as any other labour entered into transactionally. But, generally, is it moral that people should be put into a position where their survival depends on their ability to sell any sort of unwilling labour?

(This is not to deny that some people, who do have secure living conditions or who could choose to do other things in order to gain secure living conditions do choose, willingly and voluntarily, to engage in sex work; and it isn't to criticise those people in any way).

Games are not very good at subtly and nuance. When, while playing a game, the character who is our avatar in the game, who we thought we were controlling, does something which we didn't intend them to do, it's very wrenching and immersion-breaking.

At the same time, if other characters in the game interpret something the player's character has done as sexual violence when the player did not intend sexual violence, that's also undesirable.

So, questions:

## Sex between non-player characters

People have sex. If people didn't have sex, there wouldn't be people; but more, if people didn't have sex, there wouldn't be (many) stories, since most stories are driven at least in part by sex. So pretending that non-player characters don't have sex is worse than unrealistic. 

We live in a pathologically repressed society, in which open sex &mdash; sex in public places, sex with other people present &mdash; is rare, is seen as deviant, is (perhaps in consequence) highly eroticised. Does that mean that all the societies we represent in our games must be similarly repressed?

I would argue strongly to the contrary. Games are environments in which we can explore moral possibilities, and a society in which public sexuality was normal is clearly a possibility. Would such a society be a better society? Games are a mechanism through which we can ask that question, and questions of that sort. 

If we're going to represent a society in which public sex is normal, then we're going to have to represent public sex on screen. It can take one of many forms:

1. Sex as normal activity &mdash; it's just going on in the background, and no other non-player characters pay much attention;
2. Sex as conscious performance &mdash; sex where the participants intend to be watched, and other non-player characters do pay attention (this may include consciously eroticised performance);
3. Sex as part of a religious or other ritual event &mdash; this is related to, and is, sex as conscious performance, but the purpose of the performance is symbolic and/or sacramental. This doesn't mean it is not eroticised, but it may not be eroticised.

By 'eroticised', I'm meaning deliberately intended to trigger sexual feelings in the audience &mdash; which, if the player character is present, includes the player.

### Breeding strategies for non-player characters

In order to model population growth, I need to model when two NPCs will have sex; I need to model (roughly) when two NPCs will choose to have sex.

This is different for female NPCs to male NPCs (and female NPCs will be much more cautious, because of the risk/cost of pregnancy), so let's consider each in turn.

**Note that** sex happens if and only if the conditions for both the female partner and the male partner are met.

**Note that** we're considering heterosexual sex only here because what I'm currently modelling is population growth, and only heterosexual sex leads to pregnancy.

#### Generalised breeding strategy for a female NPC

A female NPC will have sex with a male NPC:

1. Long term relationship
    1. Her life goal is `:ancestor`;
    2. His life goal is either `:ancestor` or `:citizen`;
    3. Her attitude towards him is +2 or above;
    4. They reside in the same dwelling, *or* one or other of them is of no fixed abode (in which case they will reside in the same dwelling);
    5. All other levels in her hierarchy of needs are currently satisfied.
2. Passion
    1. His attitude towards her is +4, and 
    2. Her attitude towards him is also +4;
    3. All other levels in her hierarchy of needs are currently satisfied.
3. Social climbing
    1. Her life goal is `:climber` and 
    2. His social rank is two or more higher than hers (counting `:bonnet-laird` as above `:master`);
    3. All other levels in her hierarchy of needs are currently satisfied.
4. Transactional
    1. Need
        1. She is hungry and broke; and
        2. He has surplus food and/or money.
    2. Coercion
        1. He has something she urgently wants (the example I'm thinking of is he has abducted a child);
    3. Sex work
        1. Her life goal is `:hoarder`, and he has surplus money; or
        2. It's possible I make sex work an actual career choice -- a craft of its own.
5. Rape
    1. If she is a slave and he is her owner;
    2. If he has defeated her in a fight;
    3. If she assesses that he will defeat her in an impending fight;
    4. Possibly, to distrct him from doing worse things to people she is protecting (e.g. children)

#### Generalised breeding strategy for a male NPC

1. Ancestor
    1. His life goal is `:ancestor`; and
    2. His attitude towards her is +2 or above;
    3. All other levels in his hierarchy of needs are currently satisfied.
2. Long term relationship
    1. His life goal is `:ancestor` or `:citizen`;
    2. Her life goal is `:ancestor` or `:citizen`;
    3. His attitude towards her is +2 or above;
    4. They reside in the same dwelling, *or* one or other of them is of no fixed abode (in which case they will reside in the same dwelling);
    5. All other levels in his hierarchy of needs are currently satisfied.
3. Dynastic
    1. His life goal is `:conqueror`; and
    2. Her social rank is `:ariston` or above;
    3. She has no other existing long term sexual relationship;
    4. All other levels in his hierarchy of needs are currently satisfied.
4. Social climbing
    1. His life goal is `:climber`; and
    2. Her social rank is at least one higher than his;
    3. All other levels in his hierarchy of needs are currently satisfied.
5. Casual
    1. His attitude towards her is +1 or above;
    2. All other levels in his hierarchy of needs are currently satisfied.


## Sexual violence between non-player characters

In a world in which there are characters who are thuggish, who seek to dominate, terrorise and subdue other characters, whether those characters are outlaws or soldiers or aristocrats, to pretend that rape would not be used as a means to dominate, terrorise or subdue is… bowdlerisation. It's unrealistic, and it's a morally indefensible choice.

So there has to be a mechanism for non-player characters to decide to commit acts of sexual violence towards other non-player characters. The player must at least hear of such events through the gossip network, and should be able to find the specific non-player characters involved, and speak to them. Whether it's necessary to portray acts of sexual violence on screen is something I'm much less persuaded by, simply because it runs the risk of eroticising them.

## Mutually consented sexual activity between the player character and non-player characters

Mutually consented sexual behaviour between the player character and (certain, scripted) non-player characters has been a feature of video games for some time, and has occasionally been portrayed with real sensitivity and eroticism. Two cases I would point to are 

1. The sex scene between Geralt and Shani in The Witcher;
2. The sex scene between V and Judy in Cyberpunk  2077.

Cyberpunk is a largely non-cutscene game, but the sex scenes is a cutscenes and I completely understand why, from a technical point of view: the player does not have, either with mouse and keyboard or with a game controller, nearly enough control over their character to convey the subtlety and nuance of a good sex scene. Sex scenes in most video games are also pretty rare, and that must be at least partly because of cultural prurience. 

But if a game allows a player to have a long lasting, narratively sexual relationship with a non-player character, as many games do, then sex is a behaviour which may happen repeatedly, and just playing the same cutscene over and over again is not going to be an adequate way of representing that.

The ideal would be to have a moderately large library of brief motion captures of people authentically having sex, and to be able to select performances at random from that library to apply to the body models of the characters who in the game are having sex, whether that be the player character with a non-player character, or two non-player characters. In the case where the player character is involved, this would happen in the location where the player chose to initiate it, so it wouldn't be a cutscene in the normal sense; but I think that the controller should be disabled for the duration of the performance.

## Sexual violence by one non-player character towards another

This is at least implicitly represented in existing video games, and while caution about eroticising it should be maintained, I think it's something which should be narratively possible.

## Sexual violence from the player character towards non-player characters

This would be extremely tricky (and controversial!) to handle; I *think* it ought to be in the narrative toolkit, but I have no specification to offer just now.

## Sexual violence from a non-player character towards the player character

Even trickier!


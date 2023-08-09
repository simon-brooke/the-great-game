# Selecting the Player Character

## Background

Many computer role playing games, particularly older ones such as Neverwinter Nights, allow you to 'design' your player character from a fairly broad canvas. Race, class, attributes, gender and appearance are all selectable.

Choice has eroded over time. For example the Dragon Age series, where you can chose between three races, two genders, and a small number of classes. In the Mass Effect trilogy, you play as Shepard, who is human and essentially a Fighter, but can be either male or female and whose appearance you can customise. You can play as either lawful good or chaotic neutral. In Cyberpunk 2077, you play as V, who is human, either male or female, essentially a Fighter, and chaotic neutral.

In more recent games, there has been a trend towards more limited choice. In the games of The Witcher series, you get no choice at all, but play as Geralt of Rivia, who in the categorisation of Dungeons and Dragons, is a Fighter/Ranger, male, human, and somewhere between chaotic good and chaotic neutral depending on how you play him. In the Horizon series, you play as Aloy, again a Fighter/Ranger, female, human, and essentially chaotic good.

As I've argued elsewhere, part of the reason for limiting choice is voice acting.

Limiting choice of player character, especially in games with increasingly highly scripted stories, limits replayability; after two or three playthroughs, there are very few interesting surprises left.

## The Self-voiced Player

If we have voice interaction sufficiently sophisticated that we can allow the player character to say more or less whatever they want to say &mdash; [and my argument here is that we can do this](Gossip_scripted_plot_and_Johnny_Silverhand.md) &mdash; then we don't need voice acting for the player character, and that gives us a lot of freedom. There's then really no reason why the player can't inhabit any character in the game world and play as that character.

## Tinder as a Character Selector

Tinder is a dating app. It shows you pictures of potential partners, and you choose from them by swiping left to reject them, or right to express interest in them. That's a kernel of an idea for how to select from among a large selection of people.

So how about:

1. The game developer selects a large subset of characters in the game as potentially playable. This might be as large as all characters who are not plot characters, as small as only soldiers, or most plausibly, any adult who is not yet in a long term romantic relationship. This forms the candidate set.
2. In the character selector, the game shows a character chosen at random from the set, and, each time the player rejects the character shown, shows another until the player accepts a character.

That works, but we can do better.

## Refining the Selection

Suppose, below the image of the character on the selection screen, we have a short text caption with name, age, home, occupation, gender, and below that, we have a row of icons showing attributes, with some representation of the character's relative measurement of that attribute. Clicking one of these attribute icons would be interpreted as meaning 'show me a character quite like the current character, but having a higher score on this particular attribute'.

### Refinable Attributes

In lots of games which present the player with dialogue options, there are some options which can't be selected unless the player character passes a 'skill check'. Very often, for example, a player won't be able to issue a particular threat unless they have a specific value of strength, or to say something flirtatious unless they have a specific value of charm.

It makes no sense in a game in which the player gets to freely choose what to say for an attribute like 'charm' to be a refinable attribute. Instead, responding to 'charming' or flirtatious or threatening or funny or sexually suggestive speech is a matter for the programming of a particular non-player character (although the interpretation of the speech and the tagging of it as charming or flirtatious or threatening or funny or suggestive would be a function of the top level speech input processor).

So, sensibly refinable attributes might include things like

1. Strength;
2. Agility;
3. Dexterity;
4. Endurance.

I did think that 'intelligence' or 'learning' might be on that list but the more I think of it, the harder I find it to understand how low intelligence might be represented in a game in which the player speaks freely.

There's another attribute icon with slightly different semantics which might sensibly be added, and that's gender. Selecting this icon would be interpreted as meaning 'show me a character quite like the current character, but having a different gender'.

### Summary design

So the character selecter now looks like

1. A main area in which a rendering of the proposed character is shown; this rendering can be zoomed and rotated, so that the player can look at the face and body from different angles;
2. A description panel, normally hidden but when displayed replacing the character rendering in the main area, giving fuller biographical information about the character;
3. Below the main area, a caption, giving name, age, gender, occupation, home;
4. To the right hand side, a vertical column of attribute icons.

To interact with the screen, the player can

1. Zoom in and out on the rendered image, for example with a mouse scroll wheel or the left and right trigger buttons of a game controller;
2. Rotate the rendered image, for example by dragging with the right mouse button held down or with the right joystick of a game controller;
3. Toggle between the character render and the description panel;
4. When the description panel is displayed, scroll it;
5. Select any attribute icon to refine the choice of character;
6. 'Swipe left' (or other action) to reject the current choice of character and choose another, without refining in any specific way;
7. 'Swipe right' to select the current character and procede into the game.



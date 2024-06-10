# Towards a procedural animation API

I've been watching this animation tutorial, and I'm impressed with its simplicity. This seems to be to be something it would be possible to do, and it started me thinking about what I wanted from a procedural animation API.

<iframe width="800" height="504" src="https://www.youtube.com/embed/LNidsMesxSE?si=QgaN2aHG0g71aWkX" title="An Indie Approach to Procedural Animation" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>

The game is now open source, with the source available [here](https://github.com/WolfireGames/overgrowth/). It's available on [Steam, here](https://store.steampowered.com/app/25000/Overgrowth/).

So: what have we here?

A simple character rig with a small number of key frames which can adaptively negotiate a moderately complex terrain. Inverse kinematics is used, but the core of the animation is key-frame animation with well thought out interpolation, and it all looks remarkably persuasive. This looks buildable.

So: what do I actually need?

## Smooth continuous movement on complex terrain

Characters need to transition from one movement to another movement smoothly, without sudden jums or jerks. Turning needs to look physically plausible.

## Animation for several skeleton types

Creatures I need to animate include

1. People: upright bipedal motion;
2. Horses: even-length-legged quadripedal motion;
3. Camels: also even length legged quadripeds, but their gait is different from horses, although the skeleton might be similar. So: different key frames? Or something more elaborate than that?
4. Seals: primarily aquatic quadrupeds, with forelimbs considerably longer than back limbs, and all limbs much shorter than the other quadripeds under consideration. But I'm not sure how much I need to actually animate them.
5. Dragons: exist in the world, but aren't necessarily going to appear in version one of the game. A much more complicated problem.
6. Birds: but don't need to animate them close up or landed. However, not all birds fly using the same 'gait', so, again this may be complex.
7. Other quadrupeds, such as dogs and cats; but these are a long way down the priority scale.

At the proof of concept stage, I only need animated people; and the only reason for cataloguing the other things I need to animate is that the animation framework needs to be able to handle multiple skeleton types.

It's also worth pointing out that in Cyberpunk 2077, it's very noticable that several different gaits are implemented for people: there is a noticably distinct 'fat person' gait, and several different gaits that are used, for example one extremely sexualised female gait, used mainly for characters who are sex workers, but for a small number of other female characters. The point here is that not all characters walk the same, and having distinctive gaits for some characters or classes of characters decidedly adds to a game's persuasiveness.

## The API

### (move-to model terrain target)

Plans a route across the terrain from where the model is to the target (which can be either a location or an object, including another potentially moving character), avoiding obstacles and choosing the easiest path across slopes, and produces a route; animates the model along that route, using the keyframe-based system described in the video, with inverse kinematics to ensure feet touch the ground.

### (look-at model terrain target)

Turns the model's gaze, and, if necessary, head and body, to face towards the target.

### (sit-on model terrain target)

Causes the model to sit on the target, moving to it first if necessary. If the target is approximately chair-height, sit on it as if on a chair; if it's a horse or camel, sit on it as though to ride; otherwise, sit cross legged. If the target is just a location, sit cross legged on the terrain surface at that location.

Obviously, what I've described there is partially specific to upright bipedal models; horses and camels don't sit on chairs!

### (lie-on model terrain target)

As above, but lie down.

### (drink-from model terrain target)

As above, but drink. Target is expected to be a drinkable container, such as a mug, flask or bottle, or else a point in the terrain which is flooded.

### (eat-from model terrain target)

### (strike model terrain target implement vector)
### (thrust model terrain target implement vector)

Two closely related animation calls, both of which might be used both in combat animation and in craft activity animation. **BUT** both combat animation and craft activity animation are much more complex problems which this not does not address. 

## Other things which need to be animated

### Conversation

In conversation, people move to a convenient distance from one another, typically face one another, typically take turns to say things, frequently use hand gestures when speaking. The intensity of gestures will vary with the emotion being expressed. The animation of NPC-to-NPC conversation is if possible more complex to get right than of player character to NPC.

### Combat animation

### Craft activity animation

Both of these will be very complicated to get right.
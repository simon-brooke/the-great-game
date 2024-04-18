# Simulated Genetics

If we're going to have a world with a multi-generational population of hundreds of thousands of procedurally generated characters, and we're to persuasively represent each character as being related to others, then we have to have a mechanism for making children look reasonably like their parents, to have family resemblances among cousins, and so on. We need to do this at reasonably low data storage and algorithmic cost, firstly because we have to store all these characters, and secondly because (especially when the player approaches an urban centre), we may need to instantiate models for a lot of them in limited time.

This note discusses how this might be done.

## The pseudo-genome

Suppose we have a binary vector of memory, such that when a 'child' was born to two parents, bits were taken at random from the parents' chromosomes to populate the child's genome -- which is sort of, very roughly, what happens in actual biology -- how big would that genome have to be? After all, the full data size of the human genetic code is enormous. But actually, we don't need to simulate anything like so large. After all, all our genome needs to encode is morphology, and only sufficiently to enable the player to recognise and distinguish characters.

My hunch is that a 32 bit genome is sufficient, if we code it carefully. It allows for four billion distinct appearances to be encoded, which is way more than we need. So here's how such a genome might be structured: 

| Field                | Bits | Interpretation                                               |
| -------------------- | ---- | ------------------------------------------------------------ |
| Ethnic type          | 4    | Ethnic type. Most significant bits both indicate dark skin, with [??11] indicating dark skin/curly hair and [??01] indicating dark skin/straight hair |
| Skin tone            | 3    | Plus second-most significant bit from ethnic type (i.e. [??1?]) as most significant bit. This means  sixteen distinct tones, with the darkest tone of 'pale skinned' ethnicities just very slightly lighter than the palest tone of 'dark skinned' ethnicities. |
| Freckles?            | 2    | [11] means freckles, any other value means no freckles. Freckles won't be visible on very dark skin. |
| Hair colour          | 3    | Plus second-most significant bit from ethnic type (i.e. [??1?]) as most significant bit. Least significant bit does not contribute to tone but indicates red tint. Thus eight distinct degrees of darkness from pale blond to black, plus red tint which can affect any degree of darkness. |
| Eye colour           | 2    | Plus second-most significant bit from ethnic type (i.e. [??1?]) as most significant bit. Thus eight values: [000] blue; [001] hazel; [010]...[111] shades of brown lighter->darker. |
| Height               | 3    | Height when adult; children will have a scaled proportion of their adult height, and the same height value in the genome will result in female body models 95% the height of an equivalent male body model. So [000] codes for 150mm, [111] codes for 200mm, with eight distinct values |
| Gracility/Robustness | 3    | Slenderness to stockiness of skeleton/armature build, with [000] being very slender and [111] being very broad/heavy. |
| Age-related change   | 3    | People get white haired at different ages; some men go bald and some do not. The sons of the daughter of a bald man should have a chance of inheriting age-related baldness, although their mother won't express that gene. So I'm allowing here for eight different profiles for age related change, although I'm not yet clear what the exact values would mean. |

That's twenty-two of our thirty-two bits, leaving 10 bits (1024 values) for face models; but actually, that 2048 distinct possible face models, because the morphology of female faces is different from the morphology of male faces. Although, again, we might encode gender into the genome, which would mean only bits left for face models, but still, 1024 distinct faces is plenty, especially as each face model would need to have its own aging model, so that characters would credibly age.

## What's not included in the genome

Things which are cultural are not included in the genome; things which are lifestyle related are not included in the genome. So, for example, gracility/robustness, is not the same as skinniness/fatness, which are mostly lifestyle/diet related rather than genetic. There are some occupations (e.g., blacksmith) where you'd be unlikely to be fat (but might be very robust). Also, the same character might grow fatter (or thinner) over time. 

Similarly, hairstyle and beard-wearing are cultural (and occupational) rather than genetic, and closely related to choice of clothing. So while we do need to represent these things, they're not things which should be represented in the genome.

Injury-related change -- which would especially affect soldiers and outlaws especially but could affect any character -- also needs to be encoded somehow (and may cause real problems), but this is also not a problem for the genome.


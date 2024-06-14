(ns cc.journeyman.the-great-game.character.character
  "A character that can talk; either human or dragon (although very probably
   we won't do talking dragons until really well into this process). All
   characters have the news-passing abilities of a gossip, but we use `gossip`
   to mean a special character who is part of the news-passing network."
  (:require [cc.journeyman.the-great-game.gossip.gossip :refer [dialogue]]
            [cc.journeyman.the-great-game.agent.agent :refer [ProtoAgent]]
            [cc.journeyman.the-great-game.objects.container :refer [ProtoContainer]]
            [clojure.string :as cs :only [join]])
  (:import [clojure.lang IPersistentMap]))

(defn honorific
  "Placeholder. If a character is a teir one craftsman, they get called 'Master';
   if a teir two ariston, they get called 'Ariston' and if a teir one ariston,
   'Tyrranos'. But the logic of this is about occupations, which probably isn't
   this namespace."
  [_character]
  nil)

(defn place-name
  "Placeholder. We're going to have to have names of villages, towns, regions 
   and so on, and we're going to have to be able to retrieve those efficiently,
   but I don't yet know how this is going to work. Definitely doesn't belong 
   in this namespace."
  [_cell]
  nil)

(defn match-on?
  "Placeholder, utility function. Do all these `objects` have the same values for
   these `keys`?"
  [keys & objects]
  (reduce = (map #(select-keys % keys) objects)))

(defprotocol ProtoCharacter
  (full-name [character]
    "Return the full name of this `character`, constructed according
              to the default construction sequence")
  (relative-name [character other]
    "Return the name that `other` would naturally use in an 
                 informal context to describe `character`")
  (personal-name [character]
    "Return the personal name of this `character`."))

(defrecord Character [object
                      agent
                      family-name
                      personal-name
                      occupation
                      rank
                      epithet
                      knowledge
                      wallet]
  ;; A character; obviously, normally, a non-player character, although the 
  ;; player character is one of these. Essentially, an Agent which can speak,
  ;; which has knowledge, which has a set of affective relationships with other
  ;; characters. 
  ;; ProtoContainer
  ProtoAgent
  ProtoCharacter

  (personal-name [character] (:personal-name character))
  (full-name [character]
    (let [e (:epithet character)
          h (honorific character)
          f (:family-name character)
          p (:personal-name character)
          o (:occupation character)
          l (place-name (:cell character))]
      (cs/join " "
               (remove nil?
                       (flatten
                        [e
                         h
                         f
                         p
                         (when o ["the" o])
                         (when l ["of" l])])))))
  (relative-name [character other]
    (let [e (:epithet character)
          h (honorific character)
          f (:family-name character)
          p (:personal-name character)
          o (:occupation character)
          h (place-name (:cell character))
          same-family? (= f (:family-name other))]
      (cs/join " "
               (remove nil?
                       (flatten
                        [(when-not (match-on?
                                    [:family-name :cell] character other)

                           e)
                         (when-not same-family? h)
                         (when-not same-family? h)
                         p
                         (when (and o (not (match-on? :occupation))) ["the" o])
                         (when (and h
                                    (not (match-on? [:cell] character other))) ["of" h])]))))))


(defn make-character
  "Construct a Character record from this `seed` map"
  [^IPersistentMap seed]
  (let [object (make-object seed)
        agent (make-actor seed)]
    (apply Character.
           (list (map seed [:agent
                            :family-name
                            :personal-name
                            :occupation
                            :rank
                            :epithet
                            :knowledge
                            :wallet])))))
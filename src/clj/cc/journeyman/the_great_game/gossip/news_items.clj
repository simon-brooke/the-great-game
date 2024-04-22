(ns cc.journeyman.the-great-game.gossip.news-items
  "Using news items (propositions) to transfer knowledge between gossip agents.
   
   ## Status
   
   What is here is essentially working. It's not, however, working with the 
   rich data objects which will be needed, and it's not yet nearly efficient 
   enough, but it allows knowledge to propagate through the world procedurally,
   at a rate limited by the speed of movement of the gossip agents.

   ## Discussion
   
   The ideas here are based on the essay [The spread of knowledge in a large
   game world](The-spread-of-knowledge-in-a-large-game-world.html), q.v.; 
   they've advanced a little beyond that and will doubtless
   advance further in the course of writing and debugging this namespace.

   A news item is a map with the keys:
 
   * `date` - the date on which the reported event is claimed to have happened;
   * `nth-hand` - the number of agents the news item has passed through;
   * `verb` - what it is that happened (key into `news-topics`);

   plus other keys taken from the `keys` value associated with the verb in
   `news-topics`.
   
   ## Notes:
   
   *TODO*   
   This namespace at present considers the `:knowledge` of a gossip to be a flat
   list of propositions, each of which must be checked every time any new
   proposition is offered. This is woefully inefficient. "
  (:require [clojure.set :refer [union]]
            [cc.journeyman.the-great-game.world.location :refer [distance-between]]
            [cc.journeyman.the-great-game.time :refer [game-time]]
            [cc.journeyman.the-great-game.utils :refer [inc-or-one truthy?]]
            [taoensso.timbre :as l]))

(declare interesting-location?)

(def news-topics
  "Topics of interest to gossip agents. Topics are keyed in this map by
  their `verbs`. The `keys` associated with each topic are the extra pieces
  of information required to give context to a gossip item. Generally:

  * `actor` is the id of the character who it is reported performed the
  action;
  * `other` is the id of the character on whom it is reported the action
  was performed;
  * `location` is the place at which the action was performed;
  * `object` is an object (or possibly list of objects?) relevant to the
  action;
  * `price` is special to buy/sell, but of significant interest to merchants.

  ## Characters

  *TODO* but note that at most all the receiver can learn about a character
  from a news item is what the giver knows about that character, degraded by
  what the receiver finds interesting about them. If we just pass the id here,
  then either the receiver knows everything in the database about the
  character, or else the receiver knows nothing at all about the character.
  Neither is desirable. Further thought needed.

  By implication, the character values passed should include *all* the
  information the giver knows about the character; that can then be degraded
  as the receiver stores only that segment which the receiver finds
  interesting.

  ## Locations

  A 'location' value is a list comprising at most the x/y coordinate location
  and the ids of the settlement and region (possibly hierarchically) that contain
  the location. If the x/y is not local to the home of the receiving agent, they
  won't remember it and won't pass it on; if any of the ids are not interesting
  So location information will degrade progressively as the item is passed along.

  It is assumed that the `:home` of a character is a location in this sense.

  ## Inferences

  If an agent learns that Adam has married Betty, they can infer that Betty has
  married Adam; if they learn that Charles killed Dorothy, that Dorothy has died.
  I'm not convinced that my representation of inferences here is ideal."
  {;; A significant attack is interesting whether or not it leads to deaths
   :attack {:verb :attack :keys [:actor :other :location]}
    ;; Deaths of characters may be interesting
   :die {:verb :die :keys [:actor :location]}
    ;; Deliberate killings are interesting.
   :kill {:verb :kill :keys [:actor :other :location]
          :inferences [{:verb :die :actor :other :other :nil}]}
    ;; Marriages may be interesting
   :marry {:verb :marry :keys [:actor :other :location]
           :inferences [{:verb :marry :actor :other :other :actor}]}
    ;; The end of ongoing open conflict between to characters may be interesting
   :peace {:verb :peace :keys [:actor :other :location]
           :inferences [{:verb :peace :actor :other :other :actor}]}
    ;; Things related to the plot are interesting, but will require special
    ;; handling. Extra keys may be required by particular plot events.
   :plot {:verb :plot :keys [:actor :other :object :location]}
    ;; Rapes are interesting.
   :rape {:verb :rape :keys [:actor :other :location]
           ;; Should you also infer from rape that actor is male and adult?
          :inferences [{:verb :attack}
                       {:verb :sex}
                       {:verb :sex :actor :other :other :actor}]}
    ;; Merchants, especially, are interested in prices in other markets
   :sell {:verb :sell :keys [:actor :other :object :location :quantity :price]}
    ;; Sex can juicy gossip, although not normally if the participants are in an
    ;; established sexual relationship.
   :sex {:verb :sex :keys [:actor :other :location]
         :inferences [{:verb :sex :actor :other :other :actor}]}
    ;; Thefts are interesting.
   :steal {:verb :steal :keys [:actor :other :object :location]}
    ;; The succession of rulers is interesting; of respected craftsmen,
    ;; potentially also interesting.
   :succession {:verb :succession :keys [:actor :other :location :rank]}
    ;; The start of ongoing open conflict between two characters may be interesting.
   :war {:verb :war :keys [:actor :other :location]
         :inferences [{:verb :war :actor :other :other :actor}]}})

(def all-known-verbs
  "All verbs currently known to the gossip system."
  (set (keys news-topics)))

(defn interest-in-character
  "Integer representation of how interesting this `character` is to this
  `gossip`.
  *TODO:* this assumes that characters are passed as keywords, but, as
  documented above, they probably have to be maps, to allow for degradation."
  [gossip character]
  (count
   (concat
    ;; TODO: we ought also check the relationships of the gossip.
    ;; Are relationships just propositions in the knowledge base?
    (filter #(= (:actor %) character) (:knowledge gossip))
    (filter #(= (:other %) character) (:knowledge gossip))
    (when (interesting-location? gossip (:home character))
      (list true)))))

(defn interesting-character?
  "Boolean representation of whether this `character` is interesting to this
  `gossip`."
  [gossip character]
  (> (interest-in-character gossip character) 0))

(defn interest-in-location
  "Integer representation of how interesting this `location` is to this
  `gossip`."
  [gossip location]
  (cond
    (and (map? location) (number? (:x location)) (number? (:y location)))
    (if-let [home (:home gossip)]
      (let [d (distance-between location home)
            i (if
               (zero? d) 1
               (/ 10000 d))
            ;; 10000 at metre scale is 10km; interest should
            ;;fall off with distance from home, but possibly on a log scale
            ]
        (if (>= i 1) i 0))
      0)
    (coll? location)
    (reduce
     +
     (map
      #(interest-in-location gossip %)
      location))
    :else
    (count
     (filter
      #(some (fn [x] (= x location)) (:location %))
      (cons {:location (:home gossip)} (:knowledge gossip))))))

;; (distance-between {:x 25 :y 37} {:x 25 :y 37})
;; (interest-in-location {:home [{0, 0} :test-home] :knowledge []} [:test-home])

(defn interesting-location?
  "True if the location of this news `item` is interesting to this `gossip`."
  [gossip location]
  (> (interest-in-location gossip location) 0))

(defn interesting-object?
  [gossip object]
  ;; TODO: Not yet (really) implemented
  true)

(defn interesting-verb?
  "Is this `verb` interesting to this `gossip`?"
  [gossip verb]
  (let [vs (:interesting-verbs gossip)]
    (truthy?
     (when (set? vs)
       (vs verb)))))

;; (interesting-verb? {:interesting-verbs #{:kill :sell}} :sell)

(defn compatible-value?
  "True if `known-value` is the same as `new-value`, or, for each key present
   in `new-value`, has the same value for that key. 
   
   The rationale here is that if `new-value` contains new or different 
   information, it's worth learning; otherwise, not."
  [new-value known-value]
  (or
   (= new-value known-value)
   ;; TODO: some handwaving here about being a slightly better descriptor --
   ;; having more keys than might 
   (when (and (map? new-value) (map? known-value))
     (every? true? (map #(= (new-value %) (known-value %))
                        (keys new-value))))))

(defn compatible-item?
  "True if `new-item` is identical with, or less specific than, `known-item`.
   
   If we already know 'Bad Joe killed Sweet Daisy', there's no point in 
   learning that 'someone killed Sweet Daisy', but there is point in learning
   'someone killed Sweet Daisy _with poison_'."
  [new-item known-item]
  (truthy?
   (reduce
    #(and %1 %2)
    (map #(if
           (known-item %) ;; if known-item has this key
            (compatible-value? (new-item %) (known-item %))
            true)
         (remove #{:nth-hand :confidence :learned-from} (keys new-item))))))

(defn known-item?
  "True if this news `item` is already known to this `gossip`.
   
   This means that the `gossip` already knows an item which identifiably has
   the same _or more specific_ values for all the keys of this `item` except
   `:nth-hand`, `:confidence` and `:learned-from`."
  [gossip item]
  (truthy?
   (reduce
    #(or %1 %2)
    false
    (filter true? (map #(compatible-item? item %) (:knowledge gossip))))))

(defn interesting-item?
  "True if anything about this news `item` is interesting to this `gossip`."
  [gossip item]
  (and (not (known-item? gossip item))
       (interesting-verb? gossip (:verb item)) ;; news is only interesting if the topic is.
       (or
        (interesting-character? gossip (:actor item))
        (interesting-character? gossip (:other item))
        (interesting-location? gossip (:location item))
        (interesting-object? gossip (:object item)))))

(defn infer
  "Infer a new knowledge item from this `item`, following this `rule`."
  [item rule]
  (l/info "Applying rule '" rule "' to item '" item "'")
  (reduce merge
          item
          (cons
           {:verb (:verb rule)
            :nth-hand (inc-or-one (:nth-hand item))}
           (map (fn [k] {k (item (rule k))})
                (remove
                 #{:verb :nth-hand}
                 (keys rule))))))

(declare learn-news-item)

(defn make-all-inferences
  "Return a set of knowledge entries that can be inferred from this news
  `item`."
  [item]
  (set
   (map
    #(infer item %)
    (:inferences (news-topics (:verb item))))))

(defn degrade-character
  "Return a character specification like this `character`, but comprising
  only those properties this `gossip` is interested in."
  [gossip character]
  ;; TODO: Not yet (really) implemented
  character)

(defn degrade-location
  "Return a location specification like this `location`, but comprising
  only those elements this `gossip` is interested in. If none, return
  `nil`."
  [gossip location]
  (let [l (when
           (coll? location)
            (filter
             #(when (interesting-location? gossip %) %)
             location))]
    (when-not (empty? l) l)))

(defn degrade-news-item
  [gossip item]
  (assoc
   item
   :nth-hand (inc-or-one (:nth-hand item))
   :time-stamp (if
                (number? (:time-stamp item))
                 (:time-stamp item)
                 (game-time))
   :location (degrade-location gossip (:location item))
   :actor (degrade-character gossip (:actor item))
   :other (degrade-character gossip (:other item))
                  ;; TODO: do something to degrade confidence in the item,
                  ;; probably as a function of the provider's confidence in
                  ;; the item and the gossip's trust in the provider
   ))

;; (degrade-news-item {:home [{:x 25 :y 37} :auchencairn :scotland]}
;;                   {:verb :marry :actor :adam :other :belinda :location [{:x 25 :y 37} :auchencairn :scotland]})

(defn learn-news-item
  "Return a gossip like this `gossip`, which has learned this news `item` if
  it is of interest to them."
  ([gossip item]
   (learn-news-item gossip item true))
  ([gossip item follow-inferences?]
   (if
    (interesting-item? gossip item)
     (let [item' (degrade-news-item gossip item)
           g (assoc
              gossip
              :knowledge
              (set
               (cons
                item'
                (:knowledge gossip))))]
       (if follow-inferences?
         (assoc
          g
          :knowledge
          (union (:knowledge g) (make-all-inferences item')))
         g))
     gossip)))




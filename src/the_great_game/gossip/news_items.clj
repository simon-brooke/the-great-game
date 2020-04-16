(ns the-great-game.gossip.news-items
  "Categories of news events interesting to gossip agents"
  (:require [the-great-game.world.location :refer [distance-between]]
            [the-great-game.time :refer [game-time]]))

;; The ideas here are based on the essay 'The spread of knowledge in a large
;; game world', q.v.; they've advanced a little beyond that and will doubtless
;; advance further in the course of writing and debugging this namespace.

;; A news item is a map with the keys:
;;
;; * `date` - the date on which the reported event happened;
;; * `nth-hand` - the number of agents the news item has passed through;
;; * `verb` - what it is that happened (key into `news-topics`);
;;
;; plus other keys taken from the `keys` value associated with the verb in
;; `news-topics`

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

  #### Notes:

  ##### Characters:

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

  ##### Locations:

  A 'location' value is a list comprising at most the x/y coordinate location
  and the ids of the settlement and region (possibly hierarchically) that contain
  the location. If the x/y is not local to the home of the receiving agent, they
  won't remember it and won't pass it on; if any of the ids are not interesting
  So location information will degrade progressively as the item is passed along.

  It is assumed that the `:home` of a character is a location in this sense.

  ##### Inferences:

  If an agent learns that Adam has married Betty, they can infer that Betty has
  married Adam; if they learn that Charles killed Dorothy, that Dorothy has died.
  I'm not convinced that my representation of inferences here is ideal.
  "
  { ;; A significant attack is interesting whether or not it leads to deaths
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
    :sell {:verb :sell :keys [:actor :other :object :location :price]}
    ;; Sex can juicy gossip, although not normally if the participants are in an
    ;; established sexual relationship.
    :sex {:verb :sex :keys [:actor :other :location]
          :inferences [{:verb :sex :actor :other :other :actor}]}
    ;; Thefts are interesting
    :steal {:verb :steal :keys [:actor :other :object :location]}
    ;; The succession of rulers is interesting; of respected craftsmen,
    ;; potentially also interesting.
    :succession {:verb :succession :keys [:actor :other :location :rank]}
    ;; The start of ongoing open conflict between to characters may be interesting
    :war {:verb :war :keys [:actor :other :location]
          :inferences [{:verb :war :actor :other :other :actor}]}
    })


(defn interest-in-character
  "Integer representation of how interesting this `character` is to this
  `gossip`.
  *TODO:* this assumes that characters are passed as keywords, but, as
  documented above, they probably have to be maps, to allow for degradation."
  [gossip character]
  (count
    (concat
      (filter #(= (:actor % character)) (:knowledge gossip))
      (filter #(= (:other % character)) (:knowledge gossip)))))

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
            i (/ 10000 d) ;; 10000 at metre scale is 10km; interest should
            ;;fall off with distance from home, but possibly on a log scale
            ]
        (if (> i 1) i 0))
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

;; (interest-in-location {:home [{0, 0} :test-home] :knowledge []} [:test-home])

(defn interesting-location?
  "True if the location of this news `item` is interesting to this `gossip`."
  [gossip item]
  (> (interest-in-location gossip (:location item)) 0))

(defn interesting-object?
  [gossip object]
  ;; TODO: Not yet (really) implemented
  true)

(defn interesting-topic?
  [gossip topic]
  ;; TODO: Not yet (really) implemented
  true)

(defn interesting-item?
  "True if anything about this news `item` is interesting to this `gossip`."
  [gossip item]
     (or
       (interesting-character? gossip (:actor item))
       (interesting-character? gossip (:other item))
       (interesting-location? gossip (:location item))
       (interesting-object? gossip (:object item))
       (interesting-topic? gossip (:verb item))))

(defn infer
  "Infer a new knowledge item from this `item`, following this `rule`"
  [item rule]
  (reduce merge
          item
          (cons
            {:verb (:verb rule)}
            (map (fn [k] {k (apply (k rule) (list item))})
                 (remove
                   #(= % :verb)
                   (keys rule))))))

(declare learn-news-item)

(defn make-all-inferences
  "Return a list of knowledge entries that can be inferred from this news
  `item`."
  [item]
  (set
    (reduce
      concat
      (map
        #(:knowledge (learn-news-item {} (infer item %) false))
        (:inferences (news-topics (:verb item)))))))

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
  (let [l (if
    (coll? location)
    (filter
      #(when (interesting-location? gossip %) %)
      location))]
    (when-not (empty? l) l)))

(defn learn-news-item
  "Return a gossip like this `gossip`, which has learned this news `item` if
  it is of interest to them."
  ;; TODO: Not yet implemented
  ([gossip item]
   (learn-news-item gossip item true))
  ([gossip item follow-inferences?]
   (if
     (interesting-item? gossip item)
     (let
       [g (assoc
            gossip
            :knowledge
            (cons
              (assoc
                item
                :nth-hand (if
                            (number? (:nth-hand item))
                            (inc (:nth-hand item))
                            1)
                :time-stamp (if
                              (number? (:time-stamp item))
                              (:time-stamp item)
                              (game-time))
                :location (degrade-location gossip (:location item))
                ;; TODO: ought to maybe-degrade characters we're not yet interested in
                )
              ;; TODO: ought not to add knowledge items we already have, except
              ;; to replace if new item is of increased specificity
              (:knowledge gossip)))]
       (if follow-inferences?
         (assoc
           g
           :knowledge
           (concat (:knowledge g) (make-all-inferences item)))
         g))
     gossip)))




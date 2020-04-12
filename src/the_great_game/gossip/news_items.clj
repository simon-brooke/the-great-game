(ns the-great-game.gossip.news-items
  "Categories of news events interesting to gossip agents"
  (:require [clojure.math.numeric-tower :refer [expt sqrt]]))

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

  * `actor` is the id of the character who performed the action;
  * `other` is the id of the character on whom the action was performed;
  * `location` is the place at which the action was performed;
  * `object` is an object (or possibly list of objects?) relevant to the action;
  * `price` is special to buy/sell, but of significant interest to merchants.

  #### Notes:

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
    :die {:verb :attack :keys [:actor :location]}
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
  `gossip`."
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

(defn get-coords
  "Return the coordinates in the game world of `location`, which may be
  1. A coordinate pair in the format {:x 5 :y 32};
  2. A location, as discussed above;
  3. Any other gameworld object, having a `:location` property whose value
    is one of the above."
  [location]
  (cond
    (empty? location) nil
    (map? location)
    (cond
      (and (number? (:x location)) (number? (:y location)))
      location
      (:location location)
      (:location location))
    :else
    (get-coords (first (remove keyword? location)))))

;; (get-coords {:x 5 :y 7})
;; (get-coords [{:x -4 :y 55} :auchencairn :galloway :scotland])

(defn distance-between
  [location-1 location-2]
  (let [c1 (get-coords location-1)
        c2 (get-coords location-2)]
    (if
      (and c1 c2)
      (sqrt (+ (expt (- (:x c1) (:x c2)) 2) (expt (- (:y c1) (:y c2)) 2))))))

;; (distance-between {:x 5 :y 5} {:x 2 :y 2})
;; (distance-between {:x 5 :y 5} {:x 2 :y 5})
;; (distance-between {:x 5 :y 5} [{:x -4 :y 55} :auchencairn :galloway :scotland])
;; (distance-between {:x 5 :y 5} [:auchencairn :galloway :scotland])

(defn interest-in-location
  "Integer representation of how interesting this `location` is to this
  `gossip`."
  [gossip location]
  (cond
    (coll? location)
    (reduce
      +
      (map
        #(interest-in-location gossip %)
        location))
    (and (map? location) (:x location) (:y location))
    (if-let [home (:home gossip)]
      (let [d (distance-between location home)
            i (/ 10000 d) ;; 10000 at metre scale is 10km; interest should
            ;;fall of with distance from home, but possibly on a log scale
            ]
        (if (i > 1) i 0)
        i))
    :else
    (count
      (filter
        #(some (fn [x] (= x location)) (:location %))
        (:knowledge gossip)))))

;; (interest-in-location
;;   {:knowledge [{:verb :steal
;;              :actor :albert
;;              :other :belinda
;;              :object :foo
;;              :location [{:x 35 :y 23} :auchencairn :galloway]}]}
;;   :galloway)

;; (interest-in-location
;;   {:knowledge [{:verb :steal
;;              :actor :albert
;;              :other :belinda
;;              :object :foo
;;              :location [{:x 35 :y 23} :auchencairn :galloway]}]}
;;   [:galloway :scotland])


;; (interest-in-location
;;   {:knowledge [{:verb :steal
;;              :actor :albert
;;              :other :belinda
;;              :object :foo
;;              :location [{:x 35 :y 23} :auchencairn :galloway]}]}
;;   :dumfries)

;; (interest-in-location
;;   {:home {:x 35 :y 23}}
;;   {:x 35 :y 24})

(defn interesting-location?
  "True if the location of this news `item` is interesting to this `gossip`."
  [gossip item]
  (> (interest-in-location gossip (:location item)) 1))

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

;; (infer {:verb :marry :actor :adam :other :belinda}
;;        {:verb :marry :actor :other :other :actor})
;; (infer {:verb :rape :actor :adam :other :belinda}
;;        {:verb :attack})
;; (infer {:verb :rape :actor :adam :other :belinda}
;;        {:verb :sex :actor :other :other :actor})

(defn learn-news-item
  "Return a gossip like this `gossip`, which has learned this news `item` if
  it is of interest to them."
  ([gossip item]
   (learn-news-item gossip item false))
  ([gossip item follow-inferences?]
   (if
     (interesting-item? gossip item)
     (let [g (assoc gossip :knowledge
               (cons
                 (assoc
                   item
                   :nth-hand (if
                               (number? (:nth-hand item))
                               (inc (:nth-hand item))
                               1)
                   ;; ought to degrate the location
                   ;; ought to maybe-degrade characters we're not yet interested in
                   )
                 ;; ought not to add knowledge items we already have, except
                 ;; to replace if new item is of increased specificity
                 (:knowledge gossip)))]
       (if follow-inferences?
         (reduce
           merge
           g
           (map
             #(learn-news-item gossip (infer item %) false)
             (:inferences (news-topics (:verb item))))))))))


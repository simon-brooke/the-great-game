(ns cc.journeyman.the-great-game.gossip.gossip
  "Interchange of news events between gossip agents.
   
   Note that habitual travellers are all gossip agents; specifically, at this
   stage, that means merchants. When merchants are moved we also need to
   update the location of the gossip with the same key.
   
   Innkeepers are also gossip agents but do not typically move."
  (:require [cc.journeyman.the-great-game.utils :refer [deep-merge]]
            [cc.journeyman.the-great-game.gossip.news-items :refer [learn-news-item]]
            ))


(defn dialogue
  "Dialogue between an `enquirer` and an `agent` in this `world`; returns a
  map identical to `enquirer` except that its `:gossip` collection may have
  additional entries."
  ;; TODO: not yet written, this is a stub.
  [enquirer respondent world]
  enquirer)

(defn gather-news
  "Gather news for the specified `gossip` in this `world`."
  [world gossip]
  (let [g (cond (keyword? gossip)
                (-> world :gossips gossip)
                (map? gossip)
                gossip)]
    (if g
      {:gossips
       {(:id g)
        (reduce
         deep-merge
         {}
         (map
          #(dialogue g % world)
          (remove
           #(= g %)
           (filter
            #(= (:location %) (:location g))
            (vals (:gossips world))))))}}
      {})))

(defn move-gossip
  "Return a world like this `world` but with this `gossip` moved to this
  `new-location`. Many gossips are essentially shadow-records of agents of
  other types, and the movement of the gossip should be controlled by the
  run function of the type of the record they shadow. The [[#run]] function
  below does NOT call this function."
  [gossip world new-location]
  (let [id (cond
            (map? gossip)
            (-> world :gossips gossip :id)
            (keyword? gossip)
            gossip)]
  (deep-merge
    world
    {:gossips
     {id
      {:location new-location}}})))

(defn run
  "Return a world like this `world`, with news items exchanged between gossip
  agents."
  [world]
  (reduce
   deep-merge
   world
   (map
    #(gather-news world %)
    (keys (:gossips world)))))



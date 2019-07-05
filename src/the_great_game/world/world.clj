(ns the-great-game.world.world
  "Access to data about the world")

;;; The world has to work either as map or a database. Initially, and for
;;; unit tests, I'll use a map; later, there will be a database. But the
;;; API needs to be agnostic, so that heirarchies which interact with
;;; `world` don't have to know which they've got - as far as they're concerned
;;; it's just a handle.

(def default-world
  "A basic world for testing concepts"
  {:date 0 ;; the age of this world in game days
   :cities
   {:aberdeen
    {:id :aberdeen
     :supplies
     ;; `supplies` is the quantity of each commodity added to the stock
     ;; each game day. If the price in the market is lower than 1 (the
     ;; cost of production of a unit) no goods will be added.
     {:fish 10
      :leather 5}
     :demands
     ;; `stock` is the quantity of each commodity in the market at any
     ;; given time. It is adjusted for production and consumption at
     ;; the end of each game day.
     {:iron 1
      :cloth 10
      :whisky 10}
     :port true
     :prices
     ;; `prices`: the current price (both buying and selling, for simplicity)
     ;; of each commodity in the market. Updated each game day based on current
     ;; stock.
     {:cloth 1
      :fish 1
      :leather 1
      :iron 1
      :whisky 1}
     :stock
     ;; `stock` is the quantity of each commodity in the market at any
     ;; given time. It is adjusted for production and consumption at
     ;; the end of each game day.
     {:cloth 0
      :fish 0
      :leather 0
      :iron 0
      :whisky 0}
     :cash 100}
    :buckie
    {:id :buckie
     :supplies
     {:fish 20}
     :demands
     {:cloth 5
      :leather 3
      :whisky 5
      :iron 1}
     :port true
     :prices {:cloth 1
              :fish 1
              :leather 1
              :iron 1
              :whisky 1}
     :stock {:cloth 0
             :fish 0
             :leather 0
             :iron 0
             :whisky 0}
     :cash 100}
    :callander
    {:id :callander
     :supplies {:leather 20}
     :demands
     {:cloth 5
      :fish 3
      :whisky 5
      :iron 1}
     :prices {:cloth 1
              :fish 1
              :leather 1
              :iron 1
              :whisky 1}
     :stock {:cloth 0
             :fish 0
             :leather 0
             :iron 0
             :whisky 0}
     :cash 100}
    :dundee {:id :dundee}
    :edinburgh {:id :dundee}
    :falkirk
    {:id :falkirk
     :supplies {:iron 10}
     :demands
     {:cloth 5
      :leather 3
      :whisky 5
      :fish 10}
     :port true
     :prices {:cloth 1
              :fish 1
              :leather 1
              :iron 1
              :whisky 1}
     :stock {:cloth 0
             :fish 0
             :leather 0
             :iron 0
             :whisky 0}
     :cash 100}
    :glasgow
    {:id :glasgow
     :supplies {:whisky 10}
     :demands
     {:cloth 5
      :leather 3
      :iron 5
      :fish 10}
     :port true
     :prices {:cloth 1
              :fish 1
              :leather 1
              :iron 1
              :whisky 1}
     :stock {:cloth 0
             :fish 0
             :leather 0
             :iron 0
             :whisky 0}
     :cash 100}}
   :merchants
   {:archie {:id :archie
             :home :aberdeen :location :aberdeen :cash 100 :capacity 10
             :known-prices {}
             :stock {}}
    :belinda {:id :belinda
              :home :buckie :location :buckie :cash 100 :capacity 10
              :known-prices {}
              :stock {}}
    :callum {:id :callum
             :home :callander :location :calander :cash 100 :capacity 10
             :known-prices {}
             :stock {}}
    :deirdre {:id :deidre
              :home :dundee :location :dundee :cash 100 :capacity 10
              :known-prices {}
              :stock {}}
    :euan {:id :euan
           :home :edinbirgh :location :edinburgh :cash 100 :capacity 10
             :known-prices {}
             :stock {}}
    :fiona {:id :fiona
            :home :falkirk :location :falkirk :cash 100 :capacity 10
            :known-prices {}
            :stock {}}}
   :routes
   ;; all routes can be traversed in either direction and are assumed to
   ;; take the same amount of time.
   [[:aberdeen :buckie]
    [:aberdeen :dundee]
    [:callander :glasgow]
    [:dundee :callander]
    [:dundee :edinburgh]
    [:dundee :falkirk]
    [:edinburgh :falkirk]
    [:falkirk :glasgow]]
   :commodities
   ;; cost of commodities is expressed in person/days;
   ;; weight in packhorse loads. Transport in this model
   ;; is all overland; you don't take bulk cargoes overland
   ;; in this period, it's too expensive.
   {:cloth {:id :cloth :cost 1 :weight 0.25}
    :fish {:id :fish :cost 1 :weight 1}
    :leather {:id :leather :cost 1 :weight 0.5}
    :whisky {:id :whisky :cost 1 :weight 0.1}
    :iron {:id :iron :cost 1 :weight 10}}})

(defn actual-price
  "Find the actual current price of this `commodity` in this `city` given
  this `world`. **NOTE** that merchants can only know the actual prices in
  the city in which they are currently located."
  [world commodity city]
  (-> world :cities city :prices commodity))

(defn run
  "Return a world like this `world` with only the `:date` to this `date`
  (or id `date` not supplied, the current value incremented by one). For
  running other aspects of the simulation, see [[the-great-game.world.run]]."
  ([world]
   (run world (inc (or (:date world) 0))))
  ([world date]
   (assoc world :date date)))

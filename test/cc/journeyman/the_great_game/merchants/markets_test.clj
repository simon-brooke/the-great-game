(ns cc.journeyman.the-great-game.merchants.markets-test
  (:require [clojure.test :refer :all]
            [cc.journeyman.the-great-game.utils :refer [deep-merge]]
            [cc.journeyman.the-great-game.world.world :refer [default-world]]
            [cc.journeyman.the-great-game.merchants.markets :refer [adjust-quantity-and-price new-price run]]))


(deftest new-price-test
  (testing "Adjustment of prices based on supply and demand"
    (is (> (new-price 1 0 10 10) 1)
        "If no stock but there is supply and demand, price should rise")
    (is (> (new-price 1 0 0 10) 1)
        "If no stock but there is demand, price should rise")
    (is (> (new-price 1 5 0 10) 1)
        "If there is insufficient stock to meet demand, price should rise")
    (is (= (new-price 1 10 0 10) 1)
        "If there is exactly sufficient stock to meet demand, price should not change")
    (is (< (new-price 1 10 0 5) 1)
        "If there is surplus stock beyond demand, price should fall")
    (is (> (new-price 1 0 0 10) (new-price 1 5 0 10))
        "The greater the relative undersupply, the more prices should rise")
    (is (< (new-price 1 10 0 0)(new-price 1 10 0 5) 1)
        "The greater the relative oversupply, the more prices should fall")
    ))

(deftest adjust-quantity-and-price-test
  (testing "Adjustment in quantity and price: supply only."
    (let [world (deep-merge
                  default-world
                  {:cities
                   {:falkirk
                    {:stock {:iron 0}
                     :supplies {:iron 12}}}})
          actual (adjust-quantity-and-price world :falkirk :iron)]
      (is
        (=
          (-> actual :cities :falkirk :stock :iron)
          (-> world :cities :falkirk :supplies :iron))
        "If stock is empty it should be topped up by supply amount."))
    (let [world (deep-merge
                  default-world
                  {:cities
                   {:falkirk
                    {:stock {:iron 5}
                     :supplies {:iron 12}
                     :prices {:iron 0.9}}}})
          actual (adjust-quantity-and-price world :falkirk :iron)]
      (is
        (=
          (-> actual :cities :falkirk :stock :iron)
          (-> world :cities :falkirk :supplies :iron))
        "If stock is not empty and price is below cost, stock should be topped up only to supply amount."))
    (let [world (deep-merge
                  default-world
                  {:cities
                   {:falkirk
                    {:stock {:iron 5}
                     :supplies {:iron 12}
                     :prices {:iron 1.1}}}})
          actual (adjust-quantity-and-price world :falkirk :iron)]
      (is
        (=
          (-> actual :cities :falkirk :stock :iron)
          (+ (-> world :cities :falkirk :supplies :iron)
             (-> world :cities :falkirk :stock :iron)))
        "If stock is not empty and price is above cost, stock should be topped up by supply amount.")))
  (testing "Adjustment in quantity and price: supply and demand."
    (let [world (deep-merge
                  default-world
                  {:cities
                   {:falkirk
                    {:stock {:iron 10}
                     :demands {:iron 5}
                     :supplies {:iron 12}
                     :prices {:iron 1.1}}}})
          actual (adjust-quantity-and-price world :falkirk :iron)]
      (is
        (=
          (-> actual :cities :falkirk :stock :iron)
          17)
        "Stock should be topped up by the difference between the supply and
        the demand amount."))))

(deftest run-test
  (let [world (deep-merge
                default-world
                {:cities
                 {:aberdeen
                  {:stock {:fish 5}
                   :supplies {:fish 12}
                   :prices {:fish 1.1}}
                  :falkirk
                  {:stock {:iron 10}
                   :demands {:iron 5}
                   :supplies {:iron 12}
                   :prices {:iron 1.1}}}})
        actual (run world)]
    (is
      (=
        (-> actual :cities :aberdeen :stock :fish)
        (+ (-> world :cities :aberdeen :supplies :fish)
           (-> world :cities :aberdeen :stock :fish)))
      "If stock is not empty and price is above cost, stock should be topped up by supply amount.")
    (is
      (=
        (-> actual :cities :falkirk :stock :iron)
        17)
      "Stock should be topped up by the difference between the supply and
      the demand amount.")))

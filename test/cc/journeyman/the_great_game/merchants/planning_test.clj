(ns cc.journeyman.the-great-game.merchants.planning-test
  (:require [clojure.test :refer :all]
            [cc.journeyman.the-great-game.utils :refer [deep-merge]]
            [cc.journeyman.the-great-game.world.world :refer [default-world]]
            [cc.journeyman.the-great-game.merchants.planning :refer [plan-trade select-cargo]]))


(deftest plan-trade-test
  (testing "Lower level trade planning"
    (let [world (deep-merge
                  default-world
                  {:merchants
                   {:fiona
                    {:known-prices
                     {:aberdeen
                      {:iron
                       [{:price 1.5 :date 1}
                        {:price 1.3 :date 0}]}
                      :buckie
                      {:iron
                       [{:price 1.7 :date 1}
                        {:price 2 :date 0}]}}}}
                   :cities
                   {:falkirk
                    {:stock {:iron 20}}}})
          actual (plan-trade :fiona world :iron)]
      (is (= (:origin actual) :falkirk)
          "Fiona is in Falkirk, so her plan must originate there")
      (is (= (:commodity actual) :iron)
          "Iron is the only thing available in Falkirk, so plan must carry iron")
      (is (= (:destination actual) :buckie)
          "Fiona believes Buckie offers the best price for iron, so should go there"))))


(deftest select-cargo-test
  (testing "Top level single trade planning: single candidate commodity"
    (let [world (deep-merge
                  default-world
                  {:merchants
                   {:fiona
                    {:known-prices
                     {:aberdeen
                      {:iron
                       [{:price 1.5 :date 1}
                        {:price 1.3 :date 0}]}
                      :buckie
                      {:iron
                       [{:price 1.7 :date 1}
                        {:price 2 :date 0}]}}}}
                   :cities
                   {:falkirk
                    {:stock {:iron 20}}}})
          actual (select-cargo :fiona world)]
      (is (= (:origin actual) :falkirk)
          "Fiona is in Falkirk, so her plan must originate there")
      (is (= (:commodity actual) :iron)
          "Iron is the only thing available in Falkirk, so plan must carry iron")
      (is (= (:destination actual) :buckie)
          "Fiona believes Buckie offers the best price for iron, so should go there")
      (is (= (:quantity actual) 1)
          "Fiona can carry only one unit of iron.")
      (is (= (:expected-profit actual) 0.7))))
  (testing "Top level single trade planning: multiple candidate commodities"
    (let [world (deep-merge
                  default-world
                  {:merchants
                   {:fiona
                    {:known-prices
                     {:aberdeen
                      {:iron
                       [{:price 1.5 :date 1}
                        {:price 1.3 :date 0}]
                       :whisky [{:price 4 :date 0}]}
                      :buckie
                      {:iron
                       [{:price 1.7 :date 1}
                        {:price 2 :date 0}]}}}}
                   :cities
                   {:falkirk
                    {:stock
                     {:iron 20
                      :whisky 50}}}})
          actual (select-cargo :fiona world)]
      (is (= (:origin actual) :falkirk)
          "Fiona is in Falkirk, so her plan must originate there")
      (is (= (:commodity actual) :whisky)
          "Whisky has the higher profit, so plan must carry whisky")
      (is (= (:destination actual) :aberdeen)
          "Fiona believes Aberdeen offers the best price for whisky, so should go there")
      (is (= (:quantity actual) 50)
          "Fiona can carry 100 units of whisky, but only 50 are available.")
      (is (= (:expected-profit actual) 150)))))

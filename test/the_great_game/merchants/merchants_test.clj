(ns the-great-game.merchants.merchants-test
  (:require [clojure.test :refer :all]
            [the-great-game.utils :refer [deep-merge]]
            [the-great-game.world.world :refer [default-world]]
            [the-great-game.merchants.merchants :refer :all]))

(deftest expected-price-test
  (testing "Anticipated prices in markets"
    (let [world (deep-merge
                  default-world
                  {:merchants
                   {:archie
                    {:known-prices
                     {:buckie
                      {:iron
                       [{:price 1.7 :date 1}
                        {:price 2 :date 0}]}}}}})]
      (let [actual (expected-price (-> world :merchants :archie) :fish :edinburgh)
            expected 1] ;;
        (is (= actual expected) "if no information assume 1"))
      (let [actual (expected-price (-> world :merchants :archie) :iron :buckie)
            expected 1.7] ;;
        (is (= actual expected) "if information select the most recent")))))

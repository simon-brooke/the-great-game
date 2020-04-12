(ns the-great-game.merchants.merchant-utils-test
  (:require [clojure.test :refer :all]
            [the-great-game.utils :refer [deep-merge]]
            [the-great-game.world.world :refer [default-world]]
            [the-great-game.merchants.merchant-utils :refer :all]))

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

(deftest burden-test
  (testing "Burden of merchant"
    (let [world (deep-merge
                  default-world
                  {:merchants
                   {:archie
                    {:stock
                     {:iron 1}}
                    :belinda
                    {:stock
                     {:fish 2}}
                    :callum
                    {:stock
                     {:iron 1
                      :fish 1}}}})]
      (let [actual (burden :archie world)
            expected (-> world :commodities :iron :weight)]
        (is (= actual expected)))
      (let [actual (burden :belinda world)
            expected (* 2 (-> world :commodities :fish :weight))]
        (is (= actual expected)))
      (let [actual (burden :callum world)
            expected (+
                       (-> world :commodities :iron :weight)
                       (-> world :commodities :fish :weight))]
        (is (= actual expected)))
      (let [actual (burden {} world)
            expected 0]
        (is (= actual expected)))
      (let [actual (burden (-> world :merchants :deidre) world)
            expected 0]
        (is (= actual expected))))))

(deftest can-carry-test
  (testing "What merchants can carry"
    (let [world (deep-merge
                  default-world
                  {:merchants
                   {:archie
                    {:cash 5
                     :stock
                     {:iron 1}}
                    :belinda
                    {:stock
                     {:fish 2}}
                    :callum
                    {:stock
                     {:iron 1
                      :fish 1}}}})]
      (let [actual (can-carry :archie world :fish)
            expected 0]
        (is (= actual expected)))
      (let [actual (can-carry :belinda world :fish)
            expected 8]
        (is (= actual expected)))
      (let [actual (can-carry (-> world :merchants :archie) world :fish)
            expected 0]
        (is (= actual expected)))
      (let [actual (can-carry {:stock {:fish 7} :capacity 10} world :fish)
            expected 3]
        (is (= actual expected))))))


(deftest affordability-test
  (testing "What merchants can afford to purchase"
    (let [world (deep-merge
                  default-world
                  {:merchants
                   {:archie
                    {:cash 5
                     :stock
                     {:iron 1}}
                    :belinda
                    {:stock
                     {:fish 2}}
                    :callum
                    {:stock
                     {:iron 1
                      :fish 1}}}})]
      (let [actual (can-afford :archie world :fish)
            expected 5]
        (is (= actual expected)))
      (let [actual (can-afford :belinda world :fish)
            expected 100]
        (is (= actual expected)))
      (let [actual (can-afford (-> world :merchants :archie) world :fish)
            expected 5]
        (is (= actual expected)))
      (let [actual (can-afford {:cash 3 :location :buckie} world :fish)
            expected 3]
        (is (= actual expected)))
      (is (thrown-with-msg?
            Exception
            #"No merchant?"
            (can-afford :no-one world :fish)))
      (is (thrown-with-msg?
            Exception
            #"No known location for merchant.*"
            (can-afford {:cash 3} world :fish))))))

(deftest add-stock-test
  (let [actual (add-stock {:iron 2 :fish 5} {:fish 3 :whisky 7})
        expected {:iron 2 :fish 8 :whisky 7}]))

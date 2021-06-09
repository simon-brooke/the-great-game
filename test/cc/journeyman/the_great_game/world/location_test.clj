(ns cc.journeyman.the-great-game.world.location-test
  (:require [clojure.test :refer [deftest is testing]]
            [cc.journeyman.the-great-game.world.location :refer [distance-between get-coords]]))

(deftest get-coords-test
  (testing "Get coordinates of location"
    (let [expected {:x 5 :y 7}
          actual (get-coords {:x 5 :y 7})]
      (is (= actual expected)))
    (let [expected {:x -4 :y 55}
          actual (get-coords [{:x -4 :y 55} :auchencairn :galloway :scotland])]
      (is (= actual expected)))
    (let [expected nil
          actual (get-coords [:auchencairn :galloway :scotland])]
      (is (= actual expected)))
    ))

(deftest distance-test
  (testing "Distance between two locations"
    (let [expected 4.242640687119285
          actual (distance-between {:x 5 :y 5} {:x 2 :y 2})]
      (is (= actual expected)))
    (let [expected 3
          actual (distance-between {:x 5 :y 5} {:x 2 :y 5})]
      (is (= actual expected)))
    (let [expected 50.80354318352215
          actual (distance-between
                   {:x 5 :y 5}
                   [{:x -4 :y 55} :auchencairn :galloway :scotland])]
      (is (= actual expected)))
    (let [expected nil
          actual (distance-between
                   {:x 5 :y 5}
                   [:auchencairn :galloway :scotland])]
      (is (= actual expected)))
    ))

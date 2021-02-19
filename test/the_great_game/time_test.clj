(ns the-great-game.time-test
  (:require [clojure.test :refer :all]
;;            [clojure.core.async :refer [thread <!]]
            [the-great-game.time :refer :all]))

(deftest now-tests
  (testing "Time progresses"
    (let [t1 (now)]
      (is (> t1 game-start-time))
      (Thread/sleep 1000)
      (is (> (now) t1)))))

(deftest game-time-tests
  (testing "Getting game-time"
    (is (= (game-time (inc game-start-time)) 1))))

(deftest calendar-tests
  (testing "In-game calendar functions"
    (let [expected :foot
          actual (day 0)]
      (is (= actual expected)))
    (let [expected :stomach
          actual (day (* 5 game-day-length))]
      (is (= actual expected)))
    (let [expected :foot
          actual (day (* days-in-week game-day-length))]
      (is (= actual expected)))
    (let [expected :first ;; waiting day
          actual (day (* 360 game-day-length))]
      (is (= actual expected)))
    (let [expected :first
          actual (week 0)]
      (is (= actual expected)))
    (let [expected :second
          actual (week (* days-in-week game-day-length))]
      (is (= actual expected)))
    (let [expected :first
          actual (week (* days-in-season game-day-length))]
      (is (= actual expected)))
    (let [expected :foot
          actual (season 0)]
      (is (= actual expected)))
    (let [expected :mouth
          actual (season (* 180 game-day-length))]
      (is (= actual expected)))
    (let [expected :eye
          actual (season (* 359 game-day-length))]
      (is (= actual expected)))
    (let [expected :waiting
          actual (season (* 360 game-day-length))]
      (is (= actual expected)))
    (let [expected :foot
          actual (season (* 365 game-day-length))]
      (is (= actual expected)))))

(deftest date-string-tests
  (testing "Date-string formatting"
    (let [expected "First Foot of the Foot"
          actual (date-string 0)]
      (is (= actual expected)))
    (let [expected "First Foot of the Nose"
          actual (date-string
                   (* days-in-season game-day-length))]
      (is (= actual expected)))
    (let [expected "Third Mouth of the Mouth"
          actual (date-string (* 180 game-day-length))]
      (is (= actual expected)))
    (let [expected "Fifth Plough of the Eye"
          actual (date-string (* 359 game-day-length))]
      (is (= actual expected)))
    (let [expected "First waiting day"
          actual (date-string (* 360 game-day-length))]
      (is (= actual expected)))
    (let [expected "First Foot of the Foot"
          actual (date-string (* 365 game-day-length))]
      (is (= actual expected)))))




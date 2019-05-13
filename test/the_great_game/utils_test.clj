(ns the-great-game.utils-test
  (:require [clojure.test :refer :all]
            [the-great-game.utils :refer :all]))

(deftest cyclic-tests
  (testing "Detecting cyclic routes"
    (let [actual (cyclic? '(:glasgow :edinburgh :glasgow))
          expected true]
      (is (= actual expected)))
    (let [actual (cyclic? '(:edinburgh :dundee :aberdeen :buckie))
          expected false]
      (is (= actual expected)))))

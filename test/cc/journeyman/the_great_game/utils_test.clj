(ns cc.journeyman.the-great-game.utils-test
  (:require [clojure.test :refer :all]
            [cc.journeyman.the-great-game.utils :refer [cyclic?]]))

(deftest cyclic-tests
  (testing "Detecting cyclic routes"
    (let [actual (cyclic? '(:glasgow :edinburgh :glasgow))
          expected true]
      (is (= actual expected)))
    (let [actual (cyclic? '(:edinburgh :dundee :aberdeen :buckie))
          expected false]
      (is (= actual expected)))))

(ns the-great-game.world.routes-test
  (:require [clojure.test :refer :all]
            [the-great-game.world.routes :refer :all]
            [the-great-game.world.world :refer [default-world]]))


(deftest routing-test
  (testing "Routing: possible route"
    (let [origin :buckie
          destination :glasgow
          routes (find-routes (:routes default-world) origin destination)]
      (is
        (= (first (first routes)) origin)
        "Routes should be from the specified origin")
      (is
        (= (last (first routes)) destination)
        "Routes should be from the specified destination")
      (is
        (= (count (set (map first routes))) 1)
        "All routes should have the same origin")
      (is
        (= (count (set (map last routes))) 1)
        "All routes should have the same destination")
      (is
        (= (count (set (map count routes))) 1)
        "All selected routes should have the same length")
      ))
  (testing "Impossible route"
    (let [origin :buckie
          destination :london ;; not present in the routing map
          actual (find-routes (:routes default-world) origin destination)]
      (is (nil? actual) "There should be no route returned."))))


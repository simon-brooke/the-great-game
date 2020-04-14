(ns the-great-game.gossip.news-items-test
  (:require [clojure.test :refer :all]
            [the-great-game.gossip.news-items :refer :all]))


(deftest location-test
  (testing "Interest in locations"
    (let [expected 1
          actual (interest-in-location
                   {:knowledge [{:verb :steal
                                 :actor :albert
                                 :other :belinda
                                 :object :foo
                                 :location [{:x 35 :y 23} :auchencairn :galloway]}]}
                   :galloway)]
      (is (= actual expected)))
    (let [expected 2
          actual (interest-in-location
                   {:knowledge [{:verb :steal
                                 :actor :albert
                                 :other :belinda
                                 :object :foo
                                 :location [{:x 35 :y 23} :auchencairn :galloway :scotland]}]}
                   [:galloway :scotland])]
      (is (= actual expected)))
    (let [expected 0
          actual (interest-in-location
                   {:knowledge [{:verb :steal
                                 :actor :albert
                                 :other :belinda
                                 :object :foo
                                 :location [{:x 35 :y 23} :auchencairn :galloway]}]}
                   [:dumfries])]
      (is (= actual expected)))
    (let [expected 7071.067811865475
          actual (interest-in-location
                   {:home [{:x 35 :y 23}]}
                   [{:x 34 :y 24}])]
      (is (= actual expected)
          "TODO: 7071.067811865475 is actually a bad answer."))
    (let [expected 0
          actual (interest-in-location
                   {:home [{:x 35 :y 23}]}
                   [{:x 34 :y 24000}])]
      (is (= actual expected)
          "Too far apart (> 10000)."))
    (let [expected true
          actual (interesting-location?
                   {:knowledge [{:verb :steal
                                 :actor :albert
                                 :other :belinda
                                 :object :foo
                                 :location [{:x 35 :y 23} :auchencairn :galloway]}]}
                   :galloway)]
      (is (= actual expected)))
    (let [expected true
          actual (interesting-location?
                   {:knowledge [{:verb :steal
                                 :actor :albert
                                 :other :belinda
                                 :object :foo
                                 :location [{:x 35 :y 23} :auchencairn :galloway]}]}
                   [:galloway :scotland])]
      (is (= actual expected)))
    (let [expected false
          actual (interesting-location?
                   {:knowledge [{:verb :steal
                                 :actor :albert
                                 :other :belinda
                                 :object :foo
                                 :location [{:x 35 :y 23} :auchencairn :galloway]}]}
                   [:dumfries])]
      (is (= actual expected)))
    (let [expected true
          actual (interesting-location?
                   {:home [{:x 35 :y 23}]}
                   [{:x 34 :y 24}])]
      (is (= actual expected)))
    (let [expected false
          actual (interesting-location?
                   {:home [{:x 35 :y 23}]}
                   [{:x 34 :y 240000}])]
      (is (= actual expected))))
  (testing "Degrading locations"
    (let [expected [:galloway]
          actual (degrade-location
                   {:home [{0 0} :test-home :galloway]}
                   [{-4 55} :auchencairn :galloway])]
      (is (= actual expected)))
    (let [expected nil
          actual (degrade-location
                   {:home [{0 0} :test-home :galloway]}
                   [:froboz])]
      (is (= actual expected)))))

(deftest inference-tests
  (testing "Ability to infer new knowledge from news items: single rule tests"
    (let [expected {:verb :marry, :actor :belinda, :other :adam}
          actual (infer {:verb :marry :actor :adam :other :belinda}
                        {:verb :marry :actor :other :other :actor})]
      (is (= actual expected)))
    (let [expected {:verb :attack, :actor :adam, :other :belinda}
          actual (infer {:verb :rape :actor :adam :other :belinda}
                        {:verb :attack})]
      (is (= actual expected)))
    (let [expected {:verb :sex, :actor :belinda, :other :adam}
          actual (infer {:verb :rape :actor :adam :other :belinda}
       {:verb :sex :actor :other :other :actor})]
      (is (= actual expected))))
  (testing "Ability to infer new knowledge from news items: all applicable rules"
    (let [expected #{{:verb :sex, :actor :belinda, :other :adam, :location nil, :nth-hand 1}
                     {:verb :sex, :actor :adam, :other :belinda, :location nil, :nth-hand 1}
                     {:verb :attack, :actor :adam, :other :belinda, :location nil, :nth-hand 1}}
          ;; dates will not be and cannot be expected to be equal
          actual (make-all-inferences
                   {:verb :rape :actor :adam :other :belinda :location :test-home})
          actual' (map #(dissoc % :date) actual)]
      (is (= actual' expected)))))

;; (deftest learn-tests
;;   (testing "Learning from an interesting news item."
;;     (let [expected {:home [{0 0} :test-home],
;;                     :knowledge ({:verb :rape, :actor :adam, :other :belinda, :location nil, :nth-hand 1}
;;                                        {:verb :sex, :actor :belinda, :other :adam, :location nil, :nth-hand 1}
;;                                        {:verb :attack, :actor :adam, :other :belinda, :location nil, :nth-hand 1}
;;                                        {:verb :sex, :actor :adam, :other :belinda, :location nil, :nth-hand 1})}
;;           actual (learn-news-item
;;                    {:home [{0, 0} :test-home]
;;                     :knowledge []}
;;                    {:verb :rape :actor :adam :other :belinda :location [:test-home]})
;;           actual' (assoc actual :knowledge (map #(dissoc % :date) (:knowledge actual)))]
;;       (is (= actual' expected)))))

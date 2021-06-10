(ns cc.journeyman.the-great-game.gossip.news-items-test
  (:require [clojure.test :refer [deftest is testing]]
            [cc.journeyman.the-great-game.gossip.news-items :refer
             [all-known-verbs compatible-item? degrade-location infer
              interest-in-character interesting-character? interest-in-location
              interesting-location? learn-news-item make-all-inferences]]))

(deftest interesting-character-tests
  (testing "To what degree characters are of interest to the gossip"
    (let [expected 1
          gossip  {:home [{0, 0} :test-home]
                   :interesting-verbs all-known-verbs
                   ;; already knows about adam
                   :knowledge [{:verb :sell :actor :adam :other :charles :object :wheat :quantity 10 :price 5 :location [:test-home]}]}
          actual (interest-in-character
                  gossip
                  :adam)]
      (is (= actual expected)))
    (let [expected 0
          gossip  {:home [{0, 0} :test-home]
                   :interesting-verbs all-known-verbs
                   ;; already knows about adam
                   :knowledge [{:verb :sell :actor :adam :other :charles :object :wheat :quantity 10 :price 5 :location [:test-home]}]}
          actual (interest-in-character
                  gossip
                  :dorothy)]
      (is (= actual expected))))
  (testing "Whether characters are of interest to the gossip"
     (let [expected true
           gossip  {:home [{0, 0} :test-home]
                    :interesting-verbs all-known-verbs
                   ;; already knows about adam
                    :knowledge [{:verb :sell :actor :adam :other :charles :object :wheat :quantity 10 :price 5 :location [:test-home]}]}
           actual (interesting-character? 
                   gossip
                   :adam)]
       (is (= actual expected)))
    (let [expected false
          gossip  {:home [{0, 0} :test-home]
                   :interesting-verbs all-known-verbs
                   ;; already knows about adam
                   :knowledge [{:verb :sell :actor :adam :other :charles :object :wheat :quantity 10 :price 5 :location [:test-home]}]}
          actual (interesting-character?
                  gossip
                  :dorothy)]
      (is (= actual expected)))))

(deftest compatible-item-test
  (testing "Compatible item: items are identical"
    (let [expected true
          new-item {:verb :kills :location :tchahua :actor :fierce-fred :other :dainty-daisy}
          known-item {:verb :kills :location :tchahua :actor :fierce-fred :other :dainty-daisy}
          actual (compatible-item? new-item known-item)]
      (is (= actual expected) "Items which are identical are compatible.")))
  (testing "Compatible item: new item is less specific"
    (let [expected true
          new-item {:verb :kills :location :tchahua :other :dainty-daisy}
          known-item {:verb :kills :location :tchahua :actor :fierce-fred :other :dainty-daisy}
          actual (compatible-item? new-item known-item)]
      (is (= actual expected)
          "An item which is less specific is compatible with existing knowledge.")))
  (testing "Compatible item: new item is more specific"
    (let [expected true
          new-item {:verb :kills :location :tchahua :actor :fierce-fred :other :dainty-daisy :date 20210609}
          known-item {:verb :kills :location :tchahua :actor :fierce-fred :other :dainty-daisy}
          actual (compatible-item? new-item known-item)]
      (is (= actual expected) "A new item which is more specific adds knowledge and is not compatible")))
  (testing "Compatible item: new item conflicts with existing knowledge."
    (let [expected false
          new-item {:verb :kills :location :tchahua :actor :jealous-joe :other :dainty-daisy}
          known-item {:verb :kills :location :tchahua :actor :fierce-fred :other :dainty-daisy}
          actual (compatible-item? new-item known-item)]
      (is (= actual expected) "A new item which we don't yet intelligently handle but is not compatible"))))

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
    (let [expected 2
          actual (interest-in-location
                  {:home [{:x 35 :y 23} :auchencairn :galloway :scotland]}
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
    (let [expected {:verb :marry, :actor :belinda, :other :adam, :nth-hand 1}
          item {:verb :marry :actor :adam :other :belinda}
          rule {:verb :marry :actor :other :other :actor}
          actual (infer item rule)]
      (is (= actual expected)))
    (let [expected {:verb :attack, :actor :adam, :other :belinda, :nth-hand 1}
          item {:verb :rape :actor :adam :other :belinda}
          rule {:verb :attack}
          actual (infer item rule)]
      (is (= actual expected)))
    (let [expected {:verb :sex, :actor :belinda, :other :adam, :nth-hand 1}
          item {:verb :rape :actor :adam :other :belinda}
          rule {:verb :sex :actor :other :other :actor}
          actual (infer item rule)]
      (is (= actual expected))))
  (testing "Ability to infer new knowledge from news items: all applicable rules"
    (let [expected #{{:verb :sex, :actor :belinda, :other :adam, :location :test-home, :nth-hand 1}
                     {:verb :sex, :actor :adam, :other :belinda, :location :test-home, :nth-hand 1}
                     {:verb :attack, :actor :adam, :other :belinda, :location :test-home, :nth-hand 1}}
          ;; dates will not be and cannot be expected to be equal
          actual (set (make-all-inferences
                       {:verb :rape :actor :adam :other :belinda :location :test-home}))]
      (is (= actual expected)))))

(deftest learn-tests
  (testing "Learning from an interesting news item."
    (let [expected {:home [{0 0} :test-home]
                    :interesting-verbs all-known-verbs
                    :knowledge [{:verb :sell :actor :adam :other :charles :object :wheat :quantity 10 :price 5 :location [:test-home]}
                                {:verb :sex, :actor :adam, :other :belinda, :location [:test-home], :nth-hand 1}
                                {:verb :sex, :actor :belinda, :other :adam, :location [:test-home], :nth-hand 1}]}
          gossip  {:home [{0, 0} :test-home]
                   :interesting-verbs all-known-verbs
                   ;; already knows about adam
                   :knowledge [{:verb :sell :actor :adam :other :charles :object :wheat :quantity 10 :price 5 :location [:test-home]}]}
          actual (learn-news-item
                  gossip
                  {:verb :sex :actor :adam :other :belinda :location [:test-home]})]
      (is (= actual expected)))))


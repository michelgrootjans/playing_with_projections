(ns playing-with-projections.core-test
  (:require [clojure.test :refer :all]
            [playing-with-projections.core :refer :all]))

(deftest playing-with-projections-test
  (testing "Should load events from JSON file."
    (is (= (count (parse-events-file "../data/0.json" )) 4))))

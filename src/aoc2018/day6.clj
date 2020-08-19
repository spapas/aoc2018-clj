(ns aoc2018.day6
  (:require [ aoc2018.common :as co]
            [ clojure.string :as str]
             [ clojure.set :as set])
  )

(defn get-points [l] 
  (into [] (map #(-> % str/trim co/parse-int) (str/split l #","))))

(def day6-input (->> "input/day6" co/get-lines (map get-points)))

(def answer1 42)

(def answer2 43)

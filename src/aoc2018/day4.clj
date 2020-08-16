(ns aoc2018.day4
  (:require [ aoc2018.common :as co]
             [ clojure.set :as set])
  )

(def day4-input (co/get-lines "input/day4"))

(def answer1 42)
(def answer2 43)

(defn pr-input []
  (doseq [z (sort day4-input)] (println z))
   )

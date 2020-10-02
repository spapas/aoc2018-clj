;(require '[aoc2018.day10 :as day10 :refer :all] :reload-all)


(ns aoc2018.day10
  (:require [aoc2018.common :as co]
            [clojure.string :as str]
            [clojure.set :as set]))

(def input (co/get-lines "input/day10-test"))

(def parse-pattern
  #"position=<([0-9\ \-]+),([0-9\ \-]+)> velocity=<([0-9\ \-]+),([0-9\ \-]+)>")

(def parsed-input
  (map #(let [r (re-find parse-pattern %)
              x (co/parse-int (str/trim (nth r 1)))
              y (co/parse-int (str/trim (nth r 2)))
              dx (co/parse-int (str/trim (nth r 3)))
              dy (co/parse-int (str/trim (nth r 4)))]
          {:pos [x y]
           :vel [dx dy]}) input))

(def answer1
  42)
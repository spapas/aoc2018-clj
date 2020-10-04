;(require '[aoc2018.day11 :as day11 :refer :all] :reload-all)


(ns aoc2018.day11
  (:require [aoc2018.common :as co]
            [clojure.string :as str]
            [clojure.set :as set]))

(def input 5719)

(def grid-sn input)

(defn get-hundreds [num]
  (quot (mod num 1000) 100))

(defn rack-id [c]
  (+ 10 (first c)))

(defn power-level [c]
  (let [y (second c)
        rid (rack-id c)
        s1 (* rid y)
        s2 (+ s1 grid-sn)
        s3 (* s2 rid)
        s4 (get-hundreds s3)]
    (- s4 5)))

(def power-levels
  (into {} (for [x (range 300) y (range 300)] [[x y], (power-level [x y])])))

(defn sum-power-levels [x y n]
  (reduce + (for [i (range x (+ n x)) j (range y (+ n y))]
              (power-level [i j]))))

(defn power-levels-squares [n]
  (for [x (range (inc (- 300 n))) y (range (inc (- 300 n)))]
    [[x y] (sum-power-levels x y n)]))

(defn max-power-level-square [n]
  (apply max-key second (power-levels-squares n)))

(def answer1 (max-power-level-square 3))

(def answer2
  (apply max-key (comp second second)
        (for [i (range 1 21)]
           [i (max-power-level-square i)])))
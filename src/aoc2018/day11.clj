;(require '[aoc2018.day11 :as day11 :refer :all] :reload-all)


(ns aoc2018.day11
  (:require [aoc2018.common :as co]
            [clojure.string :as str]
            [clojure.set :as set]))

(def input 5719)

(def grid-sn 18)

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
        s4 (get-hundreds s3)
        ]
     (- s4 5)))
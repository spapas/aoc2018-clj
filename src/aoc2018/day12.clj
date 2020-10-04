;(require '[aoc2018.day12 :as day12 :refer :all] :reload-all)


(ns aoc2018.day12
  (:require [aoc2018.common :as co]
            [clojure.string :as str]
            [clojure.set :as set]))

(defn get-digits [s]
  (map #(if (= %1 \.) 0 1) s))

(def initial-str "###....#..#..#......####.#..##..#..###......##.##..#...#.##.###.##.###.....#.###..#.#.##.#..#.#")
(def initial (get-digits initial-str))
(def rules-str "..... => .
..#.. => .
.###. => .
...## => #
#.### => .
#.#.# => #
##..# => .
##.## => #
#...# => .
..##. => .
##.#. => .
...#. => .
#..#. => #
.#### => #
.#..# => #
##... => #
.##.# => .
....# => .
#.... => .
.#.#. => #
.##.. => .
###.# => #
####. => .
##### => #
#.##. => #
.#... => #
.#.## => #
###.. => #
#..## => .
#.#.. => #
..#.# => .")

(defn s-to-n [s]
  (reduce + 
  (map-indexed (fn [idx itm] (* itm (co/ipow 2 idx))) (reverse (get-digits s)))
  ))

(def rules
  (as-> rules-str zz
    (str/split zz #"\n")
    (map #(str/split %1 #" => ") zz)
    (map #(vec [(s-to-n (first %)) (if (= "." (second %)) 0 1)]) zz)))
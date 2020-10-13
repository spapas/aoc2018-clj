;(require '[aoc2018.day12 :as day12 :refer :all] :reload-all)


(ns aoc2018.day12
  (:require [aoc2018.common :as co]
            [clojure.string :as str]
            [clojure.set :as set]))

(defn get-digits [s]
  (map #(if (= %1 \.) 0 1) s))

(def initial-str "###....#..#..#......####.#..##..#..###......##.##..#...#.##.###.##.###.....#.###..#.#.##.#..#.#")
(def initial-str1 "#..#.#..##......###...###")

(def initial (map-indexed #(vec [%1 %2]) (get-digits initial-str)))
(def rules-str "..### => #
..... => .
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

(def rules-str0 "...## => #
..#.. => #
.#... => #
.#.#. => #
.#.## => #
.##.. => #
.#### => #
#.#.# => #
#.### => #
##.#. => #
##.## => #
###.. => #
###.# => #
####. => #")

(def pows {0 1 1 2 2 4 3 8 4 16})
(defn pow2 [z] (get pows z))

(defn s-to-n [s]
  (reduce +
          (map-indexed (fn [idx itm] (* itm (pow2 idx))) (reverse (get-digits s)))))

(def rules
  (as-> rules-str zz

    (str/split zz #"\n")
    (filter #(str/ends-with? % "#") zz)
    (map #(str/split %1 #" => ") zz)
    (map #(vec [(s-to-n (first %)) (if (= "." (second %)) 0 1)]) zz)))

(def rules-dict (into {} rules))

(defn lpad [st]
  (let [fidx (first (first st))
        f (second (first st))
        s (second (second st))]
    (cond
      (= f 1) (into [[(- fidx 2) 0] [(dec fidx) 0]] st)
      (= s 1) (into [[(dec fidx) 0]] st)
      :else st)))

(defn rpad [st]
  (let [rst (reverse st)
        fidx (first (first rst))
        f (second (first rst))
        s (second (second rst))]
    (cond
      (= f 1) (conj (conj st [(inc fidx) 0]) [(+ fidx 2) 0])
      (= s 1) (conj st [(inc fidx) 0])
      :else st)))

(defn lpad-always [st]
  (let [fidx (first (first st))]
    (into [[(- fidx 4) 0] [(- fidx 3) 0] [(- fidx 2) 0] [(dec fidx) 0]] st)))

(defn rpad-always [st]
  (let [lidx (first (last st))]
    (conj
     (conj
      (conj
       (conj st [(inc lidx) 0])
       [(+ lidx 2) 0])
      [(+ lidx 3) 0])
     [(+ lidx 4) 0])))


(defn l-to-n [l]
  (reduce +
          (map-indexed
           (fn [idx itm] (* (second itm) (pow2 idx)))
           (reverse l))))

(defn d [s z]
  (print s)
  (println z)
  z)

(defn next-state [st]
  (let [pst (rpad-always (lpad-always st))
        idx (map first pst)]
    (->> (partition 5 1 pst)
         (d "1: ")
         
         (map l-to-n)
         (d "1: ")
         (map #(get rules-dict % 0))
         (map vector idx)
         (into [])
         (rpad)
         (lpad)
         )))


(defn looper [st t]
  (if (zero? t)
    st
    (recur (next-state st) (dec t))))


; 2490 too high
; 1588 not correct
; 560 not correct
(def part1
  (->> (looper initial 20)
       (filter #(-> % second zero? not))
       (map first)
       (reduce +)
       (println)))


(comment
  rules
  initial
  (->> initial next-state)
  (looper initial 20))


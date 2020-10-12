;(require '[aoc2018.day12 :as day12 :refer :all] :reload-all)


(ns aoc2018.day12
  (:require [aoc2018.common :as co]
            [clojure.string :as str]
            [clojure.set :as set]))

(defn get-digits [s]
  (map #(if (= %1 \.) 0 1) s))

(def initial-str "###....#..#..#......####.#..##..#..###......##.##..#...#.##.###.##.###.....#.###..#.#.##.#..#.#")
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

(def pows {0 1 1 2 2 4 3 8 4 16})
(defn pow2 [z] (get pows z))

(defn s-to-n [s]
  (reduce + 
   (map-indexed (fn [idx itm] (* itm (pow2 idx))) (reverse (get-digits s)))))
  

(def rules
  (as-> rules-str zz
    (str/split zz #"\n")
    (map #(str/split %1 #" => ") zz)
   (map #(vec [(s-to-n (first %)) (if (= "." (second %)) 0 1)]) zz)))

(def rules-dict (into {} rules))

(defn lpad [st]
  (let [fidx (first (first st))
        f (second (first st)) 
        s (second (second st))]
    (println fidx)
    (cond 
      (= f 1) (into [[(- fidx 2) 0] [(dec fidx) 0]] st)
      (= s 1) (into [[(dec fidx) 0]] st)
      :else st)))


(defn rpad [st]
  (let [rst (reverse st)
        fidx (first (first rst))
        f (second (first rst))
        s (second (second rst))]
    (println fidx)

    (cond
      (= f 1) (conj (conj st [(inc fidx) 0]) [(+ fidx 2) 0])
      (= s 1) (conj st [(inc fidx) 0]) 
      :else st)))

(defn lpad-always [st]
  (let [fidx (first (first st))]
    (into [[(- fidx 2) 0] [(dec fidx) 0]] st)))

(defn rpad-always [st]
  (let [rst (reverse st)
        fidx (first (first rst))]
    (conj (conj st [(inc fidx) 0]) [(+ fidx 2) 0])))


(defn l-to-n [l]
  (reduce +
          (map-indexed
           (fn [idx itm] (* (second itm) (pow2 idx)))
           (reverse l))))

(defn next-state [st]
  (let [
        pst (rpad-always (lpad-always st))
        idx (map first st)]
    (->> (partition 5 1 pst)
       (map l-to-n)
       (map #(get rules-dict %))
       (map vector idx)
         (lpad)
         (rpad))))

(defn looper [st t]
  (if (zero? t) 
    st
    (recur (next-state st) (dec t))
      ))

(comment
  rules
  initial
  (get (into {} rules) 6)
  (next-state (next-state (next-state initial)))
  (pow2 2)
  (looper initial 5)
  (l-to-n '([28 1] [29 0] [30 0] [31 1] [32 0]))
 (rpad [[0 1] [1 1] [2 1]]))
  
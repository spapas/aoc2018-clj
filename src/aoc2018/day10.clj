;(require '[aoc2018.day10 :as day10 :refer :all] :reload-all)


(ns aoc2018.day10
  (:require [aoc2018.common :as co]
            [clojure.string :as str]
            [clojure.set :as set]))

(def input (co/get-lines "input/day10"))
;(def input (co/get-lines "input/day10-test"))

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

(defn display [pts]
  (let [positions (map #(:pos %1) pts)
        pset (into #{} positions)
        xmin (first (apply min-key first positions))
        xmax (first (apply max-key first positions))
        ymin (second (apply min-key second positions))
        ymax (second (apply max-key second positions))]
    (println xmin xmax ymin ymax)
    (for [y (range (dec ymin) (inc ymax)) x (range (dec xmin) (inc xmax))]
      (let [s (if (contains? pset [x y]) "#" " ")
            e (if (= x xmax) "\n" "")]

        (print (str s e))))))

(defn step [pt]
  (let [x (-> pt :pos first)
        y (-> pt :pos second)
        dx (-> pt :vel first)
        dy (-> pt :vel second)]
    {:pos [(+ x dx) (+ y dy)] :vel [dx dy]}))

(defn step-all [pts]
  (map step pts))

(defn dist-from-center [pt]
  (let [x (-> pt :pos first)
        y (-> pt :pos second)]
    (+ (Math/abs x) (Math/abs y))))

(defn tot-dist-from-center [pts]
  (reduce + (map dist-from-center pts)))

(defn area [pts]
  (let [positions (map #(:pos %1) pts)
        xmin (first (apply min-key first positions))
        xmax (first (apply max-key first positions))
        ymin (second (apply min-key second positions))
        ymax (second (apply max-key second positions))]
    (* (- xmax xmin) (- ymax ymin))))


(defn find-closest-dist [pts prev-dist cnt ]
  (let [next-pts (step-all pts)
        next-dist (tot-dist-from-center next-pts)]
    (println prev-dist next-dist cnt)
    (if (> prev-dist next-dist) 
      (recur next-pts next-dist (inc cnt))
      pts
      )))

(defn find-smallest-area [pts prev-area cnt]
  (let [next-pts (step-all pts)
        next-area (area next-pts)]
    (println prev-area next-area cnt)
    (if (> prev-area next-area)
      (recur next-pts next-area (inc cnt))
      pts)))

(def aligned-stars (find-smallest-area parsed-input (area parsed-input) 0))

(def answer1
  (display aligned-stars))
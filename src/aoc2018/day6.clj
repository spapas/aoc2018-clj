(ns aoc2018.day6
  (:require [ aoc2018.common :as co]
            [ clojure.string :as str]
             [ clojure.set :as set])
  )

(defn get-points [l] 
  (into [] (map #(-> % str/trim co/parse-int) (str/split l #","))))

(def day6-input (->> "input/day6" co/get-lines (map get-points)))

(def points day6-input)

(def xmax (first (apply max-key first day6-input)))
(def ymax (second (apply max-key second day6-input)))
(def xmin (first (apply min-key first day6-input)))
(def ymin (second (apply min-key second day6-input)))


(def grid (for [x (range xmin (inc xmax)) y (range ymin (inc ymax))] [x y]))

(defn find-closest [p]
  (apply min-key #(+ 
                    (co/abs (- (first p) (first %)))
                    (co/abs (- (second p) (second %))))
         points))

(def answer1 42)

(def answer2 43)

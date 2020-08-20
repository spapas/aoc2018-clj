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

(defn manh [p1 p2]
  (let [x1 (first p1)
        x2 (first p2)
        y1 (second p1)
        y2 (second p2)]
    (+ (co/abs (- x1 x2)) (co/abs (- y1 y2)))))

(defn find-closest [p pts]
  (apply min-key #(manh % p) pts))

(defn mapper [p]
  (let [closest (find-closest p points)
        sec-closest (find-closest p (remove #(= % closest) points))
        eq-dist (= (manh p closest) (manh p sec-closest))]
    [p (if eq-dist nil closest)]))

(def closest-grid (map mapper grid))

(defn closest-count-reducer [acc point-with-closest]
  (let [closest ( second point-with-closest )]
    (update acc closest #(if (nil? %) 1 (inc %)))))

(def point-counter 
  (remove #(nil? (first (vec %))) (reduce closest-count-reducer {} closest-grid)))

(defn is-outer [p]
  (let [x (first p)
        y (second p)]
    (or (= x xmin) (= x xmax) (= y ymin) (= y ymax))))

(def outer-points 
  (set (remove nil? (map second (filter #(-> % first is-outer) closest-grid)))))

(def point-counter-no-outer (remove #(co/in? outer-points (first %)) point-counter))

(def answer1 (second (apply max-key second point-counter-no-outer)))

(defn is-safe [p]
  (if (< (reduce + (map #(manh % p) points)) 10000) true false))

(def safe-grid (map is-safe grid))

(def answer2 (reduce + (map #(if % 1 0) safe-grid)))

;(require '[aoc2018.day9 :as day9 :refer :all] :reload-all)


(ns aoc2018.day9
  (:require [aoc2018.common :as co]
            [clojure.string :as str]
            [clojure.zip :as z]
            [clojure.set :as set]))

(defstruct game :marbles :curr)


(defn do-put [l idx v acc]
  (if (zero? idx) (concat (reverse acc) (cons v l))
      (let [f (first l)
            r (rest l)]
        (recur r (dec idx) v (cons f acc)))))

(defn put [l idx v]
  (do-put l idx v '()))

(defn get-idx [game places dir]
  (let [marbles (:marbles game)
        curr (:curr game)
        mc (count marbles)]
    (if (= dir :cw)
      (let [c+p (+ curr places)]
        (if (>= c+p mc)
          (mod c+p mc)
          c+p))
      (let [c-p (- curr places)]
        (if (>= c-p 0)
          c-p
          (mod c-p mc))))))

(defn get-val [l idx]
  (if (zero? idx)
    (first l)
    (recur (rest l) (dec idx))))

(defn do-del-idx [l idx acc]
  (if (zero? idx)
    (concat (reverse acc) (rest l))
    (recur (rest l) (dec idx) (cons (first l) acc))))

(defn del-idx [l idx]
  (do-del-idx l idx '()))

(defn place-marble [g marble]
  (if (= 0 (mod marble 23))
    (let [c1 (get-idx g 7 :ccw)
          marbles (:marbles g)]
      [(+ marble (get-val marbles c1))
       (struct game (del-idx marbles c1) c1)])
    [0
     (let [c1 (get-idx g 1 :cw)
           c2 (get-idx g 2 :cw)
           marbles (:marbles g)
           curr (:curr g)]
       (println c1)
       (println c2)
       (cond
         (= c1 c2) (struct game [0 1] 1)
         (< c1 c2) (struct game
                           (put marbles (inc c1) marble)
                           c2)

         :else (struct game (reverse (cons marble (reverse marbles))))))]))

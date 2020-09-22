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
  ;(do-put l idx v '()))
  ;(vec (concat (take idx l) [v] (drop idx l))))
(apply conj (subvec l 0 idx) v (subvec l idx (count l)) ))

  
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
  (get l idx))
  ;(if (zero? idx)
  ;  (first l)
  ;  (recur (rest l) (dec idx))))

(defn do-del-idx [l idx acc]
  (if (zero? idx)
    (concat (reverse acc) (rest l))
    (recur (rest l) (dec idx) (cons (first l) acc))))

(defn del-idx [l idx]
  (apply conj (subvec l 0 idx) (subvec l (inc idx) (count l))))
  ;(vec (concat (take idx l) (drop (inc idx) l))))
  ;(do-del-idx l idx '()))

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

       (cond
         (= c1 c2) (struct game [0 1] 1)
         (< c1 c2) (struct game
                           (put marbles (inc c1) marble)
                           c2)

         :else (struct game (conj marbles marble) (count marbles))))]))

(def players 479)
(def limit (inc 71035))

(defn play-game [g turn scores]
  (if (zero? (mod turn 1000)) (do (println turn) true)  )
  (if (= turn limit)
    scores
    (let [[c-score n-game] (place-marble g turn)
          player (mod turn players)]
      (recur n-game (inc turn) (update scores player #(if (nil? %) c-score (+ c-score %)))))))


(def answer1 (get (apply max-key #(get % 1)
                         (play-game (struct game [0] 0) 1 {})) 1))
(println "~~~")
(println answer1)
(println "~~~")
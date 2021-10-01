(ns aoc2018.day13
  (:require [aoc2018.common :as co]
            [clojure.string :as str]
            [clojure.set :as set]))

;(require '[aoc2018.day13 :as day13 :refer :all] :reload-all)

(def input (co/get-lines "input/day13"))


(def map-input (->> input
                    (map-indexed (fn [yidx line]
                                   (map-indexed (fn [xidx c] [[xidx yidx] c]) line)))
                    (apply concat)
                    (into {})))

(def map-input-remove-carts 
  (->> map-input
       (map (fn [x]
              (let [pos (first x)
                    val (second x)]
                [pos (cond
                       (= val \^) \|
                       (= val \v) \|
                       (= val \>) \-
                       (= val \<) \-
                       :else val)])))
       (into {})
       ))
                                 
                                
(defrecord Cart [pos direction turns])


(Cart. 1 2 3)

(defn is-cart? [c]
  (or
   (= c \<)
   (= c \>)
   (= c \v)
   (= c \^)))

(def initial-carts
  (->> map-input
       (filter #(is-cart? (second %)))
       (map #(Cart. (first %) (second %) 0))))

(defn carts-collitions [carts]
  (->> carts (map :pos) frequencies (filter #(> (second %) 1))))
(defn carts-collide? [carts] (> 0 (count (carts-collitions carts))))

(defn get-next-pos [pos dir]
  (let [x (first pos)
        y (second pos)]
    (case dir
      \^ [x (- y 1 )]
      \> [(+ x 1 ) y]
      \< [(- x 1 ) y]
      \v [x (+ y 1)])))

(comment 
  (get-next-pos [1 1] \>)
  (get-next-pos [1 1] \<)
  (get-next-pos [1 1] \^)
  (get-next-pos [1 1] \v)
  )

(defn turn-left [dir]
  (case dir
    \^ \<
    \> \^
    \< \v
    \v \>))

(defn turn-right [dir]
  (case dir
    \^ \>
    \> \v
    \< \^
    \v \<))

(defn handle-intersection [dir turns]
  (case (mod turns 3)
    0 (turn-left dir)
    1 dir
    2 (turn-right dir)))

(defn next-cart [[x y] next-pos next-val dir turns]
  (case [next-val dir]
    [\- \>] (Cart. next-pos dir turns)
    [\- \<] (Cart. next-pos dir turns)
    [\| \^] (Cart. next-pos dir turns)
    [\| \v] (Cart. next-pos dir turns)

    [\\ \>] (Cart. next-pos \v turns)
    [\\ \<] (Cart. next-pos \^ turns)
    [\\ \^] (Cart. next-pos \< turns)
    [\\ \v] (Cart. next-pos \> turns)
    [\/ \>] (Cart. next-pos \^ turns)
    [\/ \<] (Cart. next-pos \v turns)
    [\/ \^] (Cart. next-pos \> turns)
    [\/ \v] (Cart. next-pos \< turns)
    [\+ \>] (Cart. next-pos (handle-intersection \> turns) (inc turns))
    [\+ \<] (Cart. next-pos (handle-intersection \< turns) (inc turns))
    [\+ \^] (Cart. next-pos (handle-intersection \^ turns) (inc turns))
    [\+ \v] (Cart. next-pos (handle-intersection \v turns) (inc turns))))



(defn tick-cart [cart]
  (let [pos (:pos cart)
        dir (:direction cart)
        turns (:turns cart)
        next-pos (get-next-pos pos dir)
        next-val (get map-input-remove-carts next-pos)
        x (first pos)
        y (second pos)]
    (next-cart pos next-pos next-val dir turns)))

(defn tick [carts] (map tick-cart carts))

(defn looper [carts]
  (if (carts-collide? carts)
    (carts-collitions carts)
    (recur (tick (sort-by #(-> % :pos second) carts)))))

(looper initial-carts)

initial-carts

(-> initial-carts tick tick tick tick tick tick tick )

(comment 
(get map-input [0 13]))
;; (get map-input [0,])
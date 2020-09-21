(ns aoc2018.day9
  (:require [aoc2018.common :as co]
            [clojure.string :as str]
            [clojure.zip :as z]
            [clojure.set :as set]))

(defstruct game :marbles :curr)

(defn place-marble [game marble]
  game)


(defn do-put [l idx v acc]
  (if (zero? idx) (concat acc (cons v l))
      (let [f (first l)
            r (rest l)]
        (recur r (dec idx) v (cons f acc)))))


(defn put [l idx v]
  (do-put l idx v '())
  )

(defn get-idx [game places dir]
  (let [marbles (:marbles game)
        curr (:curr game)
        mc (count marbles)
        ]
  (if (= dir :cw)
    (if ( > (+ curr places) mc) ())
    ())))
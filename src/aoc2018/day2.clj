(ns aoc2018.day2
  (:require [aoc2018.common :as co])
  )

(def day2-input (co/get-lines "day2"))
(def letter-frequencies (map (comp vals frequencies) day2-input))

; Create a fuction that checks if collection contains the value and
; increases the accumulator if yes
(defn get-reducer [val] 
  (fn [acc current]
    (if (some #(= % val) current) (inc acc) acc))
  )

(def answer1 (*
              (reduce (get-reducer 2) 0 letter-frequencies)
              (reduce (get-reducer 3) 0 letter-frequencies)
              ))

; Get number of differences in seq positions 
(defn diff-number [s1 s2 cnt]
  (if (empty? s1) 
    cnt
    (recur (rest s1) (rest s2) (if
                                 (= (first s1) (first s2))
                                 cnt
                                 (inc cnt)
                                 ))
    )
  )

(defn remove-different [s1 s2 res]
  (if (empty? s1)
    res
    (recur (rest s1) (rest s2) (if
                                 (= (first s1) (first s2))
                                    (conj res (first s1))
                                    res
                                    )))
  )

(def answer2 
  (apply str 
         (apply remove-different (conj 
                                   (nth 
                                     (for [x day2-input y day2-input 
                                           :when (= 1 (diff-number x y 0))]
                                       [x y]) 
                                     0
                                     ) []) )))

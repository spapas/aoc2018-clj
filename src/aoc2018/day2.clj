(ns aoc2018.day2
  (:require [aoc2018.common :as co])
  )

(def day2-input (co/get-lines "input/day2"))
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

; Get number of differences in collection positions 
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

; Remove different members of the collections
; Careful - this works properly with vectors ; lists will be reversed
; i.e use it like (remove-different s1 s2 [])
(defn remove-different [s1 s2 res]
  (if (empty? s1)
    res
    (recur (rest s1) (rest s2) (if
                                 (= (first s1) (first s2))
                                    (conj res (first s1))
                                    res
                                    )))
  )

; This is a little confusing ... 
; Starting from inside:
; The list comprehension will return an array of the elements that have a difference
; of 1 in their members. Using nth we get the 1st element of that array - it will\
; contain two collections.
; We then append [] to that collection with the coj so that the parameters that
; will be passed to remove-different using apply would be coll1 coll2 []
; Finally, the apply str trick will convert the character vector that remove-different
; would produce to a string (which is th actual result of answer2 of day2)


(def answer2 
  (apply str 
         (apply remove-different (conj 
                                   (nth 
                                     (for [x day2-input y day2-input 
                                           :when (= 1 (diff-number x y 0))]
                                       [x y]) 
                                     0
                                     ) []))))

; Notice that instead of the apply/conj trickery we could just have called remove-different
; like this 
;(let [result (nth (for etc) 0)] (remove-different (first result) (second result) [])
(def answer2a
  (apply str 
         (let [result  (nth 
                         (for [x day2-input y day2-input 
                               :when (= 1 (diff-number x y 0))]
                           [x y]) 
                         0
                         )]
           (remove-different (first result) (second result) [])
           )
         ))
; or even better just bind the result to a variable!

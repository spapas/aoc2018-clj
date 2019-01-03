(ns aoc2018.day3
  (:require [ aoc2018.common :as co]
             [ clojure.set :as set])
  )

(def day3-input (co/get-lines "input/day3"))

(defn parser[line]
  (apply assoc {}
         (interleave [:id :left :top :width :height]
                     (map co/parse-int 
                          (rest 
                            (re-find #"^#(\d+)\ @\ (\d+),(\d+):\ (\d+)x(\d+)"  line
                                     ))))))

; Same as the 1st parser using the thread-last macro
; Easier to explain. For each line:
; * Find the numbers using the reg-ex spec
; * Ignore the 1st item of the re-find response (it's the matched string)
; * Convert the number to integers
; * Interlave the numbers with dictionary keys (so that we'll have a list of "id" 1 "left" 3223 etc )
; * Pass that list to assoc using apply so that we'll get (assoc {} "id 1 etc)
(defn parser2[line]
  (->> line
       (re-find #"^#(\d+)\ @\ (\d+),(\d+):\ (\d+)x(\d+)")
       rest
       (map co/parse-int )
       (interleave [:id :left :top :width :height])
       (apply assoc {})
       )
  )


;(prn (parser (first day3-input)))
;(prn (parser2 (first day3-input)))


(def day3-input-parsed (map parser day3-input))

(first day3-input-parsed)

; A reducer for individual elements: 
(defn claim-reducer-el [matrix xyel ]
  (let [x (nth xyel 0) y (nth xyel 1) id (nth xyel 2) oldset (get-in matrix [x y])]
        (assoc-in matrix [x y] (conj oldset id))
        )
  )

; A reducer for all the elements: Just call the individual element reducer
; with the correct parameters. So for each element the individual reduce
; will be called with a vector of its x and y ranges (and id). 
(defn claim-reducer [matrix el]
  (let [left (:left el) top (:top el) width (:width el) height (:height el)
        xrange (range  left (+ left width)) yrange (range  top (+ top height))]
  (reduce claim-reducer-el matrix (for [x xrange y yrange] [x y (:id el)])))
  )

; Create an initial matrix (1000 vec of 1000 set each)
(def initial-matrix (vec (replicate 1000 (vec (replicate 1000 #{})))))
; Add the ids of all claims to the sets using the total reducer
(def claims (reduce claim-reducer initial-matrix day3-input-parsed ))
; To find the answer just count which sets have more than 1 element
(def answer1 (reduce + (for [x (range 0 1000) y (range 0 1000) 
            :when (< 1 ( count (get-in claims [x y])))]
            1
            )))

; Same logic as before, just put the claims that have more than 1 element in a set
(def overlapping-claims (reduce into  #{}  (for [x (range 0 1000) y (range 0 1000) 
            :when (< 1 ( count (get-in claims [x y])))]
            (vec  (get-in claims [x y]))
            ) ))

; And diff that value with the set of ids
(def answer2 (set/difference  (set (map :id day3-input-parsed ) ) overlapping-claims))

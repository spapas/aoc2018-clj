(ns aoc2018.day3
  (:require [aoc2018.common :as co])
  )

(def day3-input (co/get-lines "input/day3"))

(defn parser[line]
  (apply assoc {}
         (interleave ["id" "left" "top" "width" "height"]
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
       (interleave ["id" "left" "top" "width" "height"])
       (apply assoc {})
       )
  )


;(prn (parser (first day3-input)))
;(prn (parser2 (first day3-input)))


(def day3-input-parsed (map parser day3-input))

(take 5 day3-input-parsed)



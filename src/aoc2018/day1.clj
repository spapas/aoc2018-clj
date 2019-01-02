(ns aoc2018.day1
  (:require [aoc2018.common :as co])
  )

(def day1-input (co/get-lines "input/day1"))
(def day1-input-parsed (map co/parse-int day1-input))
(def answer1  (apply + day1-input-parsed)) 

(defn answer2-finder [input frequency-accumulator existing-freqs]
  (let [
    current-element (first input)
    current-frequency-acc (+ current-element frequency-accumulator)]
    ;(prn current-element frequency-accumulator existing-freqs)
    ;(Thread/sleep 1000)
    ;(prn current-element frequency-accumulator)
    (if (contains? existing-freqs current-frequency-acc)
      current-frequency-acc
      (recur (rest input) current-frequency-acc (conj  existing-freqs current-frequency-acc))
      )
    )
  )

(def answer2  (answer2-finder (cycle day1-input-parsed) 0 #{}))

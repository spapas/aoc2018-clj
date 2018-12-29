(ns aoc2018.day2
  (:require [aoc2018.common :as co])
  )

(def day2-input (co/get-lines "day2"))
(def letter-frequencies (map (comp vals frequencies) day2-input))


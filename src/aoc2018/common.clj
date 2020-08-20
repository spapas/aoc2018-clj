(ns aoc2018.common
  (:require [clojure.string :as str])
  )

(defn get-lines [file]
  (str/split-lines (slurp file)))

(defn parse-int [s]
  (Integer/parseInt s))

(defn pr-input [inp]
  (doseq [z (sort inp)] (println z))
   )

(defn abs [n] (max n (- n)))

(defn in? 
  "true if coll contains elm"
  [coll elm]  
  (some #(= elm %) coll))

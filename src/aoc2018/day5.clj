(ns aoc2018.day5
  (:require [ aoc2018.common :as co]
            [ clojure.string :as str]
             [ clojure.set :as set])
  )

(def day5-input (-> "input/day5" slurp str/trim))

(defn react? [l r] (and 
                     (= (str/lower-case l) (str/lower-case r))
                     (not= l r)))

(defn reducer [stack c]
  (let [top (first stack)
        r-stack  (rest stack)]
    (cond
     (nil? top) (cons c stack)
     (react? top c) r-stack
     :else (cons c stack))))

(def reduced-str (reduce reducer '() day5-input))

(def answer1 (count (str/join reduced-str)))
(def answer2 43)

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

(defn get-reduced-str [s]
  (reduce reducer '() s))

(def reduced-str (get-reduced-str day5-input))

(def answer1 (count (str/join reduced-str)))

(def polymers (set (map str/lower-case day5-input)))

(def reduced-string-lengths (map
                      #(as-> % v
                        (str "(?i)" v)
                        (re-pattern v)
                        (str/replace day5-input v "")
                        (get-reduced-str v)
                        (str/join v)
                        (count v))
                       polymers))


(def answer2 (apply min reduced-string-lengths))

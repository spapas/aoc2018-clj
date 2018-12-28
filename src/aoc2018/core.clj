(ns aoc2018.core
  (:require [clojure.string :as str] )
  (:gen-class))

; (var-get (resolve (symbol "aoc2018.day1/answer1" )))
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (cond 
    (= 0 (count args)) (println "Please enter a dayX as argument")
    (not (str/starts-with? (first args) "day")) (println "Please enter dayX as argument")
    :else (do 
            (println "Ok let's do " (first args))
            (let [
                  ans1 (str "aoc2018." (first args) "/answer1")
                  ans2 (str "aoc2018." (first args) "/answer2")
                  ]
              (require (symbol (str "aoc2018." (first args))))
              (println (symbol ans1))
              (println (var-get (resolve (symbol ans1))))
              (println (symbol ans2))
              (println (var-get (resolve (symbol ans2)))))
            )
    )
  )

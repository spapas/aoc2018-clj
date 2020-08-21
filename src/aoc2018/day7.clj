(ns aoc2018.day7
  (:require [ aoc2018.common :as co]
            [ clojure.string :as str]
             [ clojure.set :as set]))

(def day7-input (co/get-lines "input/day7" ))

(def parse-pattern 
  #"Step (\w) must be finished before step (\w) can begin.")

(def parsed-input 
  (map #(let [r (re-find parse-pattern %)] [(second r) (nth r 2)]) day7-input))

(def first-steps
  (set/difference
    (set (map first parsed-input))
    (set (map second parsed-input))))

(def all-steps
  (set/union
    (set (map first parsed-input))
    (set (map second parsed-input))))

(def left-steps
  (set (map first parsed-input)))

(def right-steps
  (set (map second parsed-input)))

(defn min-char [chars]
  (first (sort chars)))

(defn get-step-prereqs [step]
  (set (map first (filter #(= step (second %)) parsed-input)))
  )
  
(def prereqs (into {} (map #(vec [% (get-step-prereqs %)]) all-steps)))

(defn step-ready [step taken]
  (let [step-prereqs (get prereqs step)]
    (empty? (set/difference step-prereqs (set taken)))))

(defn pick-step [taken remaining]
  (min-char (filter #(step-ready % taken) remaining)))

(defn dosteps [taken remaining]
  (if (empty? remaining)
    taken
    (let
      [picked (pick-step taken remaining)]
      (recur (conj taken picked) (disj remaining picked)))))

(def answer1 (str/join (dosteps [] all-steps)))

(defn get-task-duration [task]
  (+ (inc (- (int (.charAt task 0)) (int \A))) 60))

(defstruct worker :name :task :remain)

(def initial-workers (map #(struct worker (inc %) nil nil) (range 5)))

(def steps-order (dosteps [] all-steps))

(defn simulation [steps workers t]
  )

(def answer2 43)

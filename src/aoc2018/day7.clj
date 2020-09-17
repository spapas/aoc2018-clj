(ns aoc2018.day7
  (:require [aoc2018.common :as co]
            [clojure.string :as str]
            [clojure.set :as set]))

(def day7-input (co/get-lines "input/day7"))

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
  (set (map first (filter #(= step (second %)) parsed-input))))

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

(def initial-workers (map #(struct worker (inc %) nil 0) (range 5)))

(def steps-order (dosteps [] all-steps))

(defn remaining-time [workers]
  (:remain (apply max-key :remain workers)))

(defn dec-remain [worker]
  (update worker :remain #(dec %)))

(defn ready-steps [taken remaining acc]
  (let [picked (pick-step taken remaining)]
    (if (nil? picked) acc
        (recur taken (disj remaining picked) (conj acc picked)))))

(defn step-workers [workers]
  (let [dec-workers (map dec-remain workers)]
       
       ))

(defn simulation [done remaining workers t]
  (if (empty? remaining)
    (+ t (remaining-time workers))
    (let
     [rsteps (ready-steps done remaining [])
      dec-workers (map dec-remain workers)
      
      free-worker (filter dec-remain workers)]
      (if (empty? rsteps)
        (recur taken remaining new-workers (inc t))
        (recur (conj taken picked) (disj remaining picked) [] (inc t))))))


(def answer2 43)

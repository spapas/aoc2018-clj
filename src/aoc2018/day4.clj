(ns aoc2018.day4
  (:require [ aoc2018.common :as co]
             [ clojure.set :as set])
  )

(def day4-input (sort (co/get-lines "input/day4")))

(def parse-pattern 
  #"\[\d{4}-\d{2}-\d{2} \d{2}:(\d{2})\] (falls|wakes|Guard) (?:#(\d+))?")

(def answer1 42)
(def answer2 43)

(defn pr-input []
  (doseq [z (sort day4-input)] (println z))
   )

(defn parse-line [l o-id]
  (let [r (re-find parse-pattern l)
        minute (co/parse-int (nth r 1))
        action-str (nth r 2)
        action (cond 
                 (= action-str "Guard") :id
                 (= action-str "wakes") :awake
                 (= action-str "falls") :asleep)
        id (if (= action :id) (co/parse-int 
                                (nth r 3)) 
                                o-id)

        ] {:id id :action action :minute minute}))

(defn parse-lines [l o-id acc]
  (let [f (first l)
        r (rest l)
        ]
    (if (nil? f) 
      acc
      (let [pl (parse-line f o-id)
            id (:id pl)]
        (parse-lines r id (conj acc pl ))
    ))))

(def parsed-lines (parse-lines day4-input 0 []))

(defn guard-reducer [acc entry]
  (let [guards (:guards acc)
        asleep (:asleep acc)
        action (:action entry)
        id     (:id entry)
        minute (:minute entry)]
    (cond 
      (= action :id) acc
      (= action :asleep) (assoc acc :asleep minute)
      (= action :awake) (let [old-guard (get guards id [])
                             new-guard (conj old-guard [asleep minute])
                             new-guards (assoc guards id new-guard)]
                             (assoc acc :guards new-guards)))))

(def guards (reduce guard-reducer {:guards {} :asleep 0} parsed-lines))

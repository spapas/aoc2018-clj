(ns aoc2018.day4
  (:require [ aoc2018.common :as co]
             [ clojure.set :as set])
  )

(def day4-input (sort (co/get-lines "input/day4")))

(def parse-pattern 
  #"\[\d{4}-\d{2}-\d{2} \d{2}:(\d{2})\] (falls|wakes|Guard) (?:#(\d+))?")

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

(def guards (:guards (reduce guard-reducer {:guards {} :asleep 0} parsed-lines)))

(defn get-total-sleep-time[guard]
  (let [times (nth guard 1)]
    (reduce #(+ %1 (- (nth %2 1) (nth %2 0))) 0 times)))

(def most-sleepy-guard (apply max-key get-total-sleep-time (vec guards)))
(def most-sleepy-guard-sleep-time (get-total-sleep-time most-sleepy-guard))

(defn inc-sleep-times [sleep-times period]
  (let [from (nth period 0) to (nth period 1)]
  (reduce (fn[acc m] (update acc m #(if (nil? %) 1 (inc %) ))) sleep-times (range from to))))

(defn get-sleepy-minutes [guard]
  (reduce inc-sleep-times {} (nth guard 1)))

(def sleepy-guard-sleepy-minutes (get-sleepy-minutes most-sleepy-guard ))

(defn get-most-sleepy-minute [guard-minutes] 
  (apply max-key #(nth % 1) (vec guard-minutes)))

(def most-sleepy-minute (get-most-sleepy-minute sleepy-guard-sleepy-minutes))

(def answer1 (* (first most-sleepy-guard) (first most-sleepy-minute)))

(def all-guard-sleepy-minutes (map 
                                #(vec [(nth % 0) (get-sleepy-minutes %)]) 
                                guards))
(def all-guard-most-sleepy-minute (map 
                                    #(vec [(nth % 0) (get-most-sleepy-minute (nth % 1))]) 
                                    all-guard-sleepy-minutes))

(def most-sleepy-guard-by-minute
  (apply max-key #(-> % second second) all-guard-most-sleepy-minute))

(def answer2 (* (first most-sleepy-guard-by-minute) (-> most-sleepy-guard-by-minute
                                                        second first )))

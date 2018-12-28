(require ['clojure.string :as 'str])

(defn get-lines [file]
  (str/split-lines (slurp file)))

(defn parse-int [s]
  (Integer/parseInt s))

(def day1-input (get-lines "day1"))
(def day1-input-parsed (map parse-int day1-input))
(def answer1  (apply + day1-input-parsed)) 

(defn answer2 [input frequency-accumulator existing-freqs]
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

(answer2 (cycle day1-input-parsed) 0 #{})

(require ['clojure.string :as 'str])

(defn get-lines [file]
  (str/split-lines (slurp file)))

(defn parse-int [s]
  (Integer/parseInt s))

(defn in? 
  "true if coll contains elm"
  [coll elm]  
  (some #(= elm %) coll))

(def day1-input (get-lines "day1"))
(def day1-input-parsed (map parse-int day1-input))
(def answer1  (apply + day1-input-parsed)) 

(defn answer2 [input frequency-accumulator existing-freqs]
  (let [
    current-element (first input)
    current-frequency-acc (+ current-element frequency-accumulator)]
    (if (in? existing-freqs current-frequency-acc)
      current-frequency-acc
      (answer2 (rest input) current-frequency-acc (cons current-frequency-acc existing-freqs))
      )
    )
  )
(def day1-input-doubled (concat day1-input-parsed day1-input-parsed day1-input-parsed day1-input-parsed))
(answer2 day1-input-doubled 0 '())

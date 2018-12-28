(require ['clojure.string :as 'str])

(defn get-lines [file]
  (str/split-lines (slurp file)))

(defn parse-int [s]
  (Integer/parseInt s))

(def day1-input (get-lines "day1"))

(def answer1  (apply + (map parse-int day1-input))) 

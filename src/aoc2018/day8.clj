;(require '[aoc2018.day8 :as day8 :refer :all] :reload-all)

(ns aoc2018.day8
  (:require [aoc2018.common :as co]
            [clojure.string :as str]
            [clojure.set :as set]))

(def input
  (map co/parse-int
       ;(str/split (str/trim (slurp "input/day8")) #" ")))
       (str/split "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2" #" ")))

(defn node-reader [st tree acc]
  (let [top (first st)
        rst (rest st)
        nn (first tree)
        nm (second tree)
        n (first top)
        m (second top)]
    ;(println st)
    (if (empty? tree)
      acc
      (if (= n 0)
        (recur rst (drop m tree) (+ acc (reduce + (take m tree))))
        (recur

         (cons [nn nm]  (cons [(dec n) m] rst))
         (drop 2 tree)
         acc)))))

(defn do-read [tree]
  (let [ft (first tree) st (second tree)]
    ;(+ (reduce + (take st (reverse tree)))
    (node-reader (list [ft st]) (drop 2 tree) 0)))
;)

(def part1 (do-read input))

(defstruct node :node_num :meta_num :nodes :metadata :parent)

(defn node-reader2 [st tree curr]
  (let [top (first st)
        rst (rest st)
        nn (first tree)
        nm (second tree)
        node_num (:node_num top)
        meta_num (:meta_num top)]
    (println top)
    (if (empty? tree)
      curr
      (if (= node_num 0)
        (recur
         rst
         (drop meta_num tree)
         (let [parent (:parent (assoc curr :metadata (take meta_num tree)))]
           (if (nil? parent) curr parent)))
        (let [new_node (struct node nn nm [] [] curr)]
          (recur (cons new_node
                       (cons
                        (update (update top :nodes #(conj % new_node)) :node_num dec)
                        rst))
                 (drop 2 tree)
                 new_node))))))

(defn do-read2 [tree]
  (let [ft (first tree)
        st (second tree)
        root (struct node ft st [] [] nil)]
    (node-reader2 (list root) (drop 2 tree) root)))
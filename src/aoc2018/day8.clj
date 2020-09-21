;(require '[aoc2018.day8 :as day8 :refer :all] :reload-all)

(ns aoc2018.day8
  (:require [aoc2018.common :as co]
            [clojure.string :as str]
            [clojure.zip :as z]
            [clojure.set :as set]))

(def input
  (map co/parse-int
       (str/split (str/trim (slurp "input/day8")) #" ")))
       ;(str/split "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2" #" ")))

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

(defstruct node :node_num :meta_num :nodes :metadata)

(defn tree-reader [nums tree]
  ;(println nums)
  ;(println tree)
  (let [top (z/node tree)
        nn (first nums)
        nm (second nums)
        node_num (:node_num top)
        meta_num (:meta_num top)]

    (if (empty? nums)
      tree
      (if (= node_num 0)
        (if (= (count nums) meta_num)
          (z/edit tree #(assoc % :metadata (take meta_num nums)))
          (recur
           (drop meta_num nums)
           (z/up (z/edit tree #(assoc % :metadata (take meta_num nums))))))

        (recur
         (drop 2 nums)
         (-> tree
             (z/edit #(update % :node_num dec))
             (z/append-child (struct node nn nm [] []))
             (z/down)
             (z/rightmost)))))))


(defn read-tree [nums]
  (let [ft (first nums)
        st (second nums)
        root (struct node ft st [] [])
        tree (z/zipper (fn [_] true) :nodes #(assoc %1 :nodes %2) root)]
    (tree-reader (drop 2 nums) tree)))

(defn get-node-from-meta [node meta]
  (cond
    (= 0 meta) nil
    (> meta (count (:nodes node))) nil
    true (get (vec (:nodes node)) (dec meta))))

(def tree-input (z/node (read-tree input)))

(defn get-node-value [node]
  ;(println node)
  (if (nil? node)
    0
    (if (= 0 (count (:nodes node)))
      (reduce + (:metadata node))
      (reduce +
      (map #(get-node-value (get-node-from-meta node %)) (:metadata node))))))

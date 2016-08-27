(ns game-of-life.core
  (:gen-class))

(def g-board-size 20) ;; constant board-size for testing

(def g-board #{[8 9] [9 10] [10 8] [10 9] [10 10]}) ;; initial board

(defn full-board
  "Generate all the points in a square board with dimension board-size"
  [board-size]
  (set (for [x (range 0 board-size) y (range 0 board-size)] [x y])))

(defn neighbors-at-point
  "Find all the neighboring points around a point"
  [i j]
  (filter
   (fn [pt]
     (let [m (first pt)
           n (last pt)]
       (and (>= m 0) (>= n 0) (< m g-board-size) (< n g-board-size)
            (not (and (= m i) (= n j))))))
   (for [x (range -1 2) y (range -1 2)] [(+ x i) (+ y j)])))

(defn count-of-living-neighbors-at-point
  "Returns the count of living neighbors around a point, given a current-board of living points."
  [i j current-board]
  (count (filter #(contains? current-board %) (apply neighbors-at-point [i j]))))

(defn point-will-live
  "Returns whether a point will live in the next board, given the current board of living points."
  [i j current-board]
  (let [n-count (count-of-living-neighbors-at-point i j current-board)]
    (if (contains? current-board [i j])      
      (or (= n-count 2) (= n-count 3))
      (= n-count 3))))

(defn next-board
  "Returns the next board of living points, given the current one."
  [current-board]
  (reduce (fn [final-set pt]
            (if (point-will-live (first pt) (last pt) current-board)
              (conj final-set pt)
              final-set))
          #{}
          (full-board g-board-size)))

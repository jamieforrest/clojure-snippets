(ns clojure-snippets.core
  (:require [game-of-life.core :refer :all])
  (:gen-class))

(defn -main
  [& args]
  (println (next-board g-board)))

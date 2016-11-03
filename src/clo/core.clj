(ns clo.core
  (:gen-class))


(defn fib-nth [n]
  (if (< n 2)
    n
    (+
      (fib-nth (- n 1))
      (fib-nth (- n 2)))))

(defn demo []
  (print "----------------\n")
  (doseq [n [5 10 20 30 35 36 37 38 39 40]]
    (print (str "\nComputing fib-nth(" n ")\n"))
    (time (fib-nth n))))

(print "Run demo as (demo)\n")

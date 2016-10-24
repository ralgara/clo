# Clojure Sessions #3 - Date TBD
## RECURSION

Clojure, owing to its roots in LISP and influences from Haskell, Erlang and others, has a wide variety of options to express recursive programs.

***
## Mandatory Fibonacci example

There are dozens of approaches to recursion. Let's start simple.

    ; naive implementation
    (defn fib-nth [n]
      (if (< n 2)
        n
        (+
          (fib-nth (- n 1))
          (fib-nth (- n 2)))))

    ; easy
    (time (fib-nth 10))

    ; still easy
    (time (fib-nth 30))

    ; what's happening?
    (time (fib-nth 40))



The naive approach blindly recalculates the function for every `n`. We need to cache (a.k.a. _memoize_) our outputs. Many possible ways exist.

And so, our first optimization    

    (def fib-nth
      (memoize
        (fn [n]
          (if (< n 2)
            n
            (+
              (fib-nth (- n 1))
              (fib-nth (- n 2)))))))

## Detour into laziness

    (nth [1 2 3 4 5] 3) ; eager

    (nth (range 0 1e8 7) 21) ; lazy

    (nth (range 0 1e8 1E5) 21) ; lazy

    (def s (range 0 1e7 7))

    (count s) ; !!!

    (take 10 s)

    (take-last 10 s) ; !!!



; a handy one is [lazy-seq](https://clojuredocs.org/clojure.core/lazy-seq).




## Import dependencies (see project.clj)

    (require '[org.httpkit.client :as http])
    (require '[clojure.data.json :as json])
    (require '[clojure.walk :as walk])
    (use 'clojure.java.io)

* Notice `require` vs `use`. `require` imports the library in its own namespace (aliased for brevity via `:as`). `use` imports directly into the current namespace.

## Read and parse a sample tweet

    (def testdoc
      (walk/keywordize-keys
        (json/read-str
          (slurp "sample.json"))))

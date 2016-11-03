Continued: _Recursion optimization of Fibonacci sequence_

A simple optimization is just to cache results so we don't recalculate, dropping complexity to O(N). This is known as _memoization_.

Memoization is possible in most languages with auxiliary lookup maps. In most cases like Java, Python and Ruby, the results are quite ugly although some non-standard libraries help. Groovy goes a better job with _Memoize_.

But functional languages like Clojure and Haskell address this cleanly and directly:

And so, our first optimization    

    (def fib-nth
      (memoize
        (fn [n]
          (if (< n 2)
            n
            (+
              (fib-nth (- n 1))
              (fib-nth (- n 2)))))))

So try our problem call from the naive solution again:

    (time (fib-nth 40))
    ; fast! how about 2x, 10x, ...?
    (time (fib-nth 80))
    ; so fast we'll need a little tweak

    (def fib-nth
      (memoize
        (fn [n]
          (if (< n 2)
            n
            (bigint
              (+
                (fib-nth (- n 1))
                (fib-nth (- n 2))))))))

    (time (fib-nth 100))

    (time (fib-nth 200))

    (time (fib-nth 1000))

    (time (fib-nth 10000)) ;?

Enter tail recursion

## Detour into laziness

Clojure along with many modern languages relies heavily on lazy sequences. The main advantage is deferred realization.

    (nth [1 2 3 4 5] 3) ; eager

    (nth (range 0 1E10 7) 10) ; lazy

    ; but deferred is still real
    (time (nth (range 0 1E10 7) 3E7))

    (def s (range 0 1E10 7))

    (count s) ; !!!

    (take 10 s)

    (take-last 10 s) ; !!!

* When thinking about computation over lazy sequences, considering actual realization costs are key.

* We can convert an eager collection to a lazy one with [lazy-seq](https://clojuredocs.org/clojure.core/lazy-seq).

* The inverse is done with `doall`

Back to Fibonacci, but this time as a sequence:

    (defn fib-seq
      ([]
         (fib-seq 0 1))
      ([a b]
         (lazy-seq
          (cons b (fib-seq b (+ a b))))))

    (take 20 (fib-seq))


## Final word on recursion

One of the main challenges in large recursive operations managing the stack. Complex recursion over deeply nested data structures often causes overflow. Techniques like tail recursion and trampoline are often used to address this. We will not cover these right away but will revisit them when we get to working with large trees.

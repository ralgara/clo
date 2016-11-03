Continued: _Write a function to get the Nth element of the Fibonnacci sequence_

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

The naive approach blindly recalculates the function for every `N`, hence its complexity is O(2^N).

How can this be optimized?

[Next](session-3-3.md)

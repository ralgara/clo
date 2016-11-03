# Clojure Sessions #3 - Nov 3, 2016

## RECURSION

Clojure, owing to its roots in LISP and influences from Haskell, Erlang and others, has a wide variety of options to express recursive programs.

## First, lists revisited

### cons

Stands for _construct_ (akin to a constructor in OOP). For lists, `cons` prepends an object to the head of a list.

    (cons "rock" '("paper" "scissors"))
    ; better yet:
    (cons :rock '(:paper :scissors))

Write a reverse (name `myreverse` to avoid name collision with core function) function using only `cons`, `last`, `butlast` and `nil?`

[Next](session-3-1.md)

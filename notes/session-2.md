# Clojure Sessions #2 - October 20th, 2016
***
## Starting up: Leiningen

**R**ead **E**valuate **P**rint **L**oop

lein repl

***
## Scalars and naming things

    (def one 1)               ; btw, semicolons are for comments

    (def pi-ish 3.1416)       ; punctuation, with a few exceptions

    (def some.hex 0xbeef)     ; is allowed and useful in Clojure

    (def oct|al 017)

    (def bin 2r10101)

    (def whut? 36rCRAZY)

    (def ratio -22/7)
***
## Collections Refresher

#### Vectors

* Random access by index


    (vector 1 2 3)

    (def stuff [1 2 3 "foo" "bar"])   ; vector literal

    (get stuff 3)

    (def more_stuff [stuff ["and" "then" "some"]])

    (get (get more_stuff 0) 3)

#### Lists

* Sequential access
* **Homomorphism**: every Clojure program is a list, and the reader assumes the first element of a list is a function reference. For list literals in shorthand form, use leading quote


    (list 1 2 3)

    (def planets '("mercury" "venus" "earth")) ; literal

    (def stars '("vega" "sirius" "polaris"))

    (list stars planets)

    (flatten (list stars planets))

#### Maps

* Random access by key
* **Direct mapping to JSON** (a lot more on this later)
* Keys can be any object or scalar
* Keywords (e.g. `:year`) have useful semantics for maps and are usually the key type of choice

## Functions

    (+ 3 57)

    (fn [x] (* 7 x))          ; function definition

    ((fn [x] (* 7 x)) 8)      ; inline invocation

    (def f (fn [x] (* 7 x)))  ; binding

    (defn f [x] (* 7 x))      ; shorthand form

    (f 8)                     ; invocation by reference

    (#(* 7 %) 8)              ; anonymous literal (one argument)

    (#(* %1 %2) 9 7)          ; two or ore arguments

    (.toUpperCase "big letters")    ; Java interop


## Functional programming basics

    (def numbers [1 2 3 4 5 6 7 8 9])

    (map #(* 10 %) numbers)

    (map #(.toUpperCase %) (.split "four-score-and-seven-years-ago" "-"))

    (reduce + (map #(* 10 %) numbers))

    (filter odd? numbers)

    (filter #(< % 5) numbers)

    (group-by even? numbers)

## Let's have some fun

But some official business first. We need to import our dependencies into the namespace (see project.clj)

    (require '[org.httpkit.client :as http])
    (require '[clojure.data.json :as json])
    (require '[clojure.walk :as walk])

    (defn get-document [url]
      @(http/get url
        (fn [{:keys [status headers body error]}] body)))

The @ form above _dereferences_ the future returned by _http/get_, making _get-document_ a blocking call

    (get-document "http://clarabridge.com")     ; first target =)

    (defn get-json [url]                        ; now for JSON
      (json/read-str (get-document url)))

    (def version-url "http://d5-d01-dev01:10010/version")

    (get-json version-url)

    (get (get-json version-url) "version")

Now let's prepare a POST payload to hit Analytics

    (def req {
      :attr [{:name "director" :size 2}]
      :metrics [{:name "rating_metric"}]
      :projectId 4036746
      :debug 0
      :clientRequestId "test-clojure-client"
      :filter {
        :date {
          :startDate 20140101
          :endDate 20140110
        }
      }
      :source {
        :index {:dataIndexName "1$4036746"}
        :cluster {
          :hosts [{:http {:hostAddress "10.81.89.46",:port 9200}}]
        }
      }
    })

    (defn get-json [url payload]
      @(http/post
        url
        {
          :body (json/write-str payload)
          :headers {"Content-Type" "application/json"}
        }
      (fn [{:keys [status headers body error]}]
        (json/read-str body))))

Aim for our target

    (def url "http://d5-d01-dev01:10010/v1/reports/struct_agg")

Tweak the payload

    (def req
      (assoc req :attr [
        {:name "director" :size 30}
        {:name "lob" :size 30}
        {:name "dvp" :size 30}]))

And fire

    (def data (get (get-json url req) "data"))

    (keys data)

    (get data "doc_count")

    (def tree (walk/keywordize-keys data))

    (:doc_count tree)

    (get-in tree [:director :buckets 0 :lob :buckets 0 :dvp   :buckets])

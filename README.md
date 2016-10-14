# clo

### REPL excercise #1

(require '[org.httpkit.client :as http])
(require '[clojure.data.json :as json])
(require '[clojurewerkz.elastisch.rest :as esr])
(use 'clojure.reflect)

(json/read-str "{\"a\":1,\"b\":2}")

(def url "http://d5-d01-dev01:10010/version")

(http/get url
  (fn [{:keys [status body]}] (println body)))

(def x
  (http/get url
    (fn [{:keys [status headers body error]}] body))

(def jx (json/read-str @x))

(class jx)

(keys jx)

(get jx "version")

(def req {
  :attr [{:name "director", :size 2}]
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

(def url "http://d5-d01-dev01:10010/v1/reports/struct_agg")

(http/post
    url
    {
      :body (json/write-str req)
      :headers {"Content-Type" "application/json"}
    }
  (fn [{:keys [status headers body error]}]
    (println body)))

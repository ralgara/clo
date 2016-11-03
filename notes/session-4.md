# Clojure Sessions #4 - TBD

## Trees

    (def raw {"metadata" {
      "status" "Success",
      "document_query" {},
      "stats" {
        "doc_count" 297603,
        "total_execute_ms" 522,
        "request_length" 1358
      },
      "version" "0.2.74",
      "qid" "024d856c-3bae-4a3a-9100-21941d4a28ba",
      "client_address" "10.120.167.213"
    },
    "data" {
      "avg_agent_rating" {
        "value" 3.576485329435105
      },
      "num_agent_rating" {
        "value" 582284
      },
      "director" {
        "buckets" [
          {
            "key" "tim madson",
            "doc_count" 105806,
            "avg_agent_rating" {
              "value" 3.497214404686692
            },
            "num_agent_rating" {
              "value" 365340
            },
            "lob" {
              "buckets" [
                {
                  "key" "online",
                  "doc_count" 70558,
                  "avg_agent_rating" {
                    "value" 3.521404011461318
                  },
                  "num_agent_rating" {
                    "value" 245794
                  }
                },
                {
                  "key" "delivery/installation",
                  "doc_count" 11720,
                  "avg_agent_rating" {
                    "value" 3.3523261892315737
                  },
                  "num_agent_rating" {
                    "value" 38478
                  }
                }
              ]
            }
          },
          {
            "key" "michele vercimak",
            "doc_count" 26752,
            "avg_agent_rating" {
              "value" 4.014186011227431
            },
            "num_agent_rating" {
              "value" 105830
            },
            "lob" {
              "buckets" [
                {
                  "key" "technical specialist",
                  "doc_count" 6481,
                  "avg_agent_rating" {
                    "value" 3.98728614032334
                  },
                  "num_agent_rating" {
                    "value" 25403
                  }
                },
                {
                  "key" "online",
                  "doc_count" 5657,
                  "avg_agent_rating" {
                    "value" 3.595467523197716
                  },
                  "num_agent_rating" {
                    "value" 20149
                  }
                }
              ]
            }
          }]
      },
      "doc_count" 297603
      }
    })

    (require '[clojure.walk :as walk])

    (def tree (walk/keywordize-keys raw))

    (def tree {:metadata {:status "Success", :document_query {}, :stats {:doc_count 297603, :total_execute_ms 522, :request_length 1358}, :version "0.2.74", :qid "024d856c-3bae-4a3a-9100-21941d4a28ba", :client_address "10.120.167.213"}, :data {:avg_agent_rating {:value 3.576485329435105}, :num_agent_rating {:value 582284}, :director {:buckets [{:key "tim madson", :doc_count 105806, :avg_agent_rating {:value 3.497214404686692}, :num_agent_rating {:value 365340}, :lob {:buckets [{:key "online", :doc_count 70558, :avg_agent_rating {:value 3.521404011461318}, :num_agent_rating {:value 245794}} {:key "delivery/installation", :doc_count 11720, :avg_agent_rating {:value 3.3523261892315737}, :num_agent_rating {:value 38478}}]}} {:key "michele vercimak", :doc_count 26752, :avg_agent_rating {:value 4.014186011227431}, :num_agent_rating {:value 105830}, :lob {:buckets [{:key "technical specialist", :doc_count 6481, :avg_agent_rating {:value 3.98728614032334}, :num_agent_rating {:value 25403}} {:key "online", :doc_count 5657, :avg_agent_rating {:value 3.595467523197716}, :num_agent_rating {:value 20149}}]}}]}, :doc_count 297603}}
    )

    (keys tree)

    (doseq [node tree]
      (println node)
      (println "------"))

    (first (seq tree))

    (defn walk [node]
      (cond
        (map? node)
        (do
          (print "map\n")
          (map walk node))

        (vector? node)
        (do
          (print "vector\n")
          (map walk node))

        (map-entry? node)
        (do
          (print "entry\n")
          (walk (last node)))

        (keyword? node)
        (do
          (print "keyword\n")
          node)))

    (walk tree)



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

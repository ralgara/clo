(defproject clo "0.1.0-SNAPSHOT"
  :dependencies [
    [org.clojure/clojure "1.8.0"],
    [org.clojure/data.json "0.2.6"]
    [clojurewerkz/elastisch "2.2.2"],
    [http-kit "2.1.18"]]
  :main clo.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

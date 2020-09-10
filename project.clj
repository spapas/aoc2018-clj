(defproject aoc2018 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [vlaaad/reveal "0.1.0-ea30"]]
  :main ^:skip-aot aoc2018.core
  :target-path "target/%s"
  :plugins [[cider/cider-nrepl "0.25.3"]
            [org.clojars.benfb/lein-gorilla "0.7.0"]]
  :profiles {:uberjar {:aot :all}})

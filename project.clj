(defproject aoc2018 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.9.0"]]
  :main ^:skip-aot aoc2018.core
  :target-path "target/%s"
  :plugins [[cider/cider-nrepl "0.18.0"][org.clojars.benfb/lein-gorilla "0.5.0"]]
  :profiles {:uberjar {:aot :all}})

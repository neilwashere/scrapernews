(defproject scrapernews "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha12"]
                 [http-kit "2.2.0"]
                 [http-kit.fake "0.2.1"]
                 [enlive "1.1.6"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/tools.cli "0.3.5"]]
  :main ^:skip-aot scrapernews.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[org.clojure/test.check "0.9.0"]]}})

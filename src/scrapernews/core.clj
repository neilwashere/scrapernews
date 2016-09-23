(ns scrapernews.core
  (:gen-class)
  (:require [clojure.string :as string]
            [clojure.tools.cli :refer [parse-opts]]
            [scrapernews.hackernews :as hackernews]
            [clojure.data.json :as json]))

(defn- error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (string/join \newline errors)))

(def cli-options
  [["-p" "--posts POSTS" "Number of posts to retrieve"
    :default 20
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]])

(defn- exit
  [status msg]
  (println msg)
  (System/exit status))

(defn- hackernews
  "Simple delegate to ease testing"
  [opts]
  (hackernews/hackernews opts))

(defn -main [& args]
  "Takes a set of options to pass on to a site for post scraping.
  prints out the resulting JSON transformations to STDOUT."
  (let [{:keys [options errors]} (parse-opts args cli-options)]
    (if errors
      (exit 1 (error-msg errors))
      (let [posts (hackernews options)]
        (json/pprint posts :escape-slash false :escape-unicode false)))))

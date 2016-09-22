(ns scrapernews.specs
  (:require [clojure.spec :as s]
            [clojure.string :as string])
  (:import [java.net URI]))

(defn valid-uri?
  [uri]
  (try
    (.toURL (URI. uri))
    (catch Exception _
        false)))

(s/def ::string256 (s/and string? #(not (string/blank? %)) #(<= (count %) 256)) )

(s/def ::positive-int (s/and int? #(>= % 0)))

(s/def ::author ::string256)

(s/def ::title ::string256)

(s/def ::points ::positive-int)

(s/def ::comments ::positive-int)

(s/def ::rank ::positive-int)

(s/def ::uri (s/and string? #(valid-uri? %)))

(s/def ::hacker-post (s/keys :req [::title ::author ::uri ::points ::comments ::rank]))

(defn valid-hacker-post?
  [post]
  (s/valid? ::hacker-post post))

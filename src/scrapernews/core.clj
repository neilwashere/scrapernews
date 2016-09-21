(ns scrapernews.core
  (:require [org.httpkit.client :as http]
            [net.cgrand.enlive-html :as html]
            [clojure.string :as string]
            [clojure.data.json :as json]))


(defn url->dom
  [url] ;; maybe some validation here
  (let [{:keys [body error] :as resp} @(http/get url)]
    (if error
      (throw (ex-info "Unable to get dom" {:causes error}))
      (html/html-snippet body))))

(defn title
  [node]
  (first (html/select node [:a.storylink :> html/text])))

(defn uri
  [node]
  (get-in (first (html/select node [:a.storylink])) [:attrs :href]))

(defn author
  [node]
  (first (html/select node [:a.hnuser :> html/text])))

(defn points
  [node]
  (some-> (html/select node [:span.score :> html/text])
          first
          (string/split #"\s")
          first
          read-string))

(defn comments
  [node]
  (some-> (html/select node [:a :> html/text])
          last
          (string/split #" ")
          first
          read-string))

(defn rank
  [node]
  (-> (html/select node [:span.rank :> html/text])
      first
      (string/split #"\.")
      first
      read-string))

(defn hack-extract
  [[link meta _]]
  {:title     (title link)
   :uri       (uri link)
   :authortor (author meta)
   :points    (points meta)
   :comments  (comments meta)
   :rank      (rank link)})

(defn stories
  [dom]
  (partition 3 (html/select dom [:table.itemlist :tr])))

(defn story-comprehension
  [stories]
  (for [story stories]
    (hack-extract story)))

(defn valid-story
  [story]
  )

(defn -main [& args]
  (let [all-stories
        (->> (url->dom "http://news.ycombinator.com")
             stories
             story-comprehension
             (take (or (first args) 100)))]
    (json/pprint all-stories :escape-slash false :escape-unicode false)))

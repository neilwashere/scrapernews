(ns scrapernews.hackernews
  (:require [clojure.data.json :as json]
            [clojure.string :as string]
            [net.cgrand.enlive-html :as html]
            [org.httpkit.client :as http]
            [scrapernews.specs :as specs]))

(def hacker-url "http://news.ycombinator.com")

(defn url->dom
  [url]
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

(defn post-html->map
  [[link meta _]]
  {:scrapernews.specs/title     (title link)
   :scrapernews.specs/uri       (uri link)
   :scrapernews.specs/author    (author meta)
   :scrapernews.specs/points    (points meta)
   :scrapernews.specs/comments  (comments meta)
   :scrapernews.specs/rank      (rank link)})

(defn all-posts
  [dom]
  (partition 3 (html/select dom [:table.itemlist :tr])))

(defn post-comprehension
  [posts]
  (for [post-html posts]
    (post-html->map post-html)))

(defn hacker-init
  [options]
  (let [num-posts (:posts options)
        potential-posts (-> (url->dom hacker-url)
                              all-posts
                              post-comprehension)
        posts (take num-posts (filter specs/valid-hacker-post? potential-posts))]
    (json/pprint posts :escape-slash false :escape-unicode false)))

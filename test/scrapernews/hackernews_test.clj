(ns scrapernews.hackernews-test
  (:require [scrapernews.hackernews :as sut]
            [clojure.test :as t]
            [org.httpkit.fake :refer [with-fake-http]]))

(t/deftest hackernews
  ;; mock out the call the hackernews.
  ;; Here be dragons!
  (let [page (slurp "test/fixtures/hackernews.html")]
    (with-fake-http [sut/hacker-url page]
      (let [result (sut/hackernews {:posts 2})]

        (t/testing "It will return a seq of posts"
          (t/is (seq? result)))

        (t/testing "It will contain 2 entries"
          (t/is (= (count result) 2)))

        (t/testing "The entries will conform to a specification"
          (t/is (every? #(scrapernews.specs/valid? :scrapernews.specs/hacker-post %) result)))))))

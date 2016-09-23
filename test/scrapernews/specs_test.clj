(ns scrapernews.specs-test
  (:require [scrapernews.specs :as sut]
            [clojure.test :as t]
            [clojure.spec :as s]))

;; TODO -These tests are fairly simplistic and could be make more robust
;; using generators from the `clojure.spec.gen' namespace. I've ommited this as
;; there is it requires adding sample generation for the URI, which looks a bit iffy.
;; however, beyond this excercise, that's what I'd do.
(t/deftest hacker-post
  (let [sample-post
        {:scrapernews.specs/title    "A titles"
         :scrapernews.specs/uri      "http://example.com"
         :scrapernews.specs/author   "A. N. Other"
         :scrapernews.specs/points   1
         :scrapernews.specs/comments 1
         :scrapernews.specs/rank     1}]

    (t/testing "Valid post is valid"
      (t/is (sut/valid? :scrapernews.specs/hacker-post sample-post)))

    (t/testing "title must not be empty"
      (let [bad-post (assoc sample-post :scrapernews.specs/title "")]
        (t/is (not (sut/valid? :scrapernews.specs/title bad-post)))))

    (t/testing "title must not be larger that 256 chars"
      (let [bad-post (assoc sample-post :scrapernews.specs/title (clojure.string/join (repeat 257 "x")))]
        (t/is (not (sut/valid? :scrapernews.specs/title bad-post)))))

    (t/testing "author must not be larger that 256 chars"
      (let [bad-post (assoc sample-post :scrapernews.specs/author (clojure.string/join (repeat 257 "x")))]
        (t/is (not (sut/valid? :scrapernews.specs/author bad-post)))))

    (t/testing "author must not be empty"
      (let [bad-post (assoc sample-post :scrapernews.specs/author "")]
        (t/is (not (sut/valid? :scrapernews.specs/author bad-post)))))

    (t/testing "rank must be postive int"
      (let [bad-post (assoc sample-post :scrapernews.specs/rank -1)]
        (t/is (not (sut/valid? :scrapernews.specs/title bad-post)))))

    (t/testing "comments must be postive int"
      (let [bad-post (assoc sample-post :scrapernews.specs/comments -1)]
        (t/is (not (sut/valid? :scrapernews.specs/comments bad-post)))))

    (t/testing "points must be postive int"
      (let [bad-post (assoc sample-post :scrapernews.specs/points -1)]
        (t/is (not (sut/valid? :scrapernews.specs/points bad-post)))))

    (t/testing "uri must valid"
      (let [bad-post (assoc sample-post :scrapernews.specs/uri "")]
        (t/is (not (sut/valid? :scrapernews.specs/uri bad-post)))))))

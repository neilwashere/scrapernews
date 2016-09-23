(ns scrapernews.core-test
  (:require [clojure.test :as t]
            [scrapernews.core :as sut]))


(t/deftest -main
  (with-redefs [sut/hackernews (fn [_] {:a 1 :b "test"})
                sut/exit (fn [_ msg] (println msg))]

    (t/testing "it will produce json to STDOUT"
      (let [output (with-out-str (sut/-main "--posts" "2"))]
        (t/is (= output "{\"a\":1, \"b\":\"test\"}\n"))))

    (t/testing "it will fail with bad arg"
      (let [output (with-out-str (sut/-main "--posts" "four"))]
        (t/is (>= (.indexOf output "Error while parsing option") 0))))

    (t/testing "it will fail with bad options"
      (let [output (with-out-str (sut/-main "--post" "2"))]
        (t/is (>= (.indexOf output "Unknown option") 0))))))

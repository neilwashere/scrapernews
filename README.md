# scrapernews

A Cloure (hacker)news web scraper.

![hackattack](http://img.memecdn.com/What-a-hacker-looks-like-according-to-The-Sun-newspaper_c_120929.jpg)

## Installation

Grab the code from [github](git@github.com:neilwashere/scrapernews.git)
Or grab the docker image `docker pull neilwashere/scrapernews`

### Requirements

If running from source you will need
* Java 1.8 (although 1.7 should work)
* [Leiningen](http://leiningen.org/) (Clojure dependency manager and build toolkit. Make sure the `lein` command is on your path.

If you just want the docker image, make sure you have [docker](http://leiningen.org/)

## Usage

At the moment only [hackernews](http://news.ycombinator.com) is implemented.

From source

     $ lein run [args]

From docker

    $ docker run neilwashere/scrapernews [args]

If you just want a standalone jar, compile and install first

    $ lein uberjar

    $ cd target/uberjar

    $ java -jar scrapernews-0.1.0-SNAPSHOT-standalone.jar [args]

## Options

    `--posts` Number of posts to retrieve. Default 20

## Examples

     $ docker run neilwashere/scrapernews --posts 2

     [{"title":"OpenSSL Security Advisory",
       "uri":"https://www.openssl.org/news/secadv/20160922.txt",
       "author":"jgrahamc",
       "points":46,
       "comments":6,
       "rank":1},
      {"title":"The Age of the Superbug Is Already Here",
       "uri":
       "http://www.huffingtonpost.com/entry/antibiotic-resistance-crisis-un_us_57d8ea87e4b0fbd4b7bc66c4",
       "author":"pmcpinto",
       "points":44,
       "comments":32,
       "rank":2}]

Posts that have missing or invalid attributes will be discarded

### Libraries in use

* [http-kit](http://www.http-kit.org/) - A really nice async/sync HTTP library.
* [enlive](https://github.com/cgrand/enlive) - A more idiomatic way to parse HTML (no xpath!)
* [org.clojure/data.json](https://github.com/clojure/data.json) - Defacto JSON lib for Clojure
* [org.clojure/tools.cli](https://github.com/clojure/tools.cli) - Simple yet powerful command line arg processor

I have also used an alpha version of Clojure as this includes the phenomenal [clojure spec](http://clojure.org/about/spec) API.


## License

Unlicense
http://choosealicense.com/licenses/unlicense/

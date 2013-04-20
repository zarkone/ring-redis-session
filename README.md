What is clj-redis-session
=========================

`clj-redis-session` uses redis as a Clojure/Ring's HTTP session
storage engine. What makes it different is its support for
hierarchical data, actually any *print-str*able clojure data types.

Why clj-redis-session
=====================

The reason I wrote `clj-redis-session` is that the only redis-backed
sesssion store I could find ([rrss](https://github.com/paraseba/rrss))
doesn't support hierarchical data structures, e.g. lists, maps.

Installation
============

Add

    [clj-redis-session "1.0.0"]

to `:dependencies` in your `project.clj`.

Usage
=====

`clj-redis-session` is a drop-in replacement for Ring native session
stores. `clj-redis-session` uses
[Carmine](https://github.com/ptaoussanis/carmine) as its Redis client.

    (ns hello
      (:use
        ring.middleware.session
        [clj-redis-session.core :only [redis-store]])
      (:require
        [taoensso.carmine :as car]))

    ;; clj-redis-session use Carmine as its Redis client
    (def redis-pool (car/make-conn-pool))
    (def redis-spec (car/make-conn-spec))

    (def app
      (-> your-routes
          ... other middlewares ...
          (wrap-session {:store (redis-store redis-pool redis-spec)})
          ....))

Want sessions to automatically expire?

    # expire after 12 hours
    (wrap-session your-app {:store (redis-store redis-pool redis-spec {:expire-secs (* 3600 12)})})

You can also change the prefix (default to `session`) for the keys in
redis:

    (wrap-session your-app {:store (redis-store redis-pool redis-spec {:prefix "i-am-prefix"})})

License
=======

Copyright (C) 2013 Zhe Wu <wu@madk.org>

Distributed under the Eclipse Public License, the same as Clojure.

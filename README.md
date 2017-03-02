clj-redis-session
=================

[![Build Status][travis-badge]][travis]
[![Dependencies Status][deps-badge]][deps]
[![Clojars Project][clojars-badge]][clojars]
[![Tag][tag-badge]][tag]
[![JDK version][jdk-v]](.travis.yml)
[![Clojure version][clojure-v]](project.clj)

[![Project Logo][logo]][logo-large]

*A Redis backed Clojure/Ring session store*


What is it?
-----------

`clj-redis-session` uses redis as a Clojure/Ring's HTTP session
storage engine. What makes it different is its support for
hierarchical data, actually any `*print-str*`able clojure data types.


Why?
----

The reason `clj-redis-session` was written was that there was a need for
a Redis-backed session store that supported hierarchical data structures,
and the only Redis session store available ([rrss][rrss]) ... didn't.


Installation
------------

Add
```clojure

[clojusc/clj-redis-session "3.0.0-SNAPSHOT"]
```
to `:dependencies` in your `project.clj`.


Usage
-----

**¡Important!**

As of version 3.0.0 (as maintained in the Clojusc org), the
namespace for `clj-redis-session` has changed! Whereas before the
following was used:

```clj
clj-redis-session.core
```

This now needs to be updated to:

```clj
ring.redis.session
```


`clj-redis-session` is a drop-in replacement for Ring native session
stores. `clj-redis-session` uses [Carmine][carmine] as its Redis client.


First, require the session namespaces:

```clj
(ns your-app
  (:require [ring.middleware.session :as ring-session]
            [ring.redis.session :refer [redis-store]]))
```

Then define the Redis [connection options][redis conn opts] as you would when
using Carmine directly. For example:

```clj
(def conn {:pool {}
           :spec {:host "127.0.0.1"
                  :port 6379
                  :password "s3kr1t"
                  :timeout-ms 5000}})
```

At this point, you'll be ready to use `clj-redis-session` to manage your
application sessions:

```clj
(def your-app
  (-> your-routes
      (... other middlewares ...)
      (ring-session/wrap-session {:store (redis-store conn)})
      (...)))
```

If you are using `friend` for auth/authz, you will want to thread your security
wrappers first, and then the session. If you are using `ring-defaults` to wrap
for the site defaults, you'll want to thread the session wrapper before the
defaults are set.

Automatically expire sessions after 12 hours:

```clj
(wrap-session your-app {:store (redis-store conn {:expire-secs (* 3600 12)})})
```

Automatically extend the session expiration time whenever the session data is
read:

```clj
(wrap-session your-app {:store (redis-store conn {:expire-secs (* 3600 12)
                                                  :reset-on-read true})})
```

You can also change the default prefix, `session`, for the keys in Redis to
something else:

```clj
(wrap-session your-app {:store (redis-store conn {:prefix "your-app-prefix"})})
```


License
-------

Copyright © 2013 Zhe Wu <wu@madk.org>

Copyright © 2016 Clojure-Aided Enrichment Center 

Distributed under the Eclipse Public License, the same as Clojure.


[travis]: https://travis-ci.org/clojusc/clj-redis-session
[travis-badge]: https://travis-ci.org/clojusc/clj-redis-session.png?branch=dev
[logo]: resources/images/redis-logo-small.png
[logo-large]: resources/images/redis-logo.png
[rrss]: https://github.com/paraseba/rrss
[carmine]: https://github.com/ptaoussanis/carmine
[redis conn opts]: https://github.com/ptaoussanis/carmine/blob/master/src/taoensso/carmine.clj#L26
[deps]: http://jarkeeper.com/clojusc/clj-redis-session
[deps-badge]: http://jarkeeper.com/clojusc/clj-redis-session/status.svg
[tag-badge]: https://img.shields.io/github/tag/clojusc/clj-redis-session.svg
[tag]: https://github.com/clojusc/clj-redis-session/tags
[clojure-v]: https://img.shields.io/badge/clojure-1.8.0-blue.svg
[jdk-v]: https://img.shields.io/badge/jdk-1.7+-blue.svg
[clojars]: https://clojars.org/clojusc/clj-redis-session
[clojars-badge]: https://img.shields.io/clojars/v/clojusc/clj-redis-session.svg

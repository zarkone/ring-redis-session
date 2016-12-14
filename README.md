clj-redis-session
=================

[![Build Status][travis-badge]][travis]

*A Redis backed Clojure/Ring session store*

[![Project Logo][logo]][logo-large]


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

[clojusc/clj-redis-session "2.1.4-SNAPSHOT"]
```
to `:dependencies` in your `project.clj`.


Usage
-----

**¡Important!**

As of version 3.0.0 (as maintained in the Clojusc org), the
namespaces for `clj-redis-session` have changed! Whereas before the
following was used:

```clj
clj-redis-session.core :refer [redis-store]
```

This now needs to be updated to:

```clj
clojusc.redis.session :refer [redis-store]
```


`clj-redis-session` is a drop-in replacement for Ring native session
stores. `clj-redis-session` uses [Carmine][carmine] as its Redis client.


First, require the session namespaces:

```clj
(ns your-app
  (:require [ring.middleware.session :as ring-session]
            [clojusc.redis.session :refer [redis-store]]))
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

Distributed under the Eclipse Public License, the same as Clojure.


[travis]: https://travis-ci.org/clojusc/clj-redis-session
[travis-badge]: https://travis-ci.org/clojusc/clj-redis-session.png?branch=dev
[logo]: resources/images/redis-logo-small.png
[logo-large]: resources/images/redis-logo.png
[rrss]: https://github.com/paraseba/rrss
[carmine]: https://github.com/ptaoussanis/carmine
[redis conn opts]: https://github.com/ptaoussanis/carmine/blob/master/src/taoensso/carmine.clj#L26

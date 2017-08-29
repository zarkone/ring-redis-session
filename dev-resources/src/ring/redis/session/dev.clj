(ns ring.redis.session.dev
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.pprint :refer [pprint print-table]]
            [clojure.reflect :refer [reflect]]
            [clojure.string :as string]
            [clojure.walk :refer [macroexpand-all]]
            [ring.redis.session :refer [redis-store]]
            [ring.redis.util :as util]
            [taoensso.carmine :as car :refer [wcar]]))

(defn make-session-store
  "Provide some sane default for local dev."
  ([]
    (make-session-store
      {:pool {}
       :spec {:host "127.0.0.1"
              :port 6379}}))
  ([conn]
    (make-session-store
      conn
      {:expire-secs 43200
       :reset-on-read true}))
  ([conn opts]
    (redis-store conn opts)))

(def default-connection (make-session-store))

(defmacro redis
  "With this macro we can do things like the following in the REPL (for
  querying Redis):

  ```clj
  => (redis 'ping)
  => (redis 'get \"testkey\")
  => (redis 'set \"foo\" \"bar\")
  ```

  (Note that the escaped strings are for the docstring, and not what you'd
  actually type in the REPL.)"
  [cmd-str & body]
  `(car/wcar default-connection
             ((resolve (symbol (str "car/" ~cmd-str))) ~@body)))

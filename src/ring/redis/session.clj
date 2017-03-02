(ns ring.redis.session
  "Redis session storage."
  (:require [clojure.tools.logging :as log]
            [ring.middleware.session.store :as api]
            [taoensso.carmine :as redis])
  (:import java.util.UUID))

;;; Utility functions

(defn new-session-key [prefix]
  (str prefix ":" (str (UUID/randomUUID))))

;;; Method implementations

(defn read-redis-session
  ""
  [this session-key]
  (let [conn (:redis-conn this)]
    (when session-key
      (log/debug "In read-session ...")
      (log/debug "\tsession-key:" session-key)
      (when-let [data (redis/wcar conn (redis/get session-key))]
        (if (and (:expiration this) (:reset-on-read this))
          (redis/wcar conn (redis/expire session-key (:expiration this))))
        (read-string data)))))

(defn write-redis-session
  ""
  [this old-session-key data]
  (let [conn (:redis-conn this)
        session-key (or old-session-key (new-session-key (:prefix this)))
        data-str (binding [*print-dup* true]
                   (print-str data))
        expiri (:expiration this)]
    (log/debug "In write-redis-session ...")
    (log/debug "\tsession-key:" session-key)
    (log/debug "\tdata-str:" data-str)
    (if expiri
      (redis/wcar conn (redis/setex session-key expiri data-str))
      (redis/wcar conn (redis/set session-key data-str)))
    session-key))

(defn delete-redis-session
  ""
  [this session-key]
  (redis/wcar (:redis-conn this) (redis/del session-key))
  nil)

;;; Protocol Implementation

(defrecord RedisStore [redis-conn prefix expiration reset-on-read])

(def store-behaviour {:read-session read-redis-session
                      :write-session write-redis-session
                      :delete-session delete-redis-session})

(extend RedisStore api/SessionStore store-behaviour)

;;; Constructor

(defn redis-store
  "Creates a redis-backed session storage engine."
  ([redis-conn]
     (redis-store redis-conn))
  ([redis-conn {:keys [prefix expire-secs reset-on-read]
                :or {prefix "session"
                     reset-on-read false}}]
     (log/debug "Creating Redis store ...")
     (->RedisStore redis-conn prefix expire-secs reset-on-read)))

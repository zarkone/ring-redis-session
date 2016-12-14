(ns clojusc.redis.session
  "Redis session storage."
  (:use ring.middleware.session.store)
  (:require [clojure.tools.logging :as log]
            [taoensso.carmine :as car :refer [wcar]])
  (:import java.util.UUID))

(defn new-session-key [prefix]
  (str prefix ":" (str (UUID/randomUUID))))

(deftype RedisStore [redis-conn prefix expiration reset-on-read]
  SessionStore
  (read-session [_ session-key]
    (when session-key
      (log/debug "In read-session, 'session-key':" session-key)
      (when-let [data (wcar redis-conn (car/get session-key))]
        (if (and expiration reset-on-read)
          (wcar redis-conn (car/expire session-key expiration)))
        (read-string data))))
  (write-session [_ session-key data]
    (let [session-key (or session-key (new-session-key prefix))
          data-str (binding [*print-dup* true]
                     (print-str data))]
      (log/debug "In write-session, 'session-key':" session-key)
      (log/debug "In write-session, 'data-str':" data-str)
      (if expiration
        (wcar redis-conn (car/setex session-key expiration data-str))
        (wcar redis-conn (car/set session-key data-str)))
      session-key))
  (delete-session [_ session-key]
    (wcar redis-conn (car/del session-key))
    nil))

(defn redis-store
  "Creates a redis-backed session storage engine."
  ([redis-conn]
     (redis-store redis-conn {}))
  ([redis-conn {:keys [prefix expire-secs reset-on-read]
                :or {prefix "session"
                     reset-on-read false}}]
     (log/debug "Creating Redis store ...")
     (RedisStore. redis-conn prefix expire-secs reset-on-read)))

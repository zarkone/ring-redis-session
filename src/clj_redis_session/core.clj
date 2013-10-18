(ns clj-redis-session.core
  "Redis session storage."
  (:use ring.middleware.session.store)
  (:require [taoensso.carmine :as car :refer [wcar]])
  (:import java.util.UUID))

(defn new-session-key [prefix]
  (str prefix ":" (str (UUID/randomUUID))))

(deftype RedisStore [redis-conn prefix expiration]
  SessionStore
  (read-session [_ session-key]
    (when session-key
      (when-let [data (wcar redis-conn (car/get session-key))]
        (read-string data))))
  (write-session [_ session-key data]
    (let [session-key (or session-key (new-session-key prefix))
          data-str (binding [*print-dup* true]
                     (print-str data))]
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
  ([redis-conn {:keys [prefix expire-secs]
                :or {prefix "session"}}]
     (RedisStore. redis-conn prefix expire-secs)))

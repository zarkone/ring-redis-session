(ns clj-redis-session.core
  "Redis session storage."
  (:use ring.middleware.session.store)
  (:require [taoensso.carmine :as car])
  (:import java.util.UUID))

(defn session-key [prefix]
  (str prefix ":" (str (UUID/randomUUID))))

(deftype RedisStore [redis-pool redis-spec prefix expiration]
  SessionStore
  (read-session [_ session-key]
    (when session-key
      (when-let [data (car/with-conn redis-pool redis-spec (car/get session-key))]
        (read-string data))))
  (write-session [_ session-key data]
    (let [session-key (or session-key (session-key prefix))
          data-str (binding [*print-dup* true]
                     (print-str data))]
      (if expiration
        (car/with-conn redis-pool redis-spec (car/setex session-key expiration data-str))
        (car/with-conn redis-pool redis-spec (car/set session-key data-str)))
      session-key))
  (delete-session [_ session-key]
    (car/with-conn redis-pool redis-spec (car/del session-key))
    nil))

(defn redis-store
  "Creates a redis-backed session storage engine."
  [redis-pool redis-spec
   & {:keys [prefix expire-secs]
      :or {prefix "session"}}]
  (RedisStore. redis-pool redis-spec prefix expire-secs))

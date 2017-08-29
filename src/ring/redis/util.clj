(ns ring.redis.util
  "Redis session storage."
  (:require [clojure.pprint :as pprint])
  (:import java.util.UUID))

(defn new-session-key [prefix]
  (str prefix ":" (str (UUID/randomUUID))))

(defn log-pprint
  "A pretty-print function suitable for use with
  `clojure.tools.logging` functions."
  [& args]
  (str "\n"
    (with-out-str
      (apply pprint/pprint args))))
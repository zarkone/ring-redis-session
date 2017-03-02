(defproject clojusc/clj-redis-session "3.0.0-SNAPSHOT"
  :url "https://github.com/clojusc/clj-redis-session"
  :description "Redis-backed Clojure/Ring session store"
  :license
    {:name "Eclipse Public License"
     :url "https://opensource.org/licenses/EPL-1.0"}
  :dependencies [[com.taoensso/carmine "2.15.1"]
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [ring/ring-core "1.6.0-beta6"]]
  :profiles {
    :uber {
      :aot :all}
    :1.5 {
      :dependencies [
        [org.clojure/clojure "1.5.0"]
        [medley "0.6.0" :exclusions [org.clojure/clojure]]]}
    :1.6 {
      :dependencies [
        [org.clojure/clojure "1.6.0"]
        [medley "0.6.0" :exclusions [org.clojure/clojure]]]}
    :1.7 {
      :dependencies [[org.clojure/clojure "1.7.0"]]}
    :1.8 {
      :dependencies [[org.clojure/clojure "1.8.0"]]}
    :1.9 {
      :dependencies [[org.clojure/clojure "1.9.0-alpha14"]]}})

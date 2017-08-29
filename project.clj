(defproject clojusc/ring-redis-session "3.2.0-SNAPSHOT"
  :url "https://github.com/clojusc/ring-redis-session"
  :description "Redis-backed Clojure/Ring session store"
  :license
    {:name "Eclipse Public License"
     :url "https://opensource.org/licenses/EPL-1.0"}
  :dependencies [
    [com.taoensso/carmine "2.16.0"]
    [org.clojure/clojure "1.8.0"]
    [org.clojure/tools.logging "0.4.0"]
    [ring/ring-core "1.6.2"]]
  :profiles {
    :uber {
      :aot :all}
    :dev {
      :source-paths ["dev-resources/src"]
      :repl-options {
        :init-ns ring.redis.session.dev}}
    :test {
      :exclusions [org.clojure/clojure]
      :dependencies [
        [org.clojure/tools.namespace "0.2.11"]]
      :plugins [
        [jonase/eastwood "0.2.4"]
        [lein-ancient "0.6.10"]
        [lein-bikeshed "0.4.1"]
        [lein-kibit "0.1.5"]
        [lein-shell "0.5.0"]
        [venantius/yagni "0.1.4"]]}
    :1.5 {
      :dependencies [
        [org.clojure/clojure "1.5.0"]
        [medley "0.6.0" :exclusions [org.clojure/clojure]]]}
    :1.6 {
      :dependencies [
        [org.clojure/clojure "1.6.0"]
        [medley "0.6.0" :exclusions [org.clojure/clojure]]]}
    :1.7 {
      :dependencies [
        [org.clojure/clojure "1.7.0"]]}
    :1.8 {
      :dependencies [
        [org.clojure/clojure "1.8.0"]]}
    :1.9 {
      :dependencies [
        [org.clojure/clojure "1.9.0-alpha14"]]}
    :docs {
      :exclusions [org.clojure/clojure]
      :dependencies [
        [codox-theme-rdash "0.1.2"]]
      :plugins [
        [lein-codox "0.10.3"]
        [lein-simpleton "1.3.0"]]
      :codox {
        :project {
          :name "ring-redis-session"
          :description "Redis-backed Clojure/Ring session store"}
        :namespaces [#"^ring.redis.session\.(?!dev)"]
        :themes [:rdash]
        :output-path "docs/current"
        :doc-paths ["resources/docs"]
        :metadata {
          :doc/format :markdown
          :doc "Documentation forthcoming"}}}}
  :aliases {
    "check-deps" [
      "with-profile" "+test" "ancient" "check" "all"]
    "kibit" [
      "with-profile" "+test" "do"
        ["shell" "echo" "== Kibit =="]
        ["kibit"]]
    "outlaw" [
      "with-profile" "+test"
      "eastwood" "{:namespaces [:source-paths] :source-paths [\"src\"]}"]
    "lint" [
      "with-profile" "+test" "do"
        ["check"] ["kibit"] ["outlaw"]]})

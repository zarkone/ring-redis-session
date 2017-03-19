(defproject clojusc/ring-redis-session "3.1.0"
  :url "https://github.com/clojusc/ring-redis-session"
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
    :dev {
      :source-paths ["dev-resources/src"]
      :repl-options {
          :init-ns ring.redis.session.dev}}
    :test {
      :exclusions [org.clojure/clojure]
      :plugins
        [[lein-ancient "0.6.10"]
         [jonase/eastwood "0.2.3"]
         [lein-bikeshed "0.4.1"]
         [lein-kibit "0.1.3"]
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
      :dependencies [[org.clojure/clojure "1.7.0"]]}
    :1.8 {
      :dependencies [[org.clojure/clojure "1.8.0"]]}
    :1.9 {
      :dependencies [[org.clojure/clojure "1.9.0-alpha14"]]}
    :docs {
      :dependencies [[codox-theme-rdash "0.1.1"]]
      :plugins [[lein-codox "0.10.3"]
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
          :doc "Documentation forthcoming"}}}})

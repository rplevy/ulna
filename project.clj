(defproject ulna "0.0.1"
  :description "Annnounce on facebook what you are listening to."
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [noir "1.2.1"]
                 [clj-facebook-graph "0.4.0"]
                 [org.clojure/tools.logging "0.2.3"]
                 [clj-http "0.2.6"]]
  :dev-dependencies [[midje "1.3.0"]]
  :main ulna.server)
(ns ulna.core
  (:use clj-facebook-graph.auth)
  (:require [clj-facebook-graph [client :as client]]
            [clojure.string :as s]))

(defonce config (reduce
                 (fn [m config-entry]
                   (let [[k v] (s/split config-entry #"\s+")]
                     (assoc m (keyword k) v)))
                 {}
                 (s/split (slurp "resources/config/env.conf") #"\n")))

(defonce fb-auth {:access-token (:fb-auth config)})

#_(with-facebook-auth fb-auth
  (client/get "https://graph.facebook.com/me/friends"))
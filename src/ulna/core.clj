(ns ulna.core
  (:require [clj-facebook-graph [client :as client]]
            [clojure.string :as s]
            [clj-facebook-graph.auth :as auth]))

(defn env-vars []
  (reduce
   (fn [m env-var] (assoc m env-var (System/getenv (name env-var))))
   {} [:appsecret
       :apikey
       :title
       :baseuri]))

(defonce config (env-vars))

(defn friends [auth]
  (auth/with-facebook-auth {:access-token auth}
    (client/get "https://graph.facebook.com/me/friends")))

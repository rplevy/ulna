(ns ulna.core
  (:use clj-facebook-graph.auth)
  (:require [clj-facebook-graph [client :as client]]
            [clojure.string :as s]))

(defn env-vars []
  (reduce
   (fn [m env-var] (assoc m env-var (System/getenv (name env-var))))
   {} [:appsecret :apikey]))

(defonce config (env-vars))

(defn friends [auth]
  (with-facebook-auth {:access-token auth}
    (client/get "https://graph.facebook.com/me/friends")))

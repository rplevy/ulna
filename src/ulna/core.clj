(ns ulna.core
  (:require [clj-facebook-graph [client :as client]]
            [clojure.string :as s]
            [clj-facebook-graph.auth :as auth]
            [clojure.tools.logging :as log]))

(defn- env-vars []
  (reduce
   (fn [m env-var] (assoc m env-var (System/getenv (name env-var))))
   {} [:appsecret
       :apikey
       :title
       :baseuri]))

(defonce config (env-vars))

(defn request-access-token
  "when redirecting to an external app page, facebook provides a temporary
   auth code. This code in conjunction with the app secret, base uri, and
   api key may be submitted in a request to the graph api, to get the
   access-token, which enables interaction with the fb graph API."
  [auth-code]
  (let [response
        (client/get
         "https://graph.facebook.com/oauth/access_token"
         {:query-params
          {:client_id (:apikey config)
           :redirect_uri (str (:baseuri config) "/auth-code")
           :client_secret (:appsecret config)
           :code auth-code}})]
    (if (= (:status response) 200)
      (second (s/split (:body response) #"=|&"))
      (do (log/debug (format "Failed to get access token: " response))
          nil))))

(defn friends [auth]
  (auth/with-facebook-auth {:access-token auth}
    (client/get "https://graph.facebook.com/me/friends")))

(defn not-expired [auth]
  (when auth
    (try
      (auth/with-facebook-auth {:access-token auth}
        (= 200 (:status
                (client/get
                 (format "https://graph.facebook.com/%s" (:testuser config))))))
      (catch Exception e
        (log/debug (format "Failed to get test user: " (.getMessage e)))
        nil))))

(defn listening-to [auth listening]
  (auth/with-facebook-auth {:access-token auth}
    (client/post "https://graph.facebook.com/me/feed"
                 {:message (format "is listening to %s on %s"
                                   listening
                                   (:title config))})))
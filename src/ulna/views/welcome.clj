(ns ulna.views.welcome
  (:use [noir.core]
        [hiccup.core]
        [hiccup.page-helpers :only [include-css html5]]
        [ulna.core])
  (:require [ulna.views.common :as common]))

(defpartial main [& content]
  (html5
   [:head
    [:title "ulna"]
    (include-css "/css/reset.css")]
   [:body
    [:div#wrapper
     content]]))

(defpage "/" []
         (main
           [:p "Welcome to ulna"]))

(defpage [:post "/"] {signed-request :signed_request}
  signed-request)

(comment
  (defpage [:post "/"] [& args]  ;; see what post is being sent
    args))

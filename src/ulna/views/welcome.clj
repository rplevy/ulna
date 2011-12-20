(ns ulna.views.welcome
  (:use [noir.core]
        [hiccup.core]
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

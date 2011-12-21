(ns ulna.views.welcome
  (:use [noir.core]
        [hiccup.core]
        [ulna.core])
  (:require [ulna.views.common :as common]
            [noir [response :as response]
             [session :as session]
             [server :as server]]
            [hiccup.page-helpers :as hph]))

(defpartial login [app-id title]
  [:html
   [:head
    (hph/include-css "/css/welcome.css")
    [:title title]]
   [:body
    [:div {:class "main-div"}
    [:div {:id "fb-root"}]
     [:img {:src "/img/tfr.png"}]
     [:p]
     [:center
      [:a {:href
           (format "https://www.facebook.com/dialog/oauth?client_id=%s&redirect_uri=%s/auth-code"
                   (:apikey ulna.core/config)
                   (:baseuri ulna.core/config))}
       [:img {:src "/img/fb-button.png"}]]]]]])

(defpage "/" []
  (login (:apikey ulna.core/config)
         (:title ulna.core/config)))

(defpage "/auth-code" {code :code}
  (session/put! :access-token (ulna.core/request-access-token code))
  (response/redirect (str (:baseuri ulna.core/config) "/home")))

(defpage "/home" []
  (html [:html [:h1 "TODO: this is where you do things after authenticating"
                (ulna.core/friends (session/get :access-token))]]))

(comment
  ;; inspect what was posted
  (defpage [:post "/"] [& args]
    args))

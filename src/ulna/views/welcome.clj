(ns ulna.views.welcome
  (:use [noir.core]
        [hiccup.core]
        [ulna.core])
  (:require [ulna.views.common :as common]
            [noir
             [response :as response]
             [session :as session]
             [server :as server]]
            [hiccup
             [page-helpers :as hph]
             [form-helpers :as hfh]]))

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

(defpartial home [title]
  [:html
   [:head
    (hph/include-css "/css/welcome.css")
    [:title title]]
   [:body
    [:div {:class "main-div"}
    [:div {:id "fb-root"}]
     [:img {:src "/img/tfr.png"}]
     [:center
      [:div {:class "question"} "What are you listening to?"]
      (hfh/form-to
       [:post "/home"]
       [:div {:class "space"}
        [:input {:type "text" :name "listening-to" :id "listening-to"
                 :value nil :alt "Listening to..." :class "tf"}]]
       [:div {:class "space"}
        [:input {:type "submit" :value "OK" :class "submit"}]])]]]])
      
(defn authenticated []
  (response/redirect (str (:baseuri ulna.core/config) "/home")))
  
(defpage "/" []
  (if (ulna.core/not-expired (session/get :access-token))
    (authenticated)
    (login (:apikey ulna.core/config)
           (:title ulna.core/config))))

(defpage "/auth-code" {code :code}
  (session/put! :access-token (ulna.core/request-access-token code))
  (authenticated))

(defpage "/home" []
  (home (:title ulna.core/config)))
  
(defpage [:post "/home"] {song :song artist :artist}
  (ulna.core/listening-to (session/get :access-token) song artist)
  (home (:title ulna.core/config)))

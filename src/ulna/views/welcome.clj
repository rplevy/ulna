(ns ulna.views.welcome
  (:use [noir.core]
        [hiccup.core]
        [ulna.core])
  (:require [ulna.views.common :as common]
            [noir
             [response :as response]
             [cookies :as cookies]
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

(defpartial home [title & [listening]]
  [:html
   [:head
    (hph/include-css "/css/welcome.css")
    [:title title]]
   [:body
    [:div {:class "main-div"}
    [:div {:id "fb-root"}]
     [:img {:src "/img/tfr.png"}]
     [:center
      [:div {:class "question"}
       (when listening
         [:div {:class "space"}
          [:div {:class "listening-now"}
           "You are listening to " listening
           " on " title "."]])
       "What are you listening to" (when listening " now")  "?"]
      (hfh/form-to
       [:post "/home"]
       [:div {:class "space"}
        [:input {:type "text" :name "listening" :id "listening"
                 :value nil :autocomplete "off" :class "tf"}]]
       [:div {:class "space"}
        [:input {:type "submit" :value "OK" :class "submit"}]])]]]])
      
(defn authenticated []
  (response/redirect (str (:baseuri ulna.core/config) "/home")))
  
(defpage "/" []
  (if (ulna.core/not-expired (cookies/get :access-token))
    (authenticated)
    (login (:apikey ulna.core/config)
           (:title ulna.core/config))))

(defpage "/auth-code" {code :code}
  (cookies/put! :access-token (ulna.core/request-access-token code))
  (authenticated))

(defpage "/home" []
  (home (:title ulna.core/config)))
  
(defpage [:post "/home"] {listening :listening}
  (ulna.core/listening-to (cookies/get :access-token) listening)
  (home (:title ulna.core/config) listening))

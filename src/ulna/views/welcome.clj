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
    [:title title]
    [:link {:rel "icon" :type "image/png" :href "/img/favicon.ico"}]]
   [:body
    [:div {:class "main-div"}
    [:div {:id "fb-root"}]
     [:img {:src "/img/tfr.png"}]
     [:p]
     [:center
      [:a {:href ulna.core/oauth-code-uri}
       [:img {:src "/img/fb-button.png"}]]]]]])

(defpartial home [title & [listening]]
  [:html
   [:head
    (hph/include-css "/css/welcome.css")
    [:title title]
    [:link {:rel "icon" :type "image/png" :href "/img/favicon.ico"}]]
   [:body
    [:div {:class "main-div"}
    [:div {:id "fb-root"}]
     [:img {:src "/img/tfr.png"}]
     [:center
      [:div {:class "question"}
       (when listening
         [:div {:class "space"}
          [:div {:class "listening-now"}
           "You have been listening to " listening
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
  (let [post-pending (cookies/get :post-pending)]
    (when (seq post-pending)
      ;; if a session timed out and we saved a post in post-pending cookie,
      ;; then we did a round trip to facebook to renew, so here we post it.
      (ulna.core/listening-to (cookies/get :access-token) post-pending)
      (cookies/put! :post-pending ""))
    (home (:title ulna.core/config) post-pending)))
  
(defpage [:post "/home"] {listening :listening}
  (if (ulna.core/not-expired (cookies/get :access-token))
    (do
      (ulna.core/listening-to (cookies/get :access-token) listening)
      (home (:title ulna.core/config) listening))
    (do
      ;; if we have something to post but the access code expired, save the
      ;; post in a cookie and do the the round trip to facebook to renew.
      (cookies/put! :post-pending listening)
      (response/redirect ulna.core/oauth-code-uri))))


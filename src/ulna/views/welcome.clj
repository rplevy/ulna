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
    [:script
     (format
      "window.fbAsyncInit = function() { FB.init({
                                             appId      : '%s',
                                             status     : true,
                                             cookie     : true,
                                             xfbml      : true,
                                             oauth      : true,
                                             });
                                       };
   (function(d){
                var js, id = 'facebook-jssdk';
                if (d.getElementById(id)) {return;}
                js = d.createElement('script'); js.id = id; js.async = true;
                js.src = \"//connect.facebook.net/en_US/all.js\";
                d.getElementsByTagName('head')[0].appendChild(js);
                }(document));" app-id)]
     [:a {:href
          (format "https://www.facebook.com/dialog/oauth?client_id=%s&redirect_uri=%s/auth-code"
                  (:apikey ulna.core/config)
                  (:baseuri ulna.core/config))}
      [:img {:src "/img/tfr.png"}]]
     [:p]
     [:center
      [:div {:class "fb-login-button"}
      "Login with Facebook"]]]]])

(defpage "/auth-code" {code :code}
  (session/put! :access-token (ulna.core/request-access-token code))
  (response/redirect (str (:baseuri ulna.core/config) "/home")))

(defpage "/" []
  (login (:apikey ulna.core/config)
         (:title ulna.core/config)))

(defpage "/home" []
  (html [:html [:h1 "TODO: this is where you do things after authenticating"
                (ulna.core/friends (session/get :access-token))]]))

(comment
  (defpage [:post "/"] [& args]  ;; see what post is being sent
    args))

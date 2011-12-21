(ns ulna.views.welcome
  (:use [noir.core]
        [hiccup.core]
        [ulna.core])
  (:require [ulna.views.common :as common]
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
      "window.fbAsyncInit = function() {
                                    FB.init({
                                             appId      : '%s',
                                             status     : true,
                                             cookie     : true,
                                             xfbml      : true,
                                             oauth      : true,
                                             });
                                    };
   (function(d){
                var js, id = 'facebook-jssdk'; if (d.getElementById(id)) {return;}
                js = d.createElement('script'); js.id = id; js.async = true;
                js.src = \"//connect.facebook.net/en_US/all.js\";
                d.getElementsByTagName('head')[0].appendChild(js);
                }(document));" app-id)]
     [:img {:src "/img/tfr.png"}]
     [:p]
     [:center
      [:div {:class "fb-login-button"}
      "Login with Facebook"]]]]])

(defpage "/" {code :code}
  code)

(defpage [:post "/"] {signed-request :signed_request}
  signed-request)

(defpage "/" []
  (login (:apikey ulna.core/config)
         (:title ulna.core/config)))

(comment
  (defpage [:post "/"] [& args]  ;; see what post is being sent
    args))

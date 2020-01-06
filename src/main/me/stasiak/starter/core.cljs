(ns me.stasiak.starter.core
  (:require [reagent.core :as r]
            ["@material-ui/core" :refer [Button]]
            [datafrisk.core :as d]))

(defonce message (r/atom "Hello world!"))
(defonce store (r/atom {}))

(defn app []
  [:div [:> Button {:variant "contained" :color "primary"} @message]
   [d/DataFriskShell @store]])

(defn stop []
  (js/console.log "Stopping..."))

(defn start []
  (js/console.log "Starting...")
  (r/render [app]
    (.getElementById js/document "app")))

(defn ^:export init []
  (start))

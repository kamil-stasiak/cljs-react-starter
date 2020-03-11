(ns me.stasiak.starter.core
  (:require [reagent.core :as r]
            ["@material-ui/core" :refer [Button]]
            [datafrisk.core :as d]))

(enable-console-print!)

(comment
  (js/console.log "Hello world"))

(defonce store
  (r/atom
   {:message1 "Hello world"
    :message2 "Hi, trade"
    :checkboxes [{:id 0 :value false}
                 {:id 1 :value false}
                 {:id 2 :value false}
                 {:id 3 :value false}
                 {:id 4 :value false}]}))

(defn all-checked? [state]
  (->> (:checkboxes state)
       (map :value)
       (reduce #(and %1 %2))))

; (defn all-checked? [state] false)

(defn app []
  [:<>
   [d/DataFriskShell @store]
   [:div [:> Button {:variant "contained"
                     :color "primary"
                     :on-click #(swap! store assoc :show-message true)}
          (:message1 @store)]]

   (if (all-checked? @store) [:span "OK"])
   [:ul
    (for [{id :id value :value} (:checkboxes @store)]
      ^{:key id}
      [:li
       [:input
        {:type "checkbox"
         :checked value
         :on-change #(swap! store assoc-in [:checkboxes id :value] (not value))}]])]])

(comment
  (->> (:checkboxes @store)
       (map :value)
       (reduce #(and %1 %2))))

(comment
  (swap! store assoc-in [:checkboxes 2 :value] (not (get-in @store [:checkboxes 2 :value]))))

; Change message1 value
(comment
  (swap! store assoc :message1 "Hi company!"))

;
;
;
;
;
;
(defn stop []
  (js/console.log "Stopping..."))

(defn start []
  (js/console.log "Starting...")
  (r/render [app]
    (.getElementById js/document "app")))

(defn ^:export init [] (start))

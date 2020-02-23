(ns me.stasiak.starter.core
  (:require [reagent.core :as r]
            ; [devcards.core :as dc :include-macros true]
            [me.stasiak.starter.bmi :refer [bmi-component]]
            ["@material-ui/core" :refer [Button]]
            [datafrisk.core :as d]))
  ; (:require-macros [devcards.core :refer [defcard]]))


(defonce store (r/atom
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
          (:message2 @store)]]
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

(comment #(swap! store assoc-in [:checkboxes 2 :value] (not (get-in @store [:checkboxes 2 :value]))))

(defn stop []
  (js/console.log "Stopping..."))

(defn start []
  (js/console.log "Starting...")
  (r/render [app]
    (.getElementById js/document "app")))

(defn ^:export init []
  (start))

; (devcards.core/start-devcard-ui!)

; (defn Example [] [:div "Hello"])

; (defcard hello-example (dc/react-card (Example)))

; (defcard bmi-calculator                        ;; optional symbol name
;          "*Code taken from Reagent readme.*"          ;; optional markdown doc
;          (fn [data-atom _] (bmi-component data-atom)) ;; object of focus
;          {:height 180 :weight 80}                     ;; optional initial data
;          {:inspect-data true :history true})

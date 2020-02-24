(ns me.stasiak.starter.core
  (:require [reagent.core :as r]
            ; [devcards.core :as dc :include-macros true]
            [me.stasiak.starter.bmi :refer [bmi-component]]
            ["@material-ui/core" :refer [Button]]
            [datafrisk.core :as d]
            [clojure.spec.alpha :as spec]
            [clojure.test.check.generators :as gen]))
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

(comment
  (swap! store assoc-in [:checkboxes 2 :value] (not (get-in @store [:checkboxes 2 :value]))))

(defn stop []
  (js/console.log "Stopping..."))

(defn start []
  (js/console.log "Starting...")
  (r/render [app]
    (.getElementById js/document "app")))

(defn ^:export init []
  (start))

; SPECS
; Simple string spec
(spec/def ::id string?)
(spec/valid? ::id "Hello world")
(spec/valid? ::id 1)

; int or regex id
(def id-regex #"^[0-9]*$")
(spec/def ::id int?)
(spec/def ::id-regex
  (spec/and
   string?
   #(re-matches id-regex %)))
(spec/def ::id-types (spec/or :simple-id ::id
                              :regex-id ::id-regex))

(spec/valid? ::id 12345)
(spec/valid? ::id "12345")

(spec/def ::suit #{:club :diamond :heart :spade})
(spec/valid? ::suit :club)
(spec/valid? ::suit :fight-club)

(spec/explain ::suit :fight-club)

(spec/valid? ::id-regex "12345")
(spec/valid? ::id-regex "abc")

(spec/valid? ::id-types "12345")
(spec/valid? ::id-types 1)

(spec/conform ::id-types 10)
(spec/conform ::id-types "123123")
(spec/conform ::id-types "a21")

(spec/def ::big-even (spec/and int? even? #(> % 1000)))
(spec/valid? ::big-even :foo) ;; false
(spec/valid? ::big-even 10) ;; false
(spec/valid? ::big-even 2000) ;; true

(spec/def ::name-or-id (spec/or :name string?
                                :id   int?))
(spec/valid? ::name-or-id "abc") ;; true
(spec/valid? ::name-or-id 100) ;; true
(spec/valid? ::name-or-id :foo) ;; false

(spec/conform ::name-or-id "abc")
(spec/conform ::name-or-id 100)
(spec/explain ::name-or-id 100)
(spec/conform ::id-regex 100)

(spec/def ::name string?)
(spec/def ::age int?)
(spec/def ::skills list?)

(spec/def ::developer (spec/keys :req-un [::name ::age]
                                 :opt-un [::skills]))

(spec/valid? ::developer
             {:name "Brad" :age 24})

(spec/valid? ::developer
             {:name "Brad"})
; explain print to *out*
(spec/explain ::developer
              {:name "Brad"})

(spec/explain-data ::developer
                   {:name "Brad"})

; generators
(gen/generate (spec/gen int?))
(gen/generate (spec/gen ::developer))

; new developer
(spec/def ::age (spec/and int? #(> % 0) #(< % 120)))
(spec/def ::age int?)
(spec/def ::skills list?)
(spec/def ::developer (spec/keys :req-un [::name ::age]
                                 :opt-un [::skills]))
(gen/generate (spec/gen ::developer))

; (devcards.core/start-devcard-ui!)

; (defn Example [] [:div "Hello"])

; (defcard hello-example (dc/react-card (Example)))

; (defcard bmi-calculator                        ;; optional symbol name
;          "*Code taken from Reagent readme.*"          ;; optional markdown doc
;          (fn [data-atom _] (bmi-component data-atom)) ;; object of focus
;          {:height 180 :weight 80}                     ;; optional initial data
;          {:inspect-data true :history true})

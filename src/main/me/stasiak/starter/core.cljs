(ns me.stasiak.starter.core
  (:require [reagent.core :as r]
            ["@material-ui/core" :refer [Button]]
            [datafrisk.core :as d]
            [clojure.spec.alpha :as spec]
            [clojure.test.check.generators :as gen]))

(enable-console-print!)

(comment
  (js/console.log "Hello world"))

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

(defn ^:export init [] (start))

; SPECS
; Simple string spec

(comment
  (spec/def ::id int?)
  (spec/valid? ::id 12345)
  (spec/valid? ::id "12345")

 ; int or regex id
  (spec/def ::id-regex
    (spec/and
     string?
     #(re-matches #"^[0-9]*$" %)))

  (spec/valid? ::id-regex "12345")
  (spec/valid? ::id-regex "12345abc")
  (spec/valid? ::id-regex 12345)

  (spec/def ::id-types
    (spec/or :integer-id ::id
             :regex-id ::id-regex))

  (spec/valid? ::id-types 12345)
  (spec/valid? ::id-types "12345")
  (spec/valid? ::id-types "12a")

  (spec/def ::suit #{:club :diamond :heart :spade})
  (spec/valid? ::suit :club)
  (spec/valid? ::suit :fight-club)

 ; developer
  (spec/def ::name string?)
  (spec/def ::age int?)
  (spec/def ::skills list?)

  (spec/def ::developer
    (spec/keys :req-un [::name ::age]
               :opt-un [::skills]))

  (spec/valid? ::developer
               {:name "Brad" :age 24})

  (spec/valid? ::developer
               {:name "Brad"})

 ; explasin print to *out*
  (spec/explain ::developer
                {:name "Brad"})

  (spec/explain-data ::developer
                     {:name "Brad"})

 ; generators
  (gen/generate (spec/gen int?))
  (gen/generate
   (spec/gen ::developer))

 ; new developer
  (spec/def ::age
    (spec/and int?
              #(> % 0)
              #(< % 120)))

  (spec/def ::skills list?)
  (spec/def ::developer
    (spec/keys :req-un [::name ::age]
               :opt-un [::skills]))
  (gen/generate (spec/gen ::developer))
  (spec/explain-data ::developer
                     {:name "Brad" :age 130})
  (spec/explain ::developer
                {:name "Brad" :age 130}))
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;

(defmulti calculate-vat (fn [money] (:type money)))

(defmethod calculate-vat :basic [money]
  (* (:amount money) 1.22))
(defmethod calculate-vat :lower [money]
  (* (:amount money) 1.07))
(defmethod calculate-vat :vat-free [money]
  (* (:amount money) 1.00))

;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
(defmethod calculate-vat :basic-2011 [money]
  (* (:amount money) 1.23))
(defmethod calculate-vat :lower-2011 [money]
  (* (:amount money) 1.08))

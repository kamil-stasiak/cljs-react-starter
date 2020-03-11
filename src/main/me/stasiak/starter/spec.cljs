(ns me.stasiak.starter.spec
  (:require [clojure.spec.alpha :as spec]
            [clojure.test.check.generators :as gen]))
; SPECS
; basics
; Simple string spec
(spec/def ::id int?)
(spec/valid? ::id 12345)
(spec/valid? ::id "12345")

; regex id
(spec/def ::id-regex
  (spec/and
   string?
   #(re-matches #"^[0-9]*$" %)))

(spec/valid? ::id-regex "12345")
(spec/valid? ::id-regex "12345abc")
(spec/valid? ::id-regex 12345)

; composite
(spec/def ::id-types
  (spec/or :integer-id ::id
           :regex-id ::id-regex))

(spec/valid? ::id-types 12345)
(spec/valid? ::id-types "12345")
(spec/valid? ::id-types "12a")

(spec/def ::suit #{:club :diamond :heart :spade})
(spec/valid? ::suit :club)
(spec/valid? ::suit :fight-club)
;
;
;
;
;
;
;
;
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
              {:name "Brad" :age 130})
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

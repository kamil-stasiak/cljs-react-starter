(ns me.stasiak.starter.devcards.bmi
  (:require
   [me.stasiak.starter.bmi :refer [bmi-component]]
   [reagent.core :as r :include-macros true]
   [devcards.core :as devcards :include-macros true :refer [defcard reagent]]))

(def bmi-data (r/atom {:height 180 :weight 80}))

(defcard bmi-calculator
         "*Code taken from the Reagent readme.*"
         (devcards/reagent bmi-component)
         bmi-data
         {:inspect-data true :frame true :history true})

(defcard bmi-calculator
         "*Code taken from the Reagent readme. v2*"
         (reagent bmi-component)
         (r/atom {:height 150 :weight 70})
         {:inspect-data true :frame true :history true})

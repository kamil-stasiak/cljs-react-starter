(ns me.stasiak.starter.devcards.devcards
  (:require
   [me.stasiak.starter.devcards.bmi]
   [me.stasiak.starter.devcards.todo]
   [devcards.core :as devcards :include-macros true]))

(defn ^:export main [] (devcards.core/start-devcard-ui!))

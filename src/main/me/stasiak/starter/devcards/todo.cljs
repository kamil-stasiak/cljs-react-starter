(ns me.stasiak.starter.devcards.todo
  (:require
   [me.stasiak.starter.todo :refer [todo-app add-todo complete-all]]
   [reagent.core :as r :include-macros true]
   [devcards.core :as devcards :include-macros true :refer [defcard reagent]]))

(defonce todos (r/atom (sorted-map)))

(defonce init (do
                (add-todo "Rename Cloact to Reagent" todos)
                (add-todo "Add undo demo" todos)
                (add-todo "Make all rendering async" todos)
                (add-todo "Allow any arguments to component functions" todos)
                (complete-all true todos)))

(defcard todo
         "*TODO taken from the Reagent readme.*"
         (reagent todo-app)
         todos
         {:inspect-data true :frame true :history true})

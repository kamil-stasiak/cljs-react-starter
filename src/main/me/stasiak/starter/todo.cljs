(ns me.stasiak.starter.todo
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]))

(defonce counter (r/atom 0))

(defn add-todo [text todos]
  (let [id (swap! counter inc)]
    (swap! todos assoc id {:id id :title text :done false})))

(defn toggle [todos id] (swap! todos update-in [id :done] not))
(defn save [todos id title] (swap! todos assoc-in [id :title] title))
(defn delete [todos id] (swap! todos dissoc id))

(defn mmap [m f a] (->> m (f a) (into (empty m))))
(defn complete-all [v todos] (swap! todos mmap map #(assoc-in % [1 :done] v)))
(defn clear-done [todos] (swap! todos mmap remove #(get-in % [1 :done])))

(defn todo-input [{:keys [title on-save on-stop]}]
  (let [val (r/atom title)
        stop #(do (reset! val "")
                  (if on-stop (on-stop)))
        save #(let [v (-> @val str clojure.string/trim)]
                (if-not (empty? v) (on-save v))
                (stop))]
    (fn [{:keys [id class placeholder]}]
      [:input {:type "text" :value @val
               :id id :class class :placeholder placeholder
               :on-blur save
               :on-change #(reset! val (-> % .-target .-value))
               :on-key-down #(case (.-which %)
                               13 (save)
                               27 (stop)
                               nil)}])))

(def todo-edit (with-meta todo-input
                          {:component-did-mount #(.focus (rdom/dom-node %))}))

(defn todo-stats [{:keys [filt active done]} todos]
  (let [props-for (fn [name]
                    {:class (if (= name @filt) "selected")
                     :on-click #(reset! filt name)})]
    [:div
     [:span#todo-count
      [:strong active] " " (case active 1 "item" "items") " left"]
     [:ul#filters
      [:li [:a (props-for :all) "All"]]
      [:li [:a (props-for :active) "Active"]]
      [:li [:a (props-for :done) "Completed"]]]
     (when (pos? done)
       [:button#clear-completed {:on-click #(clear-done todos)}
        "Clear completed " done])]))

(defn todo-item []
  (let [editing (r/atom false)]
    (fn [{:keys [id done title]} todos]
      [:li {:class (str (if done "completed ")
                        (if @editing "editing"))}
       [:div.view
        [:input.toggle {:type "checkbox" :checked done
                        :on-change #(toggle todos id)}]
        [:label {:on-double-click #(reset! editing true)}
         (str title "")]
        [:button.destroy {:on-click #(delete todos id)}]]
       (when @editing
         [todo-edit {:class "edit" :title title
                     :on-save #(save todos id %)
                     :on-stop #(reset! editing false)}])])))

(defn todo-app [props]
  (let [filt (r/atom :all)]
    (fn []
      (let [items (vals @props)
            done (->> items (filter :done) count)
            active (- (count items) done)]
        [:div
         [:section#todoapp
          [:header#header
           [:h1 "todos"]
           [todo-input {:id "new-todo"
                        :placeholder "What needs to be done?"
                        :on-save #(add-todo % props)}]]
          (when (-> items count pos?)
            [:div
             [:section#main
              [:input#toggle-all {:type "checkbox" :checked (zero? active)
                                  :on-change #(complete-all (pos? active) props)}]
              [:label {:for "toggle-all"} "Mark all as complete"]
              [:ul#todo-list
               (for [todo (filter (case @filt
                                    :active (complement :done)
                                    :done :done
                                    :all identity) items)]
                 ^{:key (:id todo)} [todo-item todo props])]]
             [:footer#footer
              [todo-stats {:active active :done done :filt filt} props]]])]
         [:footer#info
          [:p "Double-click to edit a todo"]]]))))

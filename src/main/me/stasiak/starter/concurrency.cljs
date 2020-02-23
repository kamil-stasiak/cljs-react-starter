(ns me.stasiak.starter.concurrency)

;; atoms
(def person (atom {:name "Kamil" :surname "Stasiak"}))

(reset! person {:url "google.com"})

(reset! person {:url "allegro.pl"})

(comment (str @person))

(add-watch person :watcher
           (fn [key atom old-state new-state]
             (prn "-- Atom Changed --")
             (prn "key" key)
             (prn "atom" atom)
             (prn "old-state" old-state)
             (prn "new-state" new-state)))

(conj '(:def) :abc)

(def players (atom ()))
(swap! players conj :player1)
(swap! players conj :player2)
(deref players)

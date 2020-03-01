(ns me.stasiak.starter.streams
  (:require [com.rpl.specter :refer
             [transform select filterer srange setval
              LAST ALL]]))

(map inc [1 2 3])
(mapv inc [1 2 3])

(first (.split (.replace (.toUpperCase "a b c d") "A" "X") " "))

(-> "a b c d"
    .toUpperCase
    (.replace ,,, "A" "X")
    (.split ,,, " ")
    first)

(def person
  {:name "Mark Volkmann"
   :address {:street "644 Glen Summit"
             :city "St. Charles"
             :state "Missouri"
             :zip 63304}
   :employer {:name "Object Computing, Inc."
              :address {:street "12140 Woodcrest Dr."
                        :city "Creve Coeur"
                        :state "Missouri"
                        :zip 63141}}})

(:city (:address (:employer person)))
(-> person :employer :address :city)
(get-in person [:employer :address :city])

(->> (range)
     (map (fn [x] (* x x)))
     (filter even?)
     (take 10)
     (reduce +))

(assoc {:a 1 :b 2} :a 3) ; => {:a 3, :b 2}
(dissoc {:a 1 :b 2} :a) ; => {:b 2}
(conj [1 2 3] 4) ; => [1 2 3 4]
(pop [1 2 3]) ; => [1 2]
(conj #{1 2 3} 4) ; => #{1 2 3 4}

; Increment the last odd number in a sequence
(transform [ALL :a even?]
           inc
           [{:a 1} {:a 2 :b 1} {:a 4}])

(transform [(filterer odd?) LAST]
           inc
           [2 1 3 6 7 4 8])

(transform [(srange 4 11) (filterer even?)]
           reverse
           [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15])

(setval [:a (srange 2 4)] [] {:a [1 2 3 4 5]})
;; => {:a [1 2 5]}

(setval (srange 2 2) [:A :A] '(1 2 3 4 5))
;; => (1 2 :A :A 3 4 5)

(transform [:a (srange 1 5)] reverse {:a [1 2 3 4 5 6]})
           ;; => {:a [1 5 4 3 2 6]}

(ns me.stasiak.starter.multimethod)

; strategy-factory
(defmulti calculate-vat (fn [money] (:type money)))
; concrete-strategy
(defmethod calculate-vat :basic [money]
  (* (:amount money) 1.22))
(defmethod calculate-vat :lower [money]
  (* (:amount money) 1.07))
(defmethod calculate-vat :vat-free [money]
  (* (:amount money) 1.00))

(+ 1 2)
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

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
(defmethod calculate-vat :default [money]
  (* (:amount money) 1.00))

(calculate-vat {:amount 100 :type :basic})
(calculate-vat {:amount 100})
(calculate-vat {:amount 100 :type :lower})
(calculate-vat {:amount 100 :type :vat-free})
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
(calculate-vat {:amount 100 :type :basic-2011})
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

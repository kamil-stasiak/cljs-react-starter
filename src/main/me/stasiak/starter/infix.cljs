(ns me.stasiak.starter.core
  (:require-macros [infix.macros :refer [infix]]))

;

(infix 3 + 5 * 8)
; => 43

(infix (3 + 5) * 8)
; => 64

(ns me.stasiak.starter.async
  (:require [clojure.core.async :as a
             :refer [>! <! go chan buffer close!
                     alts! timeout]]))

(defn printer [in name]
  (go (while true (println (str (<! in) "-from-printer-" name)))))

(def printer-chan (chan))
(printer printer-chan "1")

(go (>! printer-chan "Hi"))

;
; pipeline
;

(defn printer-in-out [in out name]
  (go (while true
        (let [message (<! in)]
          (println (str message "-from-printer-" name))
          (>! out message)))))

(def channel-in (chan))
(def channel-2 (chan))
(def channel-3 (chan))

(printer-in-out channel-in channel-2 "FIRST")
(printer-in-out channel-2 channel-3 "SECOND")
(printer channel-3 "LAST")

(go (>! channel-in "Hello world!"))

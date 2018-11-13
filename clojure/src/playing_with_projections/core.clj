(ns playing-with-projections.core
  (:require [clojure.data.json :as json]))

;; time helpers with Java 8 API
(def zoneId (java.time.ZoneId/systemDefault))
(defn parse-timestamp [str] (java.time.Instant/parse str))
(defn convert-to-datetime [instant] (java.time.LocalDateTime/ofInstant instant zoneId))

(defn enrich-datetime-info [event]
  (let [instant (parse-timestamp (get event "timestamp"))
        datetime (convert-to-datetime instant)]   
        (assoc event
          "instant" instant
          "datetime" datetime)))
  
;;loading json content
(defn parse-events-file [path]
  (->> (slurp path)
    (json/read-str)
    (map enrich-datetime-info)))

;; projections

(defn count-events [events] (count events))

(defn -main [& args]
  (let [events (parse-events-file (first args))]
        (println "Count events" (count-events events))))

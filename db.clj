(ns db
  (:require [clojure.string :as string]))


(defn process-customer [line]
  (def processed (string/split line #"\|"))
  (zipmap [:id :name :address :phone] processed))

(defn process-product [line]
  (def processed (string/split line #"\|"))
  (zipmap [:id :name :price] processed))

(defn process-sales [line]
  (def processed (string/split line #"\|"))
  (zipmap [:id :cust :prod :count] processed))

(defn load-and-parse [type file]
  (case type
    "c" (mapv process-customer (string/split-lines (slurp file)))
    "p" (mapv process-product (string/split-lines (slurp file)))
    "s" (mapv process-sales (string/split-lines (slurp file)))))
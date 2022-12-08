(ns db
  (:require [clojure.string :as string]))

;; methods functions for reading and parsing text files

(defn clean-line "Splits and trims" [line]
  (for [t (string/split line #"\|")]
    (string/trim t)))

;; methods to read each database entry

(defn process-customer "Creates customer DB" [line]
  (zipmap [:id :name :address :phone] (clean-line line)))

(defn process-product "Creates product DB" [line]
  (zipmap [:id :name :price] (clean-line line)))

(defn process-sales "Creates sales DB" [line]
  (zipmap [:id :cust :prod :count] (clean-line line)))

;; method to load and parse each database file

(defn load-and-parse "Loads each file into the specified database structure" [type file]
  (case type
    "c" (mapv process-customer (string/split-lines (slurp file)))
    "p" (mapv process-product (string/split-lines (slurp file)))
    "s" (mapv process-sales (string/split-lines (slurp file)))))

;; utility methods to find and filter out collections of maps 

(defn find-first "Finds the first collection according to the function" [f coll]
  (first (filter f coll)))

(defn get-find-first "Finds the first collection according to the function and gets the first keyword" [f coll keyw]
  (get (find-first f coll) keyw))

(defn get-find-first-int "Finds the first collection according to the function and gets the first keyword, parses to an int" [f coll keyw]
  (read-string (get-find-first f coll keyw)))

;; methods to associate customers and products from their databases, for sales display

(defn assoc-sales-cust "Associates customer ids in sales DB from customer DB" [index svs cvs]
  (if (>= index 0)
    (recur (dec index)
           (assoc-in svs [index :cust]
                     (get-find-first
                      #(= (get % :id) (get-in svs [index :cust]))
                      cvs
                      :name))
           cvs)
    svs))

(defn assoc-sales-prod "Associates customer ids in sales DB from products DB" [index svs pvs]
  (if (>= index 0)
    (recur (dec index)
           (assoc-in svs [index :prod]
                     (get-find-first
                      #(= (get % :id) (get-in svs [index :prod]))
                      pvs
                      :name))
           pvs)
    svs))

(defn assoc-sales-fully "Associates customer ids in sales DB from customer and products DB" [svs cvs pvs]
  (db/assoc-sales-prod (dec (count svs)) (db/assoc-sales-cust (dec (count svs)) svs cvs) pvs))

;; methods to calculate sales value and count

(defn sale-value "Calculates the sale's value" [sale pvs]
  (*
   (db/get-find-first-int
    (fn [kv] (= (get kv :name) (get sale :prod)))
    pvs
    :price)
   (read-string (get sale :count))))

(defn sale-count "Calculates the sale's count" [sale]
  (read-string (get sale :count)))
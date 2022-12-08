(ns menu
  (:require db))

;; printing utility methods

(defn print-char-custom "Takes in space count, size of string to accomodate and the string to repeat" [cnt out-string repeat-string]
  (apply str (repeat (- cnt (count out-string)) repeat-string)))

(defn print-spacing "Takes in space count and size of string to accomodate" [cnt out-string]
  (print-char-custom cnt out-string " "))

(defn print-space "Takes in space count" [cnt]
  (print-char-custom cnt "" " "))

(defn print-line "Takes in line count" [cnt]
  (print-char-custom cnt "" "-"))

;; printing database methods

(defn customer-table "Takes in customers DB" [cvs]
  (println)
  (println "ID" (print-space 1) "| Name" (print-space 12) "| Address" (print-space 15) "| Phone #")
  (println (print-line 62))
  (doseq [cm cvs]
    (let [id (get cm :id)] (print id (print-spacing 6 id)))
    (let [name (get cm :name)] (print name (print-spacing 20 name)))
    (let [address (get cm :address)] (print address (print-spacing 25 address)))
    (let [phone (get cm :phone)] (println phone (print-spacing 10 phone)))))

(defn product-table "Takes in products DB" [pvs]
  (println)
  (println "ID" (print-space 1) "| Name" (print-space 13) "| Price")
  (println (print-line 37))
  (doseq [pm pvs]
    (let [id (get pm :id)] (print id (print-spacing 6 id)))
    (let [name (get pm :name)] (print name (print-spacing 20 name)))
    (let [price (get pm :price)] (print (print-spacing 5 price) price "$"))
    (println)))

(defn sales-table "Takes in sales DB" [svs]
  (println)
  (println "ID" (print-space 1) "| Name" (print-space 13) "| Product" (print-space 10) "| Count")
  (println (print-line 55))
  (doseq [sm svs]
    (let [id (get sm :id)] (print id (print-spacing 6 id)))
    (let [cust (get sm :cust)] (print cust (print-spacing 20 cust)))
    (let [prod (get sm :prod)] (print prod (print-spacing 20 prod)))
    (let [count (get sm :count)] (print (print-spacing 4 count) count))
    (println)))

;; printing customer and product information methods

(defn customer-sales "Takes in sales and products DB" [svs pvs]
  (println)
  (print "Enter customer name for search: ")
  (flush)
  (read-line)
  (let [name (read-line)]
    (println "Total for" name "is:" (let [sales (filter #(= (get % :cust) name) svs)]
                                      (if (= (count sales) 1)
                                        (db/sale-value (first sales) pvs)
                                        (try (reduce #(+ (db/sale-value %1 pvs)
                                                         (db/sale-value %2 pvs))
                                                     sales)
                                             (catch clojure.lang.ArityException _ (int 0))))) "$")))

(defn product-sales "Takes in products DB" [svs]
  (println)
  (print "Enter product name for search: ")
  (flush)
  (read-line)
  (let [name (read-line)]
    (println "Total count for" name "is:" (let [sales (filter #(= (get % :prod) name) svs)]
                                            (if (= (count sales) 1)
                                              (db/sale-count (first sales))
                                              (try (reduce #(+ (db/sale-count %1)
                                                               (db/sale-count %2))
                                                           sales)
                                                   (catch clojure.lang.ArityException _ (int 0))))))))

;; generic menu printing methods

(defn select "Prints the menu" []
  (print "" "
*** Sales Menu ***

1. Display Customer Table
2. Display Product Table
3. Display Sales Table
4. Total Sales for Customer
5. Total Count for Product
6. Exit
            
Please enter an option:" "")
  (flush)
  (read))

(defn bye "Prints the bye-bye" [] (println "See you in a while... Crocodile."))

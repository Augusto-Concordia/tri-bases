(ns app
  (:require menu db))

;; loads and parses the databases

(def custDb (db/load-and-parse "c" "cust.txt"))
(def prodDb (db/load-and-parse "p" "prod.txt"))
(def rawSalesDb (db/load-and-parse "s" "sales.txt"))
(def salesDb (db/assoc-sales-fully rawSalesDb custDb prodDb))

;; main menu loop

(def choice (menu/select))

(while (not (= choice 6))
  (case choice
    1 (menu/customer-table custDb)
    2 (menu/product-table prodDb)
    3 (menu/sales-table salesDb)
    4 (menu/customer-sales salesDb prodDb)
    5 (menu/product-sales salesDb)
    (println "\nInvalid choice..."))
  (print "\nPress ENTER to continue...")
  (flush)
  (read-line)
  (if (not (or (= choice 4) (= choice 5))) (read-line) :default)
  (def choice (menu/select)))

;; exit

(menu/bye)
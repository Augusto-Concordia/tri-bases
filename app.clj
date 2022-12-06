(ns app
  (:require menu db))

(def custDb (db/load-and-parse "c" "cust.txt"))

(menu/customer-table custDb)

(def choice (menu/select))

(while (not (= choice 6))
  (def choice (menu/select)))

(menu/bye)
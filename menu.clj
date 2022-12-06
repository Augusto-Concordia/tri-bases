(ns menu)

(defn select []
  (print "" "
*** Sales Menu ***

1. Display Customer Table
2. Display Product Table
3. Display Sales Table
4. Total Sales for Customer
5. Total Count for Product
6. Exit
            
Please enter an option?" "")
  (flush)
  (read))

(defn customer-table [cvs]
  (for [cm cvs]
    (println cm)))

(defn bye [] (println "See you in a while... Crocodile."))

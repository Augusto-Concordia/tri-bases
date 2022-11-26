(ns app
  (:require [menu])
  (:require [db]))

(print "" "
*** Sales Menu ***

1. Display Customer Table
2. Display Product Table
3. Display Sales Table
4. Total Sales for Customer
5. Total Count for Product
6. Exit
            
Please enter an option?" "")

(def choice (read))
(println choice)
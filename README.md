# spring-webflux
CRUD con Spring Boot - Spring Webflux - Mysql

#Script DB
CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `price` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

#EndPoint de consulta:
  #Return all products:
  GET: http://localhost:8080/api/products/allproduct
  #Product query by Name
  GET: http://localhost:8080/api/products/Product 1
  #Create a product
  POST: http://localhost:8080/api/products/create
  Body: { "name":"Product 5", "price":"4298.68" }
  #Update a product
  PUT: http://localhost:8080/api/products/update/2
  Body: { "name":"Product 2", "price":"4298.30"}
  #Delete a product by id
  Delete: http://localhost:8080/api/products/delete/6
  #Consult a product by id
  GET: http://localhost:8080/api/products/product/1
  #Get a list of products by their id
  GET: http://localhost:8080/api/products/searchAll?ids=1,2,3


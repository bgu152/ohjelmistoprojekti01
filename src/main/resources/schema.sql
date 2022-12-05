SET FOREIGN_KEY_CHECKS= 0;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS manufacturer;
DROP TABLE IF EXISTS users;
CREATE TABLE manufacturer
(
   id INT (11) NOT NULL AUTO_INCREMENT,
   name VARCHAR (255) NOT NULL,
   PRIMARY KEY (id)
);
CREATE TABLE product
(
   id INT (11) NOT NULL AUTO_INCREMENT,
   name VARCHAR (255) NOT NULL,
   type VARCHAR (255),
   price FLOAT,
   manufacturer_id INT (11),
   PRIMARY KEY (id),
   FOREIGN KEY (manufacturer_id) REFERENCES manufacturer (id)
);
CREATE TABLE users
(
   id INT (11) NOT NULL AUTO_INCREMENT,
   username VARCHAR (255) NOT NULL,
   password VARCHAR (255) NOT NULL,
   role VARCHAR (255) NOT NULL,
   PRIMARY KEY (id)
);
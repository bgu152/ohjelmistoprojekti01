SET FOREIGN_KEY_CHECKS= 0;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS manufacturer;
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
   FOREIGN KEY (manufacturer_id) REFERENCES manufacturer(id)
);
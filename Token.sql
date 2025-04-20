-- for MySQL
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root';


CREATE TABLE token (
	id int AUTO_INCREMENT NOT NULL,
	token varchar(255) NOT NULL,
	expiryTime datetime(6) NOT NULL,
	isUsed bit NOT NULL,
	userId int NOT NULL,
PRIMARY KEY CLUSTERED 
(
	id ASC
))
use student_space;

--Drop table
DROP TABLE users; 

--Create table without constraints
CREATE TABLE users(
	username 	VARCHAR(25),
	password 	VARBINARY(16),
	id		INT
);

--Create PK constraints
ALTER TABLE users
	ADD CONSTRAINT users_username_pk PRIMARY KEY(username);

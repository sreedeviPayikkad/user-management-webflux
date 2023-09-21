DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS department;

CREATE TABLE department
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    location varchar(100)
);



CREATE TABLE users
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name   VARCHAR(100) NOT NULL,
    age    INTEGER,
    salary DECIMAL,
    departmentId BIGINT REFERENCES department(id)
);




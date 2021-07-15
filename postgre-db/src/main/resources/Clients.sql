CREATE TABLE clients(
    id SERIAL NOT NULL,
    lastname varchar(255),
    firstname varchar(255),
    middlename varchar(255),
    date_of_birth DATE,
    primary key (id)
);
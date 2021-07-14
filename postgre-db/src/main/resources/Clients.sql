CREATE TABLE clients(
    id SERIAL NOT NULL,
    lastname varchar(255),
    firstname varchar(255),
    middlename varchar(255),
    date_of_birth DATE,
    card_id int8,
    foreign key (card_id) REFERENCES cards,
    primary key (id)
);
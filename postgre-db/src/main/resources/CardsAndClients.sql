CREATE TABLE clients (
    id SERIAL NOT NULL,
    lastname varchar(255),
    firstname varchar(255),
    middlename varchar(255),
    date_of_birth DATE,
    primary key (id)
);
CREATE TABLE cards (
    id SERIAL NOT NULL,
    PAN varchar(16),
    Form_Factor varchar(20),
    Chip varchar(20),
    pincode varchar(4),
    client_id int8,
    primary key (id),
    foreign key (client_id) REFERENCES clients(id)
);
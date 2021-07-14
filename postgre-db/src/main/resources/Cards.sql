CREATE TABLE cards (
    id SERIAL NOT NULL,
    PAN varchar(16),
    Form_Factor varchar(20),
    Chip varchar(20),
    pincode varchar(4),
    primary key (id)
);
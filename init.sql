CREATE DATABASE contact_book;

CREATE TABLE users(
id SERIAL PRIMARY KEY,
login VARCHAR(40) NOT NULL UNIQUE,
password VARCHAR(40) NOT NULL,
date_born TIMESTAMP NOT NULL
);

CREATE TABLE contact_type(
id SERIAL PRIMARY KEY,
type VARCHAR (30) NOT NULL UNIQUE
);

INSERT INTO contact_type (type)
VALUES
('PHONE'),
('EMAIL');

CREATE TABLE contacts(
id SERIAL PRIMARY KEY,
contact_name VARCHAR(50) NOT NULL,
contact_type INT,
contact_value VARCHAR(50) NOT NULL,
user_id INT,
CONSTRAINT contacts_users_id_fk FOREIGN KEY(user_id) REFERENCES users (id),
CONSTRAINT contacts_contact_type_id_fk FOREIGN KEY(contact_type) REFERENCES contact_type (id)
);

CREATE DATABASE IF NOT EXISTS kennelDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE kennelDB;

CREATE TABLE clients (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nom VARCHAR(100) NOT NULL,
  prenom VARCHAR(100) NOT NULL,
  date_naissance DATE NOT NULL,
  pseudonyme VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE adresses (
  id INT AUTO_INCREMENT PRIMARY KEY,
  numero VARCHAR(10) NOT NULL,
  rue VARCHAR(150) NOT NULL,
  code_postal VARCHAR(10) NOT NULL,
  commune VARCHAR(100) NOT NULL
);

CREATE TABLE clients_adresses (
  client_id INT NOT NULL,
  adresse_id INT NOT NULL,
  PRIMARY KEY (client_id, adresse_id),
  FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE,
  FOREIGN KEY (adresse_id) REFERENCES adresses(id) ON DELETE CASCADE
);

CREATE TABLE chiens (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nom VARCHAR(100) NOT NULL,
  date_naissance DATE NOT NULL,
  race VARCHAR(100) NOT NULL,
  sterilise BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE chats (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nom VARCHAR(100) NOT NULL,
  date_naissance DATE NOT NULL,
  race VARCHAR(100) NOT NULL,
  sterilise BOOLEAN NOT NULL DEFAULT FALSE
);

INSERT INTO clients (nom, prenom, date_naissance, pseudonyme) VALUES
  ('Martin', 'Alice', '1990-04-12', 'alice_m'),
  ('Durand', 'Bruno', '1985-11-03', 'bruno85'),
  ('Petit',  'Chloé', '1998-07-21', 'chlo');

INSERT INTO adresses (numero, rue, code_postal, commune) VALUES
  ('12', 'rue des Lilas',     '75011', 'Paris'),
  ('4',  'avenue de la Gare', '69003', 'Lyon'),
  ('27', 'boulevard Victor Hugo', '33000', 'Bordeaux');

INSERT INTO clients_adresses (client_id, adresse_id) VALUES
  (1, 1), (1, 2), (2, 2), (3, 3);

INSERT INTO chiens (nom, date_naissance, race, sterilise) VALUES
  ('Rex',   '2019-05-02', 'Berger allemand', TRUE),
  ('Milou', '2021-09-15', 'Fox terrier',     FALSE),
  ('Idéfix','2020-01-30', 'Griffon',         TRUE);

INSERT INTO chats (nom, date_naissance, race, sterilise) VALUES
  ('Felix',  '2018-03-10', 'Européen', TRUE),
  ('Tigrou', '2022-06-25', 'Maine Coon', FALSE);

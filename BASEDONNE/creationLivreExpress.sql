-- Generated by Mocodo 4.2.12

CREATE DATABASE IF NOT EXISTS `LibrairieJava`
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci
;



USE `LibrairieJava`;

DROP TABLE IF EXISTS DETAILCOMMANDE;
DROP TABLE IF EXISTS COMMANDE;
DROP TABLE IF EXISTS POSSEDER;
DROP TABLE IF EXISTS THEMES;
DROP TABLE IF EXISTS ECRIRE;
DROP TABLE IF EXISTS EDITER;

DROP TABLE IF EXISTS CLIENT;
DROP TABLE IF EXISTS VENDEUR;
DROP TABLE IF EXISTS ADMIN;

DROP TABLE IF EXISTS MAGASIN;
DROP TABLE IF EXISTS LIVRE;
DROP TABLE IF EXISTS EDITEUR;
DROP TABLE IF EXISTS AUTEUR;
DROP TABLE IF EXISTS CLASSIFICATION;

DROP TABLE IF EXISTS USER;


CREATE TABLE AUTEUR (
  PRIMARY KEY (idauteur),
  idauteur   varchar(11) NOT NULL,
  nomauteur  varchar(100),
  anneenais  int,
  anneedeces int
);

CREATE TABLE CLASSIFICATION (
  PRIMARY KEY (iddewey),
  iddewey  varchar(3) NOT NULL,
  nomclass varchar(50)
);

-- ajout des tables suivante ------
CREATE TABLE USER (
  PRIMARY KEY (idu),
  idu     INT NOT NULL,
  nom    VARCHAR(100),
  email  VARCHAR(100) UNIQUE,
  motDePasse VARCHAR(25),
  role VARCHAR(8)
);

CREATE TABLE VENDEUR (
  PRIMARY KEY (idve),
  prenomven  varchar(30),
  magasin varchar(30),
  idve INT NOT NULL

);

CREATE TABLE ADMIN (
  PRIMARY KEY (idad),
  prenomad varchar(100),
  idad INT NOT NULL

);

CREATE TABLE CLIENT (
  PRIMARY KEY (idcli),
  idcli      int NOT NULL,
  nomcli     varchar(50),
  prenomcli  varchar(30),
  adressecli varchar(100),
  codepostal varchar(5),
  villecli   varchar(100)
);
-- fin ajout et modification -----

CREATE TABLE COMMANDE (
  PRIMARY KEY (numcom),
  numcom  int NOT NULL,
  datecom date,
  enligne char(1),
  livraison char(1),
  idcli   int NOT NULL,
  idmag   VARCHAR(42) NOT NULL
);

CREATE TABLE DETAILCOMMANDE (
  PRIMARY KEY (numcom, numlig),
  numcom    int NOT NULL,
  numlig    int NOT NULL,
  qte       int,
  prixvente decimal(6,2),
  isbn      varchar(13) NOT NULL
);

CREATE TABLE ECRIRE (
  PRIMARY KEY (isbn, idauteur),
  isbn     varchar(13) NOT NULL,
  idauteur varchar(11) NOT NULL
);

CREATE TABLE EDITER (
  PRIMARY KEY (isbn, idedit),
  isbn   varchar(13) NOT NULL,
  idedit int NOT NULL
);

CREATE TABLE EDITEUR (
  PRIMARY KEY (idedit),
  idedit  int NOT NULL,
  nomedit varchar(100)
);

CREATE TABLE LIVRE (
  PRIMARY KEY (isbn),
  isbn      varchar(13) NOT NULL,
  titre     varchar(200),
  nbpages   int,
  datepubli int,
  prix      decimal(6,2)
);

CREATE TABLE MAGASIN (
  PRIMARY KEY (idmag),
  idmag    VARCHAR(42) NOT NULL,
  nommag   VARCHAR(42),
  villemag VARCHAR(42)
);

CREATE TABLE POSSEDER (
  PRIMARY KEY (idmag, isbn),
  idmag VARCHAR(42) NOT NULL,
  isbn  varchar(13) NOT NULL,
  qte   int
);

CREATE TABLE THEMES (
  PRIMARY KEY (isbn, iddewey),
  isbn    varchar(13) NOT NULL,
  iddewey varchar(3) NOT NULL
);

ALTER TABLE COMMANDE ADD FOREIGN KEY (idmag) REFERENCES MAGASIN (idmag);
ALTER TABLE COMMANDE ADD FOREIGN KEY (idcli) REFERENCES CLIENT (idcli);

ALTER TABLE DETAILCOMMANDE ADD FOREIGN KEY (isbn) REFERENCES LIVRE (isbn);
ALTER TABLE DETAILCOMMANDE ADD FOREIGN KEY (numcom) REFERENCES COMMANDE (numcom);

ALTER TABLE ECRIRE ADD FOREIGN KEY (idauteur) REFERENCES AUTEUR (idauteur);
ALTER TABLE ECRIRE ADD FOREIGN KEY (isbn) REFERENCES LIVRE (isbn);

ALTER TABLE EDITER ADD FOREIGN KEY (idedit) REFERENCES EDITEUR (idedit);
ALTER TABLE EDITER ADD FOREIGN KEY (isbn) REFERENCES LIVRE (isbn);

ALTER TABLE POSSEDER ADD FOREIGN KEY (isbn) REFERENCES LIVRE (isbn);
ALTER TABLE POSSEDER ADD FOREIGN KEY (idmag) REFERENCES MAGASIN (idmag);

ALTER TABLE THEMES ADD FOREIGN KEY (iddewey) REFERENCES CLASSIFICATION (iddewey);
ALTER TABLE THEMES ADD FOREIGN KEY (isbn) REFERENCES LIVRE (isbn);

-- ajout des dependance 

ALTER TABLE CLIENT ADD FOREIGN KEY (idcli) REFERENCES USER(idu);

ALTER TABLE VENDEUR ADD FOREIGN KEY (idve) REFERENCES USER(idu);
ALTER TABLE VENDEUR ADD FOREIGN KEY (magasin) REFERENCES MAGASIN(idmag);

ALTER TABLE ADMIN ADD FOREIGN KEY (idad) REFERENCES USER(idu);
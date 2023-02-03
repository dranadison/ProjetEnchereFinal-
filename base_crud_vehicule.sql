create database tpWs;
\c tpWs;
create sequence seq_vehicule;
create table Vehicule(idV)
CREATE TABLE Vehicule (idVehicule SERIAL NOT NULL, numero varchar(30) NOT NULL, marque varchar(255), PRIMARY KEY (idVehicule));
INSERT INTO Vehicule( numero, marque) VALUES ('3232TN', 'Toyota');
create database tpws;
\c tpws;

create table Vehicule(
    id SERIAL primary key,
    modele varchar(50),
    numeroImmatriculation varchar(20),
    dateAchat date
);

create table Kilometrage(
    id SERIAL primary key,
    idVehicule int,
    daty date,
    debutKm int,
    finKm int,
    foreign key (idVehicule) references vehicule (id)
);

create table Users(
    id SERIAL primary key,
    nom varchar(30),
    pwd varchar(30)
);

create table Token(
    id SERIAL primary key,
    idUsers int,
    valeur varchar(100),
    expiration timestamp,
    foreign key (idUsers) references Users(id)
);
--how and where to test the token
insert into Users values (1,'pwd1');
insert into Users values (2,'pwd2');
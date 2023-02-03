create database enchereWeb;
\c enchereweb;

create table Utilisateur(
    id SERIAL primary key,
    pseudo varchar(30) unique,
    mdp varchar(30),
    solde double precision default 0
);

insert into Utilisateur (pseudo,mdp) values ('Fanjava','njava');
insert into Utilisateur (pseudo,mdp) values ('Diams','diams');

create table Token(
    id SERIAL primary key,
    idUtilisateur int,
    valeur text,
    expiration timestamp,
    foreign key (idUtilisateur) references Utilisateur(id)
);

create table Categorie(
    id SERIAL primary key,
    intitule varchar(30)
);

insert into Categorie (intitule) values ('Bijou');

create table Enchere(
    id SERIAL primary key,
    idUtilisateur int,
    dateEnchere timestamp,
    descri varchar(50),
    duree double precision,
    idCategorie int,
    prixminimal double precision,
    commission double precision,
    etat int default 0,
    dernierEncheriseur int default 0,
    dernierMontant double precision default 0.0,
    foreign key (idUtilisateur) references Utilisateur(id),
    foreign key (idCategorie) references Categorie(id)
);

create table Offre(
    id SERIAL primary key,
    idEnchere int,
    idUtilisateur int,
    montant double precision,
    foreign key (idUtilisateur) references Utilisateur(id),
    foreign key (idEnchere) references Enchere(id)
);

create table Photo(
    id SERIAL primary key,
    idEnchere int,
    lien text,
    foreign key (idEnchere) references Enchere(id)
);

create table Recharge(
    id SERIAL primary key,
    idUtilisateur int,
    solde double precision,
    etat int,
    foreign key (idUtilisateur) references Utilisateur(id)
);
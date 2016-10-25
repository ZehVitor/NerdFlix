CREATE ROLE Admin WITH SUPERUSER LOGIN PASSWORD 'adminlocal';
CREATE USER Admin;

CREATE DATABASE NerdFlixDB OWNER Admin;

CREATE TABLE usuario (
    id_usuario SERIAL NOT NULL PRIMARY KEY,
    email varchar(255),
	idade integer NOT NULL,
    login varchar(255) UNIQUE NOT NULL,
    nome varchar(255),
    senha varchar(255) NOT NULL
);

CREATE TABLE filme (
    id_filme SERIAL NOT NULL,
    duracao integer NOT NULL,
    genero varchar(255),
    resolucao varchar(255),
    sinopse varchar(255),
    titulo varchar(255)
);

CREATE TABLE serie (
    id_filme SERIAL NOT NULL,
    duracao integer NOT NULL,
    genero varchar(255),
    resolucao varchar(255),
    sinopse varchar(255),
    episodio integer NOT NULL,
    temporada integer NOT NULL,
    titulo varchar(255)
);


INSERT INTO usuario (id_usuario, email, idade, login, nome, senha) VALUES (1, 'default@mail.com', 50, 'admin', 'Administrador', '123');
INSERT INTO usuario (id_usuario, email, idade, login, nome, senha) VALUES (2, 'Outro@mail.com', 12, 'ze', 'JVior', '123');
INSERT INTO usuario (id_usuario, email, idade, login, nome, senha) VALUES (3, 'Outro@mail.com', 20, 'ed', 'Edye', '321');
INSERT INTO usuario (id_usuario, email, idade, login, nome, senha) VALUES (4, 'outro@mail.com', 17, 'tay', 'Tayanne', '1234');

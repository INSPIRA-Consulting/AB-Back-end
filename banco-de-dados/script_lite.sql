DROP database IF EXISTS anjos_bolos;
CREATE database anjos_bolos;
use anjos_bolos;

CREATE TABLE ingrediente (
    idIngrediente INTEGER AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255),
    medida VARCHAR(255),
    preco FLOAT
);

CREATE TABLE produto (
    idProduto INTEGER AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255),
    valor_final FLOAT
);

CREATE TABLE receita (
    idIngrediente INTEGER NOT NULL,
    idProduto INTEGER NOT NULL,
    quantidade FLOAT,
    PRIMARY KEY (idIngrediente, idProduto),
    CONSTRAINT fkIngrediente FOREIGN KEY (idIngrediente) REFERENCES ingrediente(idIngrediente),
    CONSTRAINT fkProduto FOREIGN KEY (idProduto) REFERENCES produto(idProduto)
);

CREATE TABLE usuario (
    idUsuario SERIAL PRIMARY KEY,
    nome VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    senha VARCHAR(255),
    funcao ENUM('ADMINISTRADOR', 'ATENDENTE', 'GERENTE')
);

Select * from ingrediente;

-- Cria o usuário com senha (troque 'senha_segura' por uma senha forte)
-- CREATE USER 'admInspira'@'%' IDENTIFIED BY 'GJLMR2025';

-- Concede todas as permissões no banco anjos_bolos para o usuário criado
-- GRANT ALL PRIVILEGES ON anjos_bolos.* TO 'admInspira'@'%';

-- Aplica as mudanças de permissão
-- FLUSH PRIVILEGES;
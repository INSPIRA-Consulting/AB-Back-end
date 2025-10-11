-- Criação da Database
CREATE DATABASE IF NOT EXISTS anjos_bolos;
USE anjos_bolos;

-- Tabela Usuario
CREATE TABLE Usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(60) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    email VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(25) NOT NULL,
    telefone VARCHAR(15) NOT NULL UNIQUE,
    funcao VARCHAR(50) NOT NULL
);

-- Tabela Ingrediente
CREATE TABLE Ingrediente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(60) NOT NULL,
    custoMedida DECIMAL(12,10) NOT NULL
);

CREATE TABLE Categoria_Produto (
	id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(60) NOT NULL UNIQUE,
    descricao VARCHAR(60) NOT NULL
);

CREATE TABLE Produto (
	id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(60) NOT NULL UNIQUE,
    precoFinal DECIMAL(5,2) NOT NULL,
    fkCategoriaProduto INT,
    CONSTRAINT fk_categoria_produto FOREIGN KEY (fkCategoriaProduto) REFERENCES Categoria_Produto(id)
);

CREATE TABLE Tipo_Receita (
	id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(60) NOT NULL UNIQUE,
    descricao VARCHAR(60) NOT NULL
);

CREATE TABLE Cliente (
	id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(60) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    telefone VARCHAR(15) NOT NULL UNIQUE
);

CREATE TABLE Receita (
	id INT,
    nome VARCHAR(60) NOT NULL,
    fkIngrediente INT,
    quantidade FLOAT NOT NULL,
    unidadeMedida VARCHAR(20) NOT NULL,
    fkTipoReceita INT,
    CONSTRAINT fk_receita_ingrediente FOREIGN KEY (fkIngrediente) REFERENCES Ingrediente(id),
    CONSTRAINT fk_tipo_receita FOREIGN KEY (fkTipoReceita) REFERENCES Tipo_Receita(id),
    CONSTRAINT pk_receita PRIMARY KEY(id, fkIngrediente)
);

DROP TABLE Receita;

SELECT * FROM Ingrediente;
SELECT * FROM Usuario;
SELECT * FROM Cliente;
SELECT * FROM Categoria_Produto;
SELECT * FROM Produto;
SELECT * FROM Tipo_Receita;
SELECT * FROM Receita;

TRUNCATE Receita;
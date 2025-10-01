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

SELECT * FROM Ingrediente;
SELECT * FROM Usuario;
SELECT * FROM Categoria_Produto;
SELECT * FROM Produto;
SELECT * FROM Tipo_Receita;
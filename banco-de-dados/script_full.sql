DROP database IF EXISTS anjos_bolos;
CREATE database anjos_bolos;
use anjos_bolos;

-- 1. Cliente
CREATE TABLE Cliente (
  idCliente       INT           AUTO_INCREMENT PRIMARY KEY,
  cpf             CHAR(11)      NOT NULL,
  nome            VARCHAR(50)   NOT NULL,
  contato         CHAR(11)
);

-- 2. Usuário
CREATE TABLE Usuario (
  idUsuario       INT           AUTO_INCREMENT PRIMARY KEY,
  cpf             CHAR(11)      NOT NULL,
  nome            VARCHAR(50)   NOT NULL,
  email           VARCHAR(60)   NOT NULL,
  senha           VARCHAR(20)   NOT NULL,
  telefone        CHAR(11),
  funcao          ENUM(
                    'ADMIN',
                    'CONFEITEIRA',
                    'GERENTE',
                    'ATENDENTE'
                  ) NOT NULL
);

-- 3. Pedido
CREATE TABLE Pedido (
  idPedido            INT           AUTO_INCREMENT PRIMARY KEY,
  dataHoraPedido      DATETIME      NOT NULL,
  dataHoraRetirada    DATETIME,
  formaPagamento      ENUM(
                    'DINHEIRO',
                    'CARTAO',
                    'PIX'
                  ) NOT NULL,
  status              ENUM(
                    'PENDENTE',
                    'PREPARANDO',
                    'PRONTO',
                    'CANCELADO'
                  ) NOT NULL,
  observacao          VARCHAR(255),
  fkUsuarioResponsavel INT         NOT NULL,
  fkCliente           INT         NOT NULL,
  FOREIGN KEY (fkUsuarioResponsavel) REFERENCES Usuario(idUsuario),
  FOREIGN KEY (fkCliente) REFERENCES Cliente(idCliente)
);

-- 4. Categoria
CREATE TABLE Categoria (
  idCategoria     INT           AUTO_INCREMENT PRIMARY KEY,
  nome            VARCHAR(50)   NOT NULL,
  descricao       VARCHAR(255)
);

-- 5. Produto
CREATE TABLE Produto (
  idProduto       INT           AUTO_INCREMENT PRIMARY KEY,
  nome            VARCHAR(50)   NOT NULL,
  precoFinal      FLOAT         NOT NULL,
  fkCategoria     INT         NOT NULL,
  FOREIGN KEY (fkCategoria) REFERENCES Categoria(idCategoria)
);

-- 6. Itens do Pedido
CREATE TABLE Itens_Pedido (
  idItemPedido    INT           AUTO_INCREMENT PRIMARY KEY,
  fkPedido        INT         NOT NULL,
  fkProduto       INT         NOT NULL,
  quantidade      INT           NOT NULL,
  valorFinal      FLOAT         NOT NULL,
  peso            FLOAT,
  FOREIGN KEY (fkPedido)  REFERENCES Pedido(idPedido),
  FOREIGN KEY (fkProduto) REFERENCES Produto(idProduto)
);

-- 7. Unidade de Medida
CREATE TABLE Unidade_Medida (
  idUnidade_Medida INT           AUTO_INCREMENT PRIMARY KEY,
  unidadeMedida    VARCHAR(50)   NOT NULL,
  plural           VARCHAR(55),
  simbolo          VARCHAR(5),
  fracionada       ENUM('S','N') NOT NULL
);

-- 8. Tipo de Receita
CREATE TABLE Tipo_Receita (
  idTipoReceita    INT           AUTO_INCREMENT PRIMARY KEY,
  tipoReceita      VARCHAR(50)   NOT NULL,
  descricao        VARCHAR(255)
);

-- 9. Ingrediente
CREATE TABLE Ingrediente (
  idIngrediente    INT           AUTO_INCREMENT PRIMARY KEY,
  nome             VARCHAR(50)   NOT NULL,
  custoMedida      FLOAT         NOT NULL
);

-- 10. Receita
CREATE TABLE Receita (
  idReceita           INT           AUTO_INCREMENT PRIMARY KEY,
  fkIngrediente       INT         NOT NULL,
  qtdMedidaIngrediente FLOAT       NOT NULL,
  fkUnidadeMedida     INT         NOT NULL,
  fkTipoReceita       INT         NOT NULL,
  FOREIGN KEY (fkIngrediente)    REFERENCES Ingrediente(idIngrediente),
  FOREIGN KEY (fkUnidadeMedida)  REFERENCES Unidade_Medida(idUnidade_Medida),
  FOREIGN KEY (fkTipoReceita)    REFERENCES Tipo_Receita(idTipoReceita)
);

-- 11. Composição de Produto (Produto ⇄ Receita)
CREATE TABLE ComposicaoProduto (
  fkProduto    INT         NOT NULL,
  fkReceita    INT         NOT NULL,
  quantidade   FLOAT       NOT NULL,
  observacao   VARCHAR(255),
  PRIMARY KEY (fkProduto, fkReceita),
  FOREIGN KEY (fkProduto)  REFERENCES Produto(idProduto),
  FOREIGN KEY (fkReceita)  REFERENCES Receita(idReceita)
);

-- 12. Detalhamento de Produto (Itens_Pedido ⇄ Receita)
CREATE TABLE DetalhamentoProduto (
  idDetalhamento   INT           AUTO_INCREMENT PRIMARY KEY,
  fkItemPedido     INT         NOT NULL,
  observacao       VARCHAR(255),
  fkReceita        INT         NOT NULL,
  FOREIGN KEY (fkItemPedido) REFERENCES Itens_Pedido(idItemPedido),
  FOREIGN KEY (fkReceita)    REFERENCES Receita(idReceita)
);

-- Cria o usuário com senha (troque 'senha_segura' por uma senha forte)
CREATE USER 'admInspira'@'%' IDENTIFIED BY 'GJLMR2025';

-- Concede todas as permissões no banco anjos_bolos para o usuário criado
GRANT ALL PRIVILEGES ON anjos_bolos.* TO 'admInspira'@'%';

-- Aplica as mudanças de permissão
FLUSH PRIVILEGES;
CREATE DATABASE catdog;
use catdog;

CREATE TABLE raca (
  Id int NOT NULL AUTO_INCREMENT,
  Nome varchar(100) NOT NULL,
  DataHoraCriacao datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (Id)
);

CREATE TABLE fornecedor (
  Id int NOT NULL AUTO_INCREMENT,
  Nome varchar(150) NOT NULL,
  Cnpj varchar(14) NOT NULL,
  Ativo bit(1) NOT NULL,
  Cep varchar(8) NOT NULL,
  DataHoraCriacao datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  Estado varchar(2) NOT NULL,
  Cidade varchar(60) NOT NULL,
  Bairro varchar(60) NOT NULL,
  Logradouro varchar(80) NOT NULL,
  Numero varchar(15) NOT NULL,
  Complemento varchar(40) NOT NULL,
  PRIMARY KEY (Id)
);

CREATE TABLE marca (
	Id int NOT NULL AUTO_INCREMENT,
    Nome varchar(150) NOT NULL,
    IdFornecedor int NOt NULL,
    Ativo bit(1) NOT NULL,
    PRIMARY KEY (Id),
    KEY IX_Produto_IdFornecedor (IdFornecedor),
    CONSTRAINT FK_Produto_Fornecedor_IdFornecedor FOREIGN KEY (IdFornecedor) REFERENCES fornecedor (Id)
    );

CREATE TABLE categoria (
  Id int NOT NULL AUTO_INCREMENT,
  Nome varchar(50) NOT NULL,
  DataHoraCriacao datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`Id`)
);

CREATE TABLE produto (
  Id int NOT NULL AUTO_INCREMENT,
  Nome varchar(100) NOT NULL,
  Descricao varchar(450) NOT NULL,
  Especificacao varchar(450) NOT NULL,
  PrecoCompra decimal(18,2) NOT NULL,
  PrecoVenda decimal(18,2) NOT NULL,
  Quantidade int(11) NOT NULL,
  Ativo bit(1) NOT NULL,
  IdCategoria int NOT NULL,
  IdMarca int NOT NULL,
  TipoAnimal int DEFAULT NULL,
  DataHoraCriacao datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PorteAnimal int NOT NULL,
  PRIMARY KEY (Id),
  KEY IX_Produto_IdCategoria (IdCategoria),
  KEY IX_Produto_IdMarca (IdMarca),
  CONSTRAINT FK_Produto_Categoria_IdCategoria FOREIGN KEY (IdCategoria) REFERENCES categoria (Id),
  CONSTRAINT FK_Produto_Marca_IdMarca FOREIGN KEY (IdMarca) REFERENCES marca (Id)
);

CREATE TABLE imagem (
  Id int NOT NULL AUTO_INCREMENT,
  IdProduto int(11) NOT NULL,
  Nome varchar(150) NOT NULL,
  Caminho varchar(200) NOT NULL,
  Tipo varchar(100) DEFAULT NULL,
  DataHoraCriacao datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (Id),
  KEY IX_Imagem_IdProduto (IdProduto),
  CONSTRAINT FK_Imagem_Produto_IdProduto FOREIGN KEY (IdProduto) REFERENCES produto (Id)
);


CREATE TABLE cliente (
  Id int NOT NULL AUTO_INCREMENT,
  Nome varchar(80) NOT NULL,
  Sobrenome varchar(80) NOT NULL,
  Cpf varchar(11) NOT NULL,
  Email varchar(80) NOT NULL,
  Senha varchar(60) NOT NULL,
  DataNasc datetime NOT NULL,
  Telefone varchar(13) NOT NULL,
  Celular varchar(13) DEFAULT NULL,
  Sexo varchar(1) NOT NULL,
  Ativo bit(1) NOT NULL,
  DataHoraCriacao datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (Id),
  UNIQUE KEY Cpf (Cpf),
  UNIQUE KEY Email (Email)
);

CREATE TABLE endereco(
  Id int NOT NULL AUTO_INCREMENT primary KEY,
  nomeDestinatario varchar(100) NOT NULL,
  tipoEndereco varchar(100) NOT NULL,
  Cep varchar(8) NOT NULL,
  logradouro varchar(250) NOT NULL,
  Numero varchar(15) NOT NULL,
  Complemento varchar(40) NOT NULL,
  Estado varchar(2) NOT NULL,
  Cidade varchar(60) NOT NULL,
  Bairro varchar(60) NOT NULL,
  idCliente int NOT NULL,
  KEY IX_Cliente_IdCliente (idCliente),
  CONSTRAINT FK_Endereco_Cliente_idCliente FOREIGN KEY (idCliente) REFERENCES cliente (Id)
);

CREATE TABLE usuario (
  Id int(11) NOT NULL AUTO_INCREMENT,
  Nome varchar(80) NOT NULL,
  Cpf varchar(11) NOT NULL,
  Email varchar(80) NOT NULL,
  Permissao varchar(20) NOT NULL,
  Ativo bit(1) NOT NULL,
  Senha varchar(60) NOT NULL,
  DataHoraCriacao datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (Id),
  UNIQUE KEY Cpf (Cpf),
  UNIQUE KEY Email (Email)
);


CREATE TABLE pedido (
  Id int(11) NOT NULL AUTO_INCREMENT,
  Status int(11) NOT NULL,
  Data datetime NOT NULL,
  IdCliente int(11) NOT NULL,
  Parcela int(11) DEFAULT NULL,
  FormaPagamento int(11) NOT NULL,
  Desconto decimal(18,2) DEFAULT NULL,
  Frete decimal(18,2) DEFAULT NULL,
  CepEntrega varchar(8) DEFAULT NULL,
  NumeroCartao varchar(16) DEFAULT NULL,
  DataHoraCriacao datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (Id),
  KEY IdCliente (IdCliente),
  CONSTRAINT fk_pedido_cliente FOREIGN KEY (IdCliente) REFERENCES cliente (Id)
);


create table ItensPedido(
Id int(11) NOT NULL AUTO_INCREMENT,
IdProduto int(11) NOT NULL,
ValorUnitario decimal(18,2) DEFAULT NULL,
Quantidade int(11) NOT NULL,
IdPedido int(11) NOT NULL,
DataHoraCriacao datetime DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (Id),
  KEY IdProduto (IdProduto),
  KEY IdPedido (IdPedido),
  CONSTRAINT fk_itensPedido_pedido FOREIGN KEY (IdPedido) REFERENCES pedido (Id),
  CONSTRAINT fk_itensPedido_produto FOREIGN KEY (IdProduto) REFERENCES produto (Id)
);

create table CartaoCredito(
  Id int NOT NULL AUTO_INCREMENT primary key,
  Nome varchar(150) NOT NULL,
  Numero varchar(16) NOT NULL,
  Validade varchar(7) not null,
  CodigoSeguranca varchar(3) not null,
  IdCliente int not null,
  KEY IX_Cartao_Cliente (IdCliente),
  CONSTRAINT FK_Cartao_Cliente FOREIGN KEY (IdCliente) REFERENCES cliente (Id)
);
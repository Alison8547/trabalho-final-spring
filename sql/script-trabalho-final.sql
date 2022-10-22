CREATE USER DBC_MOVIES IDENTIFIED BY oracle;
GRANT CONNECT TO DBC_MOVIES;
GRANT CONNECT, RESOURCE, DBA TO DBC_MOVIES;
GRANT CREATE SESSION TO DBC_MOVIES;
GRANT DBA TO DBC_MOVIES;
GRANT CREATE VIEW, CREATE PROCEDURE, CREATE SEQUENCE to DBC_MOVIES;
GRANT UNLIMITED TABLESPACE TO DBC_MOVIES;
GRANT CREATE MATERIALIZED VIEW TO DBC_MOVIES;
GRANT CREATE TABLE TO DBC_MOVIES;
GRANT GLOBAL QUERY REWRITE TO DBC_MOVIES;
GRANT SELECT ANY TABLE TO DBC_MOVIES;

CREATE TABLE USUARIO (
  id_usuario NUMBER NOT NULL,
  nome VARCHAR2(255) NOT NULL,
  idade NUMBER NOT NULL,
  email VARCHAR2(255) NOT NULL,
  senha VARCHAR2(255) NOT NULL,
  tipo_usuario VARCHAR2(20) NOT NULL,
  PRIMARY KEY (id_usuario)
);

CREATE SEQUENCE SEQ_USUARIO
START WITH 1
INCREMENT BY 1
NOCACHE NOCYCLE;



CREATE TABLE ITEM_ENTRETENIMENTO (
  id_item_entretenimento NUMBER NOT NULL,
  nome VARCHAR2(255) NOT NULL,
  tipo VARCHAR2(255) NOT NULL,
  genero VARCHAR2(255) NOT NULL,
  sinopse VARCHAR2(1000),
  ano_lancamento VARCHAR2(10) NOT NULL,
  classificacao NUMBER NOT NULL,
  plataforma VARCHAR2(255) NOT NULL,
  duracao VARCHAR2(255) ,
  temporadas NUMBER,
  episodios NUMBER,
  PRIMARY KEY (id_item_entretenimento)
);

CREATE SEQUENCE SEQ_ITEM_ENTRETENIMENTO
START WITH 1
INCREMENT BY 1
NOCACHE NOCYCLE;

CREATE TABLE AVALIACAO (
  id_usuario NUMBER NOT NULL,
  id_item_entretenimento NUMBER NOT NULL,
  nota NUMBER NOT NULL,
  comentario VARCHAR2(1000) ,
  CONSTRAINT FK_id_usario FOREIGN KEY ( id_usuario ) REFERENCES USUARIO( id_usuario ),
  CONSTRAINT FK_id_item_entretenimento FOREIGN KEY ( id_item_entretenimento ) REFERENCES ITEM_ENTRETENIMENTO( id_item_entretenimento ),
  PRIMARY KEY (id_usuario, id_item_entretenimento)
);



CREATE TABLE ASSISTIDOS (
  id_usuario NUMBER NOT NULL,
  id_item_entretenimento NUMBER NOT NULL,
  CONSTRAINT FK_id_usario_assistidos FOREIGN KEY ( id_usuario ) REFERENCES USUARIO( id_usuario ),
  CONSTRAINT FK_id_item_entretenimento_as FOREIGN KEY ( id_item_entretenimento ) REFERENCES ITEM_ENTRETENIMENTO( id_item_entretenimento ),
  PRIMARY KEY (id_usuario, id_item_entretenimento)
);


CREATE TABLE INDICACAO (
  id_usuario NUMBER NOT NULL,
  nome_item VARCHAR2(255) NOT NULL,
  CONSTRAINT FK_id_usario_indicacao FOREIGN KEY ( id_usuario ) REFERENCES USUARIO( id_usuario ),
  PRIMARY KEY (id_usuario, nome_item)
);



--filmes

INSERT INTO ITEM_ENTRETENIMENTO(id_item_entretenimento , nome, tipo, genero, sinopse, ano_lancamento, classificacao, plataforma, duracao, temporadas, episodios)
VALUES (SEQ_ITEM_ENTRETENIMENTO.nextval, 'Duro de Matar', 'filme', 'acao', 'foda', '1988', 18, 'Netflix', '120', NULL, NULL);

INSERT INTO ITEM_ENTRETENIMENTO(id_item_entretenimento , nome, tipo, genero, sinopse, ano_lancamento, classificacao, plataforma, duracao, temporadas, episodios)
VALUES (SEQ_ITEM_ENTRETENIMENTO.nextval, 'Titanic', 'filme', 'Romance', 'triste', '1997', 12, 'Filmes online gratis HD', '155', NULL, NULL);

INSERT INTO ITEM_ENTRETENIMENTO(id_item_entretenimento , nome, tipo, genero, sinopse, ano_lancamento, classificacao, plataforma, duracao, temporadas, episodios)
VALUES (SEQ_ITEM_ENTRETENIMENTO.nextval, 'Senhor dos aneis', 'filme', 'Fantasia', 'muito foda', '2001', 12, 'Amazon prime', '220', NULL, NULL);

--series

INSERT INTO ITEM_ENTRETENIMENTO(id_item_entretenimento , nome, tipo, genero, sinopse, ano_lancamento, classificacao, plataforma, duracao, temporadas, episodios)
VALUES (SEQ_ITEM_ENTRETENIMENTO.nextval, 'Game of Thrones', 'serie', 'Drama', 'Um monte gente brigando pra ver quem vai ser Rei', '2011', 16, 'HBO MAX', NULL , 7, 73);

INSERT INTO ITEM_ENTRETENIMENTO(id_item_entretenimento , nome, tipo, genero, sinopse, ano_lancamento, classificacao, plataforma, duracao, temporadas, episodios)
VALUES (SEQ_ITEM_ENTRETENIMENTO.nextval, 'The Office', 'serie', 'Comedia', 'Mostra a rotina de um escritorio', '2005', 14, 'HBO MAX', NULL , 9, 120);

INSERT INTO ITEM_ENTRETENIMENTO(id_item_entretenimento , nome, tipo, genero, sinopse, ano_lancamento, classificacao, plataforma, duracao, temporadas, episodios)
VALUES (SEQ_ITEM_ENTRETENIMENTO.nextval, 'Breaking Bad', 'serie', 'Drama', 'Um professor de quimica, que descobriu que tem um cancêr. E está fazendo de tudo para deixar dinheiro pra sua família', '2008', 16, 'HBO MAX', NULL , 5, 62);

INSERT INTO ITEM_ENTRETENIMENTO(id_item_entretenimento , nome, tipo, genero, sinopse, ano_lancamento, classificacao, plataforma, duracao, temporadas, episodios)
VALUES (SEQ_ITEM_ENTRETENIMENTO.nextval, 'O Exorcista', 'serie', 'Terror', 'Dois homens muito diferentes dirigem seus esforços a um caso terrível de possessão demoníaca numa família local', '2019', 18, 'Netflix', NULL , 2, 12);

INSERT INTO ITEM_ENTRETENIMENTO(id_item_entretenimento , nome, tipo, genero, sinopse, ano_lancamento, classificacao, plataforma, duracao, temporadas, episodios)
VALUES (SEQ_ITEM_ENTRETENIMENTO.nextval, 'Supernatural', 'serie', 'Terror', 'Os irmãos Sam e Dean Winchester encaram cenários sinistros caçando monstros', '2005', 18, 'Netflix', NULL , 15, 200);

INSERT INTO ITEM_ENTRETENIMENTO(id_item_entretenimento , nome, tipo, genero, sinopse, ano_lancamento, classificacao, plataforma, duracao, temporadas, episodios)
VALUES (SEQ_ITEM_ENTRETENIMENTO.nextval, 'A Maldição da Mansão Bly', 'serie', 'Terror', 'Uma babá norte-americana chega a Bly Manor e começa a ver aparições em uma propriedade inglesa', '2020', 18, 'Netflix', NULL , 1, 6);
-- admins
-- admins

INSERT INTO USUARIO(id_usuario, nome, idade, email, senha, tipo_usuario)
VALUES (SEQ_USUARIO.nextval, 'gustavo', 21, 'gustavo@gmail.com', '1234', 'administrador');

INSERT INTO USUARIO(id_usuario, nome, idade, email, senha, tipo_usuario)
VALUES (SEQ_USUARIO.nextval, 'aliso', 21, 'aliso@gmail.com', '1234', 'administrador');

--clientes

INSERT INTO USUARIO(id_usuario, nome, idade, email, senha, tipo_usuario)
VALUES (SEQ_USUARIO.nextval, 'seu ze', 60, 'ze@gmail.com', '1234', 'cliente');

INSERT INTO ASSISTIDOS(id_usuario, id_item_entretenimento)
VALUES (1,1);

INSERT INTO AVALIACAO (id_usuario,id_item_entretenimento,nota,comentario)
VALUES (2,2,10,'Bom');

SELECT * FROM ITEM_ENTRETENIMENTO;

SELECT * FROM USUARIO;

SELECT * FROM AVALIACAO;

SELECT * FROM ASSISTIDOS;

SELECT * FROM INDICACAO;

SELECT * FROM ASSISTIDOS a 
INNER JOIN ITEM_ENTRETENIMENTO ie ON (ie.id_item_entretenimento = a.id_item_entretenimento)
WHERE id_usuario = 1;

SELECT AVG(nota) AS media FROM AVALIACAO 
WHERE id_item_entretenimento = 2;
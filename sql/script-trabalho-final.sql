CREATE TABLE USUARIO(
    id_usuario NUMBER NOT NULL,
    nome       VARCHAR2(255) NOT NULL,
    idade      NUMBER NOT NULL,
    email      VARCHAR2(255) UNIQUE NOT NULL,
    senha      VARCHAR2(512) NOT NULL,
    ativo      NUMBER NOT NULL,
    PRIMARY KEY (id_usuario)
);

CREATE SEQUENCE SEQ_USUARIO
    START WITH 1
    INCREMENT BY 1 NOCACHE NOCYCLE;


CREATE TABLE CARGO(
    id_cargo NUMBER NOT NULL,
    nome     varchar2(512) UNIQUE NOT NULL,
    PRIMARY KEY (id_cargo)
);

CREATE SEQUENCE SEQ_CARGO
    START WITH 1
    INCREMENT BY 1 NOCACHE
 NOCYCLE;

CREATE TABLE USUARIO_CARGO(
    id_usuario NUMBER NOT NULL,
    id_cargo   NUMBER NOT NULL,
    PRIMARY KEY (id_usuario, id_cargo),
    CONSTRAINT FK_usuario_cargo_cargo FOREIGN KEY (id_cargo) REFERENCES CARGO (id_cargo),
    CONSTRAINT FK_usuario_cargo_usuario FOREIGN KEY (id_usuario) REFERENCES USUARIO (id_usuario)
);

CREATE TABLE ITEM_ENTRETENIMENTO(
    id_item_entretenimento NUMBER NOT NULL,
    nome                   VARCHAR2(255) NOT NULL,
    tipo                   VARCHAR2(255) NOT NULL,
    genero                 VARCHAR2(255) NOT NULL,
    sinopse                VARCHAR2(1000),
    ano_lancamento         VARCHAR2(10) NOT NULL,
    classificacao          NUMBER NOT NULL,
    plataforma             VARCHAR2(255) NOT NULL,
    duracao                VARCHAR2(255),
    temporadas             NUMBER,
    episodios              NUMBER,
    PRIMARY KEY (id_item_entretenimento)
);

CREATE SEQUENCE SEQ_ITEM_ENTRETENIMENTO
    START WITH 1
    INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE TABLE AVALIACAO(
    id_usuario             NUMBER NOT NULL,
    id_item_entretenimento NUMBER NOT NULL,
    nota                   NUMBER NOT NULL,
    comentario             VARCHAR2(1000),
    CONSTRAINT FK_id_usario FOREIGN KEY (id_usuario) REFERENCES USUARIO (id_usuario),
    CONSTRAINT FK_id_item_entretenimento FOREIGN KEY (id_item_entretenimento) REFERENCES ITEM_ENTRETENIMENTO (id_item_entretenimento),
    PRIMARY KEY (id_usuario, id_item_entretenimento)
);



CREATE TABLE ASSISTIDOS(
    id_usuario             NUMBER NOT NULL,
    id_item_entretenimento NUMBER NOT NULL,
    CONSTRAINT FK_id_usario_assistidos FOREIGN KEY (id_usuario) REFERENCES USUARIO (id_usuario),
    CONSTRAINT FK_id_item_entretenimento_as FOREIGN KEY (id_item_entretenimento) REFERENCES ITEM_ENTRETENIMENTO (id_item_entretenimento),
    PRIMARY KEY (id_usuario, id_item_entretenimento)
);


CREATE TABLE INDICACAO(
    id_usuario NUMBER NOT NULL,
    nome_item  VARCHAR2(255) NOT NULL,
    CONSTRAINT FK_id_usario_indicacao FOREIGN KEY (id_usuario) REFERENCES USUARIO (id_usuario),
    PRIMARY KEY (id_usuario, nome_item)
);


--filmes

INSERT INTO ITEM_ENTRETENIMENTO(id_item_entretenimento, nome, tipo, genero, sinopse, ano_lancamento, classificacao,
                                plataforma, duracao, temporadas, episodios)
VALUES (SEQ_ITEM_ENTRETENIMENTO.nextval, 'Duro de Matar', 'filme', 'acao', 'foda', '1988', 18, 'Netflix', '120', NULL,
        NULL);

INSERT INTO ITEM_ENTRETENIMENTO(id_item_entretenimento, nome, tipo, genero, sinopse, ano_lancamento, classificacao,
                                plataforma, duracao, temporadas, episodios)
VALUES (SEQ_ITEM_ENTRETENIMENTO.nextval, 'Titanic', 'filme', 'Romance', 'triste', '1997', 12, 'Filmes online gratis HD',
        '155', NULL, NULL);

INSERT INTO ITEM_ENTRETENIMENTO(id_item_entretenimento, nome, tipo, genero, sinopse, ano_lancamento, classificacao,
                                plataforma, duracao, temporadas, episodios)
VALUES (SEQ_ITEM_ENTRETENIMENTO.nextval, 'Senhor dos aneis', 'filme', 'Fantasia', 'muito foda', '2001', 12,
        'Amazon prime', '220', NULL, NULL);

--series

INSERT INTO ITEM_ENTRETENIMENTO(id_item_entretenimento, nome, tipo, genero, sinopse, ano_lancamento, classificacao,
                                plataforma, duracao, temporadas, episodios)
VALUES (SEQ_ITEM_ENTRETENIMENTO.nextval, 'Game of Thrones', 'serie', 'Drama',
        'Um monte gente brigando pra ver quem vai ser Rei', '2011', 16, 'HBO MAX', NULL, 7, 73);

INSERT INTO ITEM_ENTRETENIMENTO(id_item_entretenimento, nome, tipo, genero, sinopse, ano_lancamento, classificacao,
                                plataforma, duracao, temporadas, episodios)
VALUES (SEQ_ITEM_ENTRETENIMENTO.nextval, 'The Office', 'serie', 'Comedia', 'Mostra a rotina de um escritorio', '2005',
        14, 'HBO MAX', NULL, 9, 120);

INSERT INTO ITEM_ENTRETENIMENTO(id_item_entretenimento, nome, tipo, genero, sinopse, ano_lancamento, classificacao,
                                plataforma, duracao, temporadas, episodios)
VALUES (SEQ_ITEM_ENTRETENIMENTO.nextval, 'Breaking Bad', 'serie', 'Drama',
        'Um professor de quimica, que descobriu que tem um cancêr. E está fazendo de tudo para deixar dinheiro pra sua família',
        '2008', 16, 'HBO MAX', NULL, 5, 62);

INSERT INTO ITEM_ENTRETENIMENTO(id_item_entretenimento, nome, tipo, genero, sinopse, ano_lancamento, classificacao,
                                plataforma, duracao, temporadas, episodios)
VALUES (SEQ_ITEM_ENTRETENIMENTO.nextval, 'O Exorcista', 'serie', 'Terror',
        'Dois homens muito diferentes dirigem seus esforços a um caso terrível de possessão demoníaca numa família local',
        '2019', 18, 'Netflix', NULL, 2, 12);

INSERT INTO ITEM_ENTRETENIMENTO(id_item_entretenimento, nome, tipo, genero, sinopse, ano_lancamento, classificacao,
                                plataforma, duracao, temporadas, episodios)
VALUES (SEQ_ITEM_ENTRETENIMENTO.nextval, 'Supernatural', 'serie', 'Terror',
        'Os irmãos Sam e Dean Winchester encaram cenários sinistros caçando monstros', '2005', 18, 'Netflix', NULL, 15,
        200);

INSERT INTO ITEM_ENTRETENIMENTO(id_item_entretenimento, nome, tipo, genero, sinopse, ano_lancamento, classificacao,
                                plataforma, duracao, temporadas, episodios)
VALUES (SEQ_ITEM_ENTRETENIMENTO.nextval, 'A Maldição da Mansão Bly', 'serie', 'Terror',
        'Uma babá norte-americana chega a Bly Manor e começa a ver aparições em uma propriedade inglesa', '2020', 18,
        'Netflix', NULL, 1, 6);
-- admins
INSERT INTO CARGO (ID_CARGO, NOME)
VALUES (seq_cargo.nextval, 'ROLE_ADMIN');

INSERT INTO CARGO (ID_CARGO, NOME)
VALUES (seq_cargo.nextval, 'ROLE_CLIENTE');

INSERT INTO CARGO (ID_CARGO, NOME)
VALUES (seq_cargo.nextval, 'ROLE_RECUPERACAO');
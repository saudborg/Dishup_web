--CRIA‚ÌO DA TABELA DE PAêSES
--A SIGLA DO PAIS ESTA DEFINIDA COMO UNICA
--A SIGLA DO PAIS TEM QUE TER TAMANHO IGUAL A 3
--A DESCRICAO DO PAIS NAO PODE SER EM BRANCO
CREATE TABLE pais(
	idPais INT NOT NULL,
	siglaPais CHAR(3) NOT NULL,
	nmPais VARCHAR(50) NOT NULL,
	CONSTRAINT pais_pk PRIMARY KEY(idPais),
	CONSTRAINT sigla_pais_unique UNIQUE(siglaPais),
	CONSTRAINT sigla_pais_check_size CHECK (CHAR_LENGTH(siglaPais) = 3),
	CONSTRAINT nm_pais_check_size CHECK (CHAR_LENGTH(nmPais) > 0)
);

--CRIA‚ÌO DA TABELA DE ESTADOS
--FILHA DA TABELA DE PAêS
--A SIGLA DO ESTADO ESTç DEFINIDA COMO UNICA
--A SIGLA DO ESTADO TEM QUE TER TAMANHO DE 2
--A DESCRICAO DO ESTADO NAO PODE SER BRANCA
CREATE TABLE estado(
	idEstado INT NOT NULL,
	siglaEstado CHAR(2) NOT NULL,
	nmEstado VARCHAR(50) NOT NULL,
	idPais INT NOT NULL,
	CONSTRAINT estado_pk PRIMARY KEY(idEstado),
	CONSTRAINT sigla_estado_unique UNIQUE(siglaEstado),
	CONSTRAINT pais_fk FOREIGN KEY(idPais) REFERENCES pais(idPais),
	CONSTRAINT sigla_estado_check_size CHECK (CHAR_LENGTH(siglaEstado) = 2),
	CONSTRAINT nm_estado_check_size CHECK (CHAR_LENGTH(nmEstado) > 0)
);

--CRIA‚ÌO DA TABELA DE CIDADES
--FILHA DA TABELA DE PçIS
--FILHA DA TABELA DE ESTADO
--A DESCRICAO DA CIDADE NAO PODE SER BRANCO
CREATE TABLE cidade(
	idCidade INT NOT NULL,
	nmCidade VARCHAR(50) NOT NULL,
	idPais INT NOT NULL,
	idEstado INT NOT NULL,
	CONSTRAINT cidade_pk PRIMARY KEY(idCidade),
	CONSTRAINT pais_fk FOREIGN KEY(idPais) REFERENCES pais(idPais),
	CONSTRAINT estado_fk FOREIGN KEY(idEstado) REFERENCES estado(idEstado),
	CONSTRAINT nm_cidade_check_size CHECK (CHAR_LENGTH(nmCidade) > 0)
);

--CRIACAO DA TABELA DE TIPO DE USUARIO
--ESTA TABELA INFORMA QUAL E O TIPO DO USUARIO DESSE CADASTRO, OU SEJA, SE ƒ CONSUMIDOR OU RESTAURANTE
--NOME DO TIPO E DESCRICAO DO TIPO NÌO PODEM SER BRANCOS
CREATE TABLE tipoUsuario(
       idTipoUsuario INT NOT NULL,
       nmTipoUsuario VARCHAR(50) NOT NULL,
       descTipoUsuario VARCHAR(100) NOT NULL,
       CONSTRAINT id_tipo_usuario_pk PRIMARY KEY(idTipoUsuario),
       CONSTRAINT nm_tipo_usuario_check_size CHECK (CHAR_LENGTH(nmTipoUsuario) > 0),
       CONSTRAINT desc_tipo_usuario_check_size CHECK (CHAR_LENGTH(descTipoUsuario) > 0)
);

--CRIA‚ÌO DA TABELA DE TIPO DE STATUS
--ESSA TABELA INFORMA QUAL ƒ O TIPO DE STATUS
--NOME DO TIPO E DESCRICAO DO TIPO NÌO PODEM SER BRANCOS
CREATE TABLE statusUsuario(
	idStatus INT NOT NULL,
	nmStatus VARCHAR(50) NOT NULL,
    descStatus VARCHAR(100) NOT NULL,
    CONSTRAINT id_status_pk PRIMARY KEY(idStatus),
    CONSTRAINT nm_status_check_size CHECK (CHAR_LENGTH(nmStatus) > 0),
    CONSTRAINT desc_status_check_size CHECK (CHAR_LENGTH(descStatus) > 0)
);
 
--CRIA‚ÌO DA TABELA DE USUARIO
--EMAIL ƒ VALIDADO A PARTIR DE UMA EXPRESSÌO REGULAR
--EMAIL DEVE SER UNICO
--PASSWORD NÌO DEVE SER EM BRANCO
--http://pgdocptbr.sourceforge.net/pg80/functions-matching.html#FUNCTIONS-POSIX-REGEXP
CREATE TABLE usuario(
       idUsuario INT NOT NULL,
       emailUsuario VARCHAR(100) NOT NULL,
       passwordUsuario VARCHAR(100) NOT NULL;
       idTipoUsuario INT NOT NULL,
       idStatus INT NOT NULL,
       CONSTRAINT usuario_pk PRIMARY KEY(idUsuario),
       CONSTRAINT email_check CHECK (emailUsuario SIMILAR TO '.+@.+\\.[a-z]+'),
       CONSTRAINT email_unique UNIQUE (emailUsuario),
       CONSTRAINT password_check CHECK(CHAR_LENGTH(passwordUsuario) > 0),
       CONSTRAINT id_tipo_usuario_fk FOREIGN KEY(idTipoUsuario) REFERENCES tipoUsuario(idTipoUsuario),
       CONSTRAINT id_status_fk FOREIGN KEY(idStatus) REFERENCES status(idStatus)
);
 
--CRIACAO DA TABELA DE ENDERECO DO USUARIO
--ESSA TABELA E FILHA DA TABELA DE USUARIO. PARA EXISTIR UM ENDERE‚O, PRECISA EXISTIR UM USUARIO
CREATE TABLE enderecoUsuario(
       idUsuario INT NOT NULL,
       CEP CHAR(8) NOT NULL,
       logradouro VARCHAR(100) NOT NULL,
       numero VARCHAR(10) NOT NULL,
       bairro VARCHAR(60) NOT NULL,
       idPais INT NOT NULL,
       idEstado INT NOT NULL,
       idCidade INT NOT NULL,
       CONSTRAINT usuario_endereco_pk PRIMARY KEY(idUsuario),
       CONSTRAINT usuario_endereco_fk FOREIGN KEY (idUsuario) REFERENCES usuario(idUsuario),
       CONSTRAINT id_pais_fk FOREIGN KEY(idPais) REFERENCES pais(idPais),
       CONSTRAINT id_estado_fk FOREIGN KEY(idEstado) REFERENCES estado(idEstado),
       CONSTRAINT id_cidade_fk FOREIGN KEY(idCidade) REFERENCES cidade(idCidade),
       CONSTRAINT cep_check_size CHECK(CHAR_LENGTH(CEP) = 8),
       CONSTRAINT logradouro_check_size CHECK(CHAR_LENGTH(logradouro) > 0),
       CONSTRAINT bairro_check_size CHECK(CHAR_LENGTH(bairro) > 0)
);
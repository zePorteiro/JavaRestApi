insert into cliente
    (nome, email, senha)
values
    ('Jhulia', 'jhulia@gmail.com', '$2a$10$td6xtMB1LW7Ez8pAgp96Ce1C4mzjnlACL6OPEfNQSyxtYM6e4YAcK');

insert into condominio
    (bairro, cep, cidade, logradouro, nome, numero, cliente_id)
values
    ('Jardim Vera Cruz(Zona Leste)','08310600', 'São Paulo'	,'Rua Marçal de Lemos',	'condo',	124,	1);

insert into porteiro
    (NOME, RG, SENHA, CONDOMINIO_ID)
values
    ('Morrigan', '231423153','123456',	1);

insert into apartamento
    (BLOCO, NUM_AP, VAZIO, CONDOMINIO_ID)
values
    ('A', 123, false, 1);

insert into entrega
(DATA_RECEBIMENTO_MORADOR, DATA_RECEBIMENTO_PORTEIRO, RECEBIDO, TIPO_ENTREGA, APARTAMENTO_ID, FK_PORTEIRO )
values
(null, '2024-12-05', FALSE, 'Sedex', 1, 1);

INSERT into morador
    (CEP  	,EMAIL,  	NOME,  	SENHA,  	TELEFONE,  	APARTAMENTO_ID,  	CONDOMINIO_ID)
    values
    ('08310600', 'teste@gmail.com', 'julia','$2a$10$td6xtMB1LW7Ez8pAgp96Ce1C4mzjnlACL6OPEfNQSyxtYM6e4YAcK', '11969269404', 1, 1);
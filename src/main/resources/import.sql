insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');
insert into cozinha (id, nome) values (3, 'Brasileira');
insert into cozinha (id, nome) values (4, 'Portuguesa');

insert into restaurante (nome, taxa_frete, cozinha_id) value ('Solar do Tâmega', 5.2, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) value ('Velho Sonho', 2.3, 2);
insert into restaurante (nome, taxa_frete, cozinha_id) value ('Taberna Mineira', 2.3, 3);
insert into restaurante (nome, taxa_frete, cozinha_id) value ('Casa Grande', 3.7, 3);
insert into restaurante (nome, taxa_frete, cozinha_id) value ('Cara Pintada', 0, 3);

insert into estado(nome) value('Rio de Janeiro');
insert into estado(nome) value('São Paulo');
insert into estado(nome) value('Minas Gerais');

insert into cidade(nome, estado_id) value ('Macaé', 1);
insert into cidade(nome, estado_id) value ('Maricá', 1);
insert into cidade(nome, estado_id) value ('Sorocaba', 2);


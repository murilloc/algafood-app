create table item_pedido
(
    id               bigint         not null auto_increment,
    data_atualizacao datetime       not null,
    data_criacao     datetime       not null,
    observacao       varchar(255),
    preco_total      decimal(19, 2) not null,
    preco_unitario   decimal(19, 2) not null,
    quantidade       integer        not null,
    pedido_id        bigint         not null,
    produto_id       bigint         not null,
    primary key (id)
) engine = InnoDB
  default charset = "utf8";

create table pedido
(
    id                   bigint          not null auto_increment,
    data_atualizacao     datetime(6),
    data_cancelamento    datetime(6),
    data_confirmacao     datetime(6),
    data_criacao         datetime(6)     not null,
    data_entrega         datetime(6),
    endereco_bairro      varchar(60)     not null,
    endereco_cep         varchar(9)      not null,
    endereco_complemento varchar(60),
    endereco_logradouro  varchar(100)    not null,
    endereco_numero      varchar(20)     not null,
    status_pedido        varchar(10)     not null,
    subtotal             decimal(19, 2)  not null,
    valor_total          decimal(19, 2)  not null,
    taxaFrete            decimal(19, 2) not null,
    endereco_cidade_id   bigint,
    restaurante_id       bigint          not null,
    cliente_id           bigint          not null,
    forma_pagamento_id   bigint          not null,
    primary key (id)
) engine = InnoDB
  default charset = "utf8";

alter table item_pedido
    add constraint FK6_itempedido_pedido foreign key (pedido_id) references pedido (id);

alter table item_pedido
    add constraint FK_itempedido_produto foreign key (produto_id) references produto (id);

alter table pedido
    add constraint FK_itempedido_cidade foreign key (endereco_cidade_id) references cidade (id);

alter table pedido
    add constraint FK_pedido_restaurante foreign key (restaurante_id) references restaurante (id);

alter table pedido
    add constraint FK_pedido_usuario foreign key (cliente_id) references usuario (id);
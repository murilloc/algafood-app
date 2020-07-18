create table foto_produto
(
    produto_id bigint not null,
    nome_arquivo varchar(150) not null,
    descricao varchar(150) null,
    content_type varchar(80) not null,
    tamanho int not null,
    constraint foto_produto_pk
        primary key (produto_id),
    constraint foto_produto_produto_id_fk
        foreign key (produto_id) references produto (id)
);
create table usuario_responsavel_restaurante
(
    usuario_id     bigint not null,
    restaurante_id bigint not null,
    primary key (usuario_id, restaurante_id)
) engine = InnoDB
  default charset = utf8;


alter table usuario_responsavel_restaurante
    add constraint fk_usuario_usuario_resp_restaurante
        foreign key (usuario_id) references usuario (id);

alter table usuario_responsavel_restaurante
    add constraint fk_restaurante_usuario_resp_restaurante
        foreign key (restaurante_id) references restaurante (id);
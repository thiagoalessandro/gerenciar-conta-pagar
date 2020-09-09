create table tbl_gasto
(
    id          bigserial not null
        constraint tbl_gasto_pkey
            primary key,
    periodo     char(7),
    tipo_gasto  varchar(20),
    valor       numeric(18, 2),
    descricao   varchar(255),
    cartao      varchar(50),
    responsavel varchar(50),
    projecao    char(1),
    cd_usu_atu  varchar(25),
    dh_atu      timestamp,
    id_sit      varchar(1),
    constraint tbl_gasto_periodo_tipo_gasto_cartao_responsavel_projecao UNIQUE (periodo, tipo_gasto, cartao, responsavel, projecao)
);

create index idx_tbl_gasto_periodo on tbl_gasto (periodo);

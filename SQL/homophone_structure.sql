create table homophone
(
    id           bigint auto_increment
        primary key,
    name         varchar(500) null,
    sheng_mu     varchar(50)  null,
    yun_mu       varchar(50)  null,
    sheng_diao   varchar(50)  null,
    origin_value varchar(50)  null
)
    charset = utf8;

create table origin_homophone
(
    id     bigint auto_increment
        primary key,
    phrase varchar(50)  null,
    notes  varchar(500) null,
    ph     varchar(50)  null
)
    charset = utf8;

create table sheng_diao_sort
(
    id           int auto_increment
        primary key,
    sheng_diao   varchar(50) null,
    sort         int         null,
    origin_value varchar(50) null
)
    charset = utf8;

create table sheng_mu_sort
(
    id          int auto_increment
        primary key,
    sheng_mu    varchar(50) null,
    sort        int         null,
    length_sort int         null
)
    charset = utf8;

create table yun_mu_sort
(
    id          int auto_increment
        primary key,
    yun_mu      varchar(50) null,
    sort        int         null,
    length_sort int         null
)
    charset = utf8;



create table auth_refresh_token
(
    id            varchar(64)  not null,
    account       varchar(64)  not null comment '用户ID',
    refresh_token varchar(255) not null comment '刷新令牌',
    expires_time  datetime     not null comment '过期时间',
    create_time   datetime     not null comment '创建时间',
    constraint auth_refresh_tokens_pk
        primary key (id)
)
comment '刷新令牌';
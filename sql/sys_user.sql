create table sys_user
(
    id          varchar(64)  not null
        primary key,
    account     varchar(50)  not null comment '账号',
    username    varchar(100) not null comment '名称',
    password    varchar(200) not null comment '密码',
    email       varchar(100) null comment '邮箱',
    gender      varchar(20)  null comment '性别码',
    phone       varchar(20)  null comment '手机号码',
    status      varchar(20)  not null comment '状态码',
    is_enable   TINYINT(1)   not null default 1 comment '是否启用码',
    create_by   varchar(64)  null comment '创建人',
    create_time datetime     not null comment '创建时间',
    update_by   varchar(64)  null comment '更新人',
    update_time datetime     null comment '更新时间'
)
    comment '用户表';

INSERT INTO demo.sys_user (id, account, username, password, email, gender, phone, status, create_by, create_time,
                           update_by, update_time)
VALUES ('1', 'admin', '系统管理员', '$2a$10$Risdqwk0hlWyughdJvIaCOnxbzK4gGOAsZ3ZF.QhHe7VW48cJedRC', null, '1', null,
        '1', null, '2025-11-08 23:56:20', null, '2025-11-12 19:18:51');

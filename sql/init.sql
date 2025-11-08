create table sys_user
(
    id               varchar(64)  not null
        primary key,
    account          varchar(50)  not null comment '账号',
    username         varchar(100) not null comment '名称',
    password         varchar(200) not null comment '密码',
    email            varchar(100) null comment '邮箱',
    gender_code      varchar(20) null comment '性别码',
    phone            varchar(20) null comment '手机号码',
    user_status_code varchar(20)  not null comment '状态码',
    create_by        varchar(64) null comment '创建人',
    create_time      datetime     not null comment '创建时间',
    update_by        varchar(64) null comment '更新人',
    update_time      datetime null comment '更新时间'
) comment '用户表';

INSERT INTO sys_user (id, account, username, password, email, gender_code, phone, user_status_code, create_by,
                      create_time, update_by, update_time)
VALUES ('1', 'admin', '系统管理员', 'admin123', null, '1', null, '1', null, '2025-11-08 23:56:20', null, null);

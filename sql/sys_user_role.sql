create table sys_user_role
(
    id          varchar(64) not null
        primary key,
    user_id     varchar(64) not null comment '用户 ID',
    role_id     varchar(64) not null comment '角色 ID',
    create_by   varchar(64) null comment '创建人',
    create_time datetime    not null comment '创建时间',
    update_by   varchar(64) null comment '更新人',
    update_time datetime    null comment '更新时间'
)
    comment '用户角色表';

INSERT INTO demo.sys_user_role (id, user_id, role_id, create_by, create_time, update_by, update_time) VALUES ('1', '1', '1', null, '2025-11-10 11:41:16', null, null);

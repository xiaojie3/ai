create table sys_role
(
    id            varchar(64)   not null
        primary key,
    code     varchar(50)   not null,
    name     varchar(100)  not null comment '角色名称',
    group_id varchar(64)   null comment '角色组ID',
    description   varchar(1000) not null comment '角色描述',
    enabled       varchar(1)    null comment '是否启用',
    create_by     varchar(64)   null comment '创建人',
    create_time   datetime      not null comment '创建时间',
    update_by     varchar(64)   null comment '更新人',
    update_time   datetime      null comment '更新时间'
)
    comment '角色表';

INSERT INTO demo.sys_role (id, code, name, group_id, description, enabled, create_by, create_time, update_by, update_time)
VALUES ('1', 'super', '超级管理员', '1', '超级管理员', '1', null, '2025-11-09 01:47:24', null, null);
INSERT INTO demo.sys_role (id, code, name, group_id, description, enabled, create_by, create_time, update_by, update_time)
VALUES ('2', 'admin', '管理员', '1', '管理员', '1', null, '2025-11-09 01:47:38', null, null);

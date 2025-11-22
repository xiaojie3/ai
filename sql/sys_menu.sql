create table sys_menu
(
    id          varchar(64)  not null comment '菜单ID' primary key,
    path        varchar(100) null null comment '路由路径',
    name        varchar(50)  null null comment '组件名',
    component   varchar(100) null comment '组件路径',
    create_by   varchar(64)  null comment '创建人',
    create_time datetime     not null comment '创建时间',
    update_by   varchar(64)  null comment '更新人',
    update_time datetime     null comment '更新时间'
) comment '菜单';

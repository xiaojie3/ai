create table sys_role_group
(
    id          varchar(64) not null comment '角色组ID' primary key,
    code        varchar(50) not null comment '代码' unique key,
    name        varchar(50) not null comment '名称',
    parent_id   varchar(64) default '0' comment '父级ID',
    create_by   varchar(64) null comment '创建人',
    create_time datetime    not null comment '创建时间',
    update_by   varchar(64) null comment '更新人',
    update_time datetime    null comment '更新时间'
) comment '角色组';

insert into sys_role_group(id, code, name, create_by, create_time, update_by, update_time)
values ('1', 'admin-group' ,'系统管理组', 'admin', '2022-01-01 00:00:00', null, null);
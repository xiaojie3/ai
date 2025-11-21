create table sys_user_status
(
    id           varchar(64)  not null
        primary key,
    code         varchar(10)  not null comment '字典码',
    name         varchar(50)  not null comment '字典名称',
    english_name varchar(100) null comment '英文名称',
    create_by    varchar(64)  null comment '创建人',
    create_time  datetime     not null comment '创建时间',
    update_by    varchar(64)  null comment '更新人',
    update_time  datetime     null comment '更新时间',
    constraint sys_user_status_uk_code
        unique (code)
)
    comment '用户状态';


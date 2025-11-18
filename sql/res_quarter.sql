create table res_quarter
(
    id           varchar(64)  not null
        primary key,
    quarter_code varchar(20)  not null comment '代码',
    quarter_name varchar(100) not null comment '名称',
    english_name varchar(200) null comment '英文名称',
    create_by    varchar(64)  null comment '创建人',
    create_time  datetime     null comment '创建时间',
    update_by    varchar(64)  null comment '更新人',
    update_time  datetime     null comment '更新时间',
    chinese_name varchar(100) not null,
    code         varchar(20)  not null
)
    comment '学季字典表';


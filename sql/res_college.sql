create table res_college
(
    id           varchar(64)  not null comment '学院 ID'
        primary key,
    college_code varchar(20)  null comment '学院代码',
    campus_id    varchar(64)  null comment '校区ID',
    college_name varchar(100) null comment '学院名称',
    college_abbr varchar(50)  null comment '学院简称',
    english_name varchar(200) null comment '英文名称',
    dean         varchar(50)  null comment '院长',
    landline     varchar(20)  null comment '座机',
    phone        varchar(20)  null comment '手机号',
    email        varchar(50)  null comment '邮箱',
    intro        varchar(500) null comment '简介',
    create_by    varchar(64)  null comment '创建人',
    create_time  datetime     not null comment '创建时间',
    update_by    varchar(64)  null comment '更新人',
    update_time  datetime     null comment '更新时间',
    campus_name  varchar(100) not null,
    chinese_abbr varchar(50)  null
)
    comment '学院表';


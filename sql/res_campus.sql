create table res_campus
(
    id           varchar(64)  not null comment '校区ID'
        primary key,
    campus_code  varchar(20)  null comment '校区代码',
    school_id    varchar(64)  null comment '学校ID',
    campus_name  varchar(100) null comment '校区名称',
    english_name varchar(200) null comment '英文名称',
    address      varchar(200) null comment '校区地址',
    principal    varchar(50)  null comment '校区管理人',
    landline     varchar(20)  null comment '固定电话',
    phone        varchar(20)  null comment '手机号',
    email        varchar(50)  null comment '邮箱',
    intro        varchar(500) null comment '简介',
    create_by    varchar(64)  null comment '创建人',
    create_time  datetime     not null comment '创建时间',
    update_by    varchar(64)  null comment '更新人',
    update_time  datetime     null comment '更新时间'
)
    comment '校区表';

INSERT INTO demo.res_campus (id, campus_code, school_id, campus_name, english_name, address, principal, landline, phone, email, intro, create_by, create_time, update_by, update_time) VALUES ('1', '00000', '1', '测试校区', 'Test Campus', null, null, null, null, null, null, 'admin', '2025-10-30 17:10:06', null, '2025-10-30 17:11:06');

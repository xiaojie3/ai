create table res_major
(
    id           varchar(64)   not null
        primary key,
    major_code   varchar(20)   not null comment '专业代码',
    college_id   varchar(64)   not null comment '学院 ID',
    major_name   varchar(100)  not null comment '专业中文名称',
    major_abbr   varchar(50)   null comment '专业中文简称',
    english_name varchar(200)  null comment '专业英文名称',
    degree       varchar(20)   null comment '授予学位',
    years        int           null comment '学制年限',
    intro        varchar(1000) null comment '简介',
    create_by    varchar(64)   null comment '创建人',
    create_time  datetime      null comment '创建时间',
    update_by    varchar(64)   null comment '更新人',
    update_time  datetime      null comment '更新时间',
    chinese_abbr varchar(50)   null,
    chinese_name varchar(100)  not null
)
    comment '专业表';


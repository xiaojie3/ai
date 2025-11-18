create table res_course
(
    id                   varchar(64)   not null comment '课程ID'
        primary key,
    course_code          varchar(20)   null comment '课程号',
    course_name          varchar(100)  null comment '课程名称',
    english_name         varchar(200)  null comment '英文名称',
    credit               decimal(5, 1) null comment '学分',
    total_hours          int           not null comment '总学时（理论+实践）',
    theory_hours         int           null comment '理论学时',
    practice_hours       int           null comment '实践学时',
    course_type_code     varchar(10)   null comment '课程类型字典码',
    teaching_mode_code   varchar(10)   null comment '上课方式字典码',
    assessment_type_code varchar(10)   null comment '考核方式字典码',
    description          varchar(200)  null comment '课程描述',
    create_by            varchar(64)   null comment '创建人',
    create_time          datetime      not null comment '创建时间',
    update_by            varchar(64)   null comment '更新人',
    update_time          datetime      null comment '更新时间',
    chinese_name         varchar(100)  not null,
    constraint uk_course_code
        unique (course_code) comment '课程编码唯一'
)
    comment '课程表';


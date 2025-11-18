create table res_college_course
(
    id          varchar(64) not null comment '学院开设课程ID'
        primary key,
    college_id  varchar(64) null comment '学院ID',
    course_id   varchar(64) null comment '课程ID',
    create_by   varchar(64) null comment '创建人',
    create_time datetime    null comment '创建时间'
)
    comment '学院开设课程';


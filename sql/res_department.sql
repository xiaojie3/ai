create table res_department
(
    id              varchar(64)  not null
        primary key,
    college_id      varchar(64)  not null comment '学院 ID',
    department_code varchar(10)  not null comment '部门编号',
    department_name varchar(100) not null comment '部门名称',
    create_by       varchar(64)  null comment '创建人',
    create_time     datetime     not null comment '创建时间',
    update_by       varchar(64)  null comment '更新人',
    update_time     datetime     null comment '更新时间'
)
    comment '部门表';

INSERT INTO demo.res_department (id, college_id, department_code, department_name, create_by, create_time, update_by, update_time) VALUES ('78933f315ae74eb08b2e4f36a6e6b7df', '1', '01', '教务处', '1', '2025-11-17 11:26:54', null, '2025-11-17 11:26:54');

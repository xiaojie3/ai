create table res_semester
(
    id            varchar(64)  not null
        primary key,
    start_year    int          not null comment '学期起始年份（如 2023 表示该学期从 2023 年开始）',
    end_year      int          not null comment '学期结束年份（如 2024 表示该学期至 2024 年结束，用于跨年度学期场景）',
    quarter_code  varchar(10)  not null comment '学季标识（1-4 分别代表春 / 夏 / 秋 / 冬，或按学校实际划分，如 1 = 秋季、2 = 春季）',
    semester_name varchar(50)  not null comment '学期中文名称（如 “2023-2024 学年第一学期”“2024 年夏季学期”）',
    english_name  varchar(100) not null comment '学期英文名称（如 “2023-2024 Academic Year, First Semester”）',
    start_date    date         not null comment '学期开始日期（精确到日，补充时间维度，如 2023-09-01）',
    end_date      date         not null comment '学期结束日期（精确到日，如 2024-01-15）',
    is_current    tinyint      not null comment '是否当前学期（1 = 是，0 = 否，用于快速查询当前活跃学期）',
    status        tinyint      not null comment '学期状态（0 = 未开始，1 = 进行中，2 = 已结束，便于业务逻辑判断）',
    create_by     varchar(64)  null comment '创建人',
    create_time   datetime     not null comment '创建时间',
    update_by     varchar(64)  null comment '更新人',
    update_time   datetime     null comment '更新时间',
    chinese_name  varchar(50)  not null
)
    comment '学期表';


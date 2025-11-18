create table res_school
(
    id                   varchar(64)   not null comment '主键'
        primary key,
    school_code          varchar(20)   not null comment '学校代码',
    school_name          varchar(100)  not null comment '中文名称',
    school_abbr          varchar(50)   null comment '中文简称',
    english_name         varchar(200)  null comment '英文名称',
    english_abbr         varchar(20)   null comment '英文简称',
    key_school           varchar(20)   null comment '重点学校',
    org_structure        varchar(100)  null comment '组织机构',
    project_211          varchar(20)   null comment '211 工程学校',
    province             varchar(50)   null comment '所属省',
    city                 varchar(50)   null comment '所属市',
    online_dept          varchar(100)  null comment '网络院系',
    anniversary_date     date          null comment '校庆日',
    competent_dept       varchar(100)  null comment '主管部门',
    graduate_school      varchar(20)   null comment '研究生院',
    school_type          varchar(50)   null comment '办学类型',
    school_nature        varchar(50)   null comment '学校性质',
    adult_edu_dept       varchar(100)  null comment '成教院系',
    party_secretary      varchar(50)   null comment '党委负责人',
    discipline_count     int           null comment '学科门类数',
    legal_representative varchar(50)   null comment '法定代表人',
    legal_cert_no        varchar(50)   null comment '法人证书号',
    sponsor              varchar(100)  null comment '学校举办者',
    principal            varchar(50)   null comment '校长',
    establish_date       date          null comment '建立年月',
    homepage_url         varchar(255)  null comment '主页地址',
    fax                  varchar(20)   null comment '传真电话',
    postal_code          varchar(10)   null comment '邮政编码',
    contact_phone        varchar(50)   null comment '联系电话',
    email                varchar(100)  null comment '电子邮箱',
    address              varchar(500)  null comment '地址',
    intro                varchar(1000) null comment '简介',
    create_by            varchar(64)   null comment '创建人',
    create_time          datetime      not null comment '创建时间',
    update_by            varchar(64)   null comment '更新人',
    update_time          datetime      null comment '更新时间'
)
    comment '学校表';

INSERT INTO demo.res_school (id, school_code, school_name, school_abbr, english_name, english_abbr, key_school, org_structure, project_211, province, city, online_dept, anniversary_date, competent_dept, graduate_school, school_type, school_nature, adult_edu_dept, party_secretary, discipline_count, legal_representative, legal_cert_no, sponsor, principal, establish_date, homepage_url, fax, postal_code, contact_phone, email, address, intro, create_by, create_time, update_by, update_time) VALUES ('1', '00000', '测试大学', '测试', 'Test University', 'TU', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '测试', null, 'admin', '2025-10-30 17:10:45', null, '2025-11-06 18:18:40');

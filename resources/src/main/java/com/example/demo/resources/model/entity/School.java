package com.example.demo.resources.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 学校表(ResSchool)表实体类
 *
 * @author robot
 * @since 2025-11-21 14:05:50
 */
@Data
@TableName("res_school")
public class School {
    //主键
    private String id;
    //学校代码
    private String schoolCode;
    //中文名称
    private String schoolName;
    //中文简称
    private String schoolAbbr;
    //英文名称
    private String englishName;
    //英文简称
    private String englishAbbr;
    //重点学校
    private String keySchool;
    //组织机构
    private String orgStructure;
    //211 工程学校
    private String project211;
    //所属省
    private String province;
    //所属市
    private String city;
    //网络院系
    private String onlineDept;
    //校庆日
    private LocalDate anniversaryDate;
    //主管部门
    private String competentDept;
    //研究生院
    private String graduateSchool;
    //办学类型
    private String schoolType;
    //学校性质
    private String schoolNature;
    //成教院系
    private String adultEduDept;
    //党委负责人
    private String partySecretary;
    //学科门类数
    private Integer disciplineCount;
    //法定代表人
    private String legalRepresentative;
    //法人证书号
    private String legalCertNo;
    //学校举办者
    private String sponsor;
    //校长
    private String principal;
    //建立年月
    private LocalDate establishDate;
    //主页地址
    private String homepageUrl;
    //传真电话
    private String fax;
    //邮政编码
    private String postalCode;
    //联系电话
    private String contactPhone;
    //电子邮箱
    private String email;
    //地址
    private String address;
    //简介
    private String intro;
    //创建人
    private String createBy;
    //创建时间
    private LocalDateTime createTime;
    //更新人
    private String updateBy;
    //更新时间
    private LocalDateTime updateTime;
}


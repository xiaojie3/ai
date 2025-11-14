package com.example.demo.resources.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 学校表
 *
 * @author robot
 * @since 2025-11-12 20:36:13
 */
@Entity
@Table(name = "res_school")
@Data
@EntityListeners(AuditingEntityListener.class) // 启用审计监听
public class School {
    @Id
    @Column(length = 64)
    private String id;

    // 学校代码
    @Column(name = "school_code")
    private String schoolCode;

    // 中文名称
    @Column(name = "school_name")
    private String schoolName;

    // 中文简称
    @Column(name = "school_abbr")
    private String schoolAbbr;

    // 英文名称
    @Column(name = "english_name")
    private String englishName;

    // 英文简称
    @Column(name = "english_abbr")
    private String englishAbbr;

    // 重点学校
    @Column(name = "key_school")
    private String keySchool;

    // 组织机构
    @Column(name = "org_structure")
    private String orgStructure;

    // 211 工程学校
    @Column(name = "project_211")
    private String project211;

    // 所属省
    @Column(name = "province")
    private String province;

    // 所属市
    @Column(name = "city")
    private String city;

    // 网络院系
    @Column(name = "online_dept")
    private String onlineDept;

    // 校庆日
    @Column(name = "anniversary_date")
    private LocalDate anniversaryDate;

    // 主管部门
    @Column(name = "competent_dept")
    private String competentDept;

    // 研究生院
    @Column(name = "graduate_school")
    private String graduateSchool;

    // 办学类型
    @Column(name = "school_type")
    private String schoolType;

    // 学校性质
    @Column(name = "school_nature")
    private String schoolNature;

    // 成教院系
    @Column(name = "adult_edu_dept")
    private String adultEduDept;

    // 党委负责人
    @Column(name = "party_secretary")
    private String partySecretary;

    // 学科门类数
    @Column(name = "discipline_count")
    private Integer disciplineCount;

    // 法定代表人
    @Column(name = "legal_representative")
    private String legalRepresentative;

    // 法人证书号
    @Column(name = "legal_cert_no")
    private String legalCertNo;

    // 学校举办者
    @Column(name = "sponsor")
    private String sponsor;

    // 校长
    @Column(name = "principal")
    private String principal;

    // 建立年月
    @Column(name = "establish_date")
    private LocalDate establishDate;

    // 主页地址
    @Column(name = "homepage_url")
    private String homepageUrl;

    // 传真电话
    @Column(name = "fax")
    private String fax;

    // 邮政编码
    @Column(name = "postal_code")
    private String postalCode;

    // 联系电话
    @Column(name = "contact_phone")
    private String contactPhone;

    // 电子邮箱
    @Column(name = "email")
    private String email;

    // 地址
    @Column(name = "address")
    private String address;

    // 简介
    @Column(name = "intro")
    private String intro;

    @CreatedDate
    @Column(name = "create_time", nullable = false, updatable = false) // 不可更新
    private LocalDateTime createTime;

    @LastModifiedDate
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

    @CreatedBy
    @Column(name = "create_by", length = 64, updatable = false)
    private String createBy;

    @LastModifiedBy
    @Column(name = "update_by", length = 64)
    private String updateBy;
}

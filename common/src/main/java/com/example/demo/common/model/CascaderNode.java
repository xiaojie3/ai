package com.example.demo.common.model;

import lombok.Data;

import java.util.List;
@Data
public class CascaderNode {
    private String value;
    private String label;
    private List<CascaderNode> children;

    // 可以为角色组和角色提供不同的构造函数，方便转换
    public CascaderNode(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public CascaderNode(String value, String label, List<CascaderNode> children) {
        this.value = value;
        this.label = label;
        this.children = children;
    }
}

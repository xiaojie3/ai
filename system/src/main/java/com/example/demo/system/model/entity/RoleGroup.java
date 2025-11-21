package com.example.demo.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class RoleGroup {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String name; // 对应 label
    private String code; // 对应 value
    private Long parentId; // 父级ID，顶级为0
}

package com.example.demo.system.service.impl;

import com.example.demo.common.model.CascaderNode;
import com.example.demo.system.mapper.RoleGroupMapper;
import com.example.demo.system.mapper.RoleMapper;
import com.example.demo.system.model.entity.Role;
import com.example.demo.system.model.entity.RoleGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleGroupService {
    private final RoleGroupMapper roleGroupMapper;
    private final RoleMapper roleMapper;

    /**
     * 查询所有角色组和角色，组装成级联选择器所需的树形结构
     * @return List<CascaderNode>
     */
    public List<CascaderNode> getRoleGroupCascader() {
        // 1. 查询所有顶级角色组
        List<RoleGroup> topLevelGroups = roleGroupMapper.selectTopLevelGroups();

        // 2. 查询所有角色（一次性查出，避免在循环中多次查询数据库）
        // 注意：这里为了简化，查询了所有角色。如果数据量巨大，可以考虑分批查询。
        // 但通常角色数量有限，一次性查询是可以接受的。
        List<Role> allRoles = roleMapper.selectList(null); // selectList(null) 查询所有

        // 3. 组装树形结构
        List<CascaderNode> cascaderOptions = new ArrayList<>();
        for (RoleGroup group : topLevelGroups) {
            // 3.1 将角色组转换为 CascaderNode
            CascaderNode groupNode = new CascaderNode(group.getCode(), group.getName());

            // 3.2 为当前角色组查找并添加对应的角色
            List<CascaderNode> roleNodes = allRoles.stream()
                    .filter(role -> group.getId().equals(role.getGroupId()))
                    .map(role -> new CascaderNode(role.getCode(), role.getName()))
                    .collect(Collectors.toList());

            groupNode.setChildren(roleNodes);

            // 3.3 将组装好的角色组节点添加到最终列表
            cascaderOptions.add(groupNode);
        }

        return cascaderOptions;
    }
}

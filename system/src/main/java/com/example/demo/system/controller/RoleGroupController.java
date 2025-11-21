package com.example.demo.system.controller;

import com.example.demo.common.model.ApiResult;
import com.example.demo.common.model.CascaderNode;
import com.example.demo.system.service.impl.RoleGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/role-group")
@RequiredArgsConstructor
public class RoleGroupController {
    private final RoleGroupService roleGroupService;

    @GetMapping("/cascader")
    public ApiResult<List<CascaderNode>> getRoleGroupCascader() {
        List<CascaderNode> result = roleGroupService.getRoleGroupCascader();
        return ApiResult.success(result);
    }
}

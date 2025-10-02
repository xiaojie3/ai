package com.example.systemadministration.dto;

import com.example.edu.common.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoleDto extends BaseDto {
    private String name;

    private Set<PermissionDto> permissionDtoSet = new HashSet<>();
}

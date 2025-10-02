package com.example.edu.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserContext {

    private String userId;

    private String username;

    private Set<String> permissions;
}

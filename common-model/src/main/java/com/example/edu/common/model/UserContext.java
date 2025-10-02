package com.example.edu.common.model;

import lombok.Data;

import java.util.Set;
@Data
public class UserContext {
    private String userId;
    private String username;
    private Set<String> permissions;
    public UserContext(){};
    public UserContext(String string, String username, Set<String> permissions) {
    }
}

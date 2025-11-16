package com.example.demo.auth.repository;

import java.util.List;

public interface RoleRepository {
    List<String> getRoleCodeListByUserId(String userId);
}

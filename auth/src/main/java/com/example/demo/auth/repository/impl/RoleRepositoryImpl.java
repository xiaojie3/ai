package com.example.demo.auth.repository.impl;

import com.example.demo.auth.model.entity.User;
import com.example.demo.auth.repository.RoleRepository;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleRepositoryImpl implements RoleRepository {
    private final DSLContext dsl;

    public RoleRepositoryImpl(DSLContext dsl) {
        this.dsl = dsl;
    }
    @Override
    public List<String> getRoleCodeListByUserId(String userId) {
        Result<Record> result = dsl.select().from("sys_role")
                .leftJoin("sys_user_role").on("sys_role.id = sys_user_role.role_id")
                .where("user_id = '" + userId + "'").fetch();
        List<String> list = new ArrayList<>();
        for (Record r : result) {
            list.add(String.valueOf(r.getValue("role_code")));
        }
        return list;
    }
}

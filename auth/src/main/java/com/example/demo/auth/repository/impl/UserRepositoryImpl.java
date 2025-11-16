package com.example.demo.auth.repository.impl;

import com.example.demo.auth.model.entity.User;
import com.example.demo.auth.repository.UserRepository;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final DSLContext dsl;

    public UserRepositoryImpl(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public User findByAccount(String account) {
        Result<Record> result = dsl.select().from("sys_user").where("account = '" + account + "'").fetch();
        for (Record r : result) {
            User user = new User();
            user.setId(String.valueOf(r.getValue("id")));
            user.setPassword(String.valueOf(r.getValue("password")));
            user.setAccount(account);
            return user;
        }
        return null;
    }

    @Override
    public User findById(String id) {
        Result<Record> result = dsl.select().from("sys_user").where("id = '" + id + "'").fetch();
        for (Record r : result) {
            User user = new User();
            user.setId(id);
            user.setPassword(String.valueOf(r.getValue("password")));
            user.setAccount(String.valueOf(r.getValue("account")));
            return user;
        }
        return null;
    }
}

package com.example.demo.auth.repository.impl;

import com.example.demo.auth.model.dto.UserDetailsDto;
import com.example.demo.auth.repository.UserRepository;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

@Repository
public class UserJpaRepositoryImpl implements UserRepository {
    private final DSLContext dsl;

    public UserJpaRepositoryImpl(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public UserDetailsDto findByAccount(String account) {
        Result<Record> result = dsl.select().from("sys_user").where("account = '" + account + "'").fetch();
        String password = "";
        for (Record r : result) {
            password = String.valueOf(r.getValue("password"));
        }
        return new UserDetailsDto(account, password);
    }
}

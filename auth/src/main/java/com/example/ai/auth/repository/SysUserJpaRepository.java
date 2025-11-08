package com.example.ai.auth.repository;

import com.example.ai.auth.dto.UserDetailsDto;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

@Repository
public class SysUserJpaRepository {
    private final DSLContext dsl;

    public SysUserJpaRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public UserDetailsDto findByAccount(String account) {
        Result<Record> result = dsl.select().from("sys_user").where("account = '" + account + "'").fetch();
        String password = "";
        for (Record r : result) {
            password = String.valueOf(r.getValue("password"));
        }
        return new UserDetailsDto(account, password);
    }
}

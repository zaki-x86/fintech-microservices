package com.example.repository;

import com.example.model.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Objects;

import static com.example.Tables.USER;
import static com.example.Tables.USER_CLIENT;

@Repository
@RequiredArgsConstructor
public class UserClientRepo {

    public static final int SAVED = 1;
    private final DSLContext dsl;


    public void save(long userId, String os, String ip) {

        int result = dsl.insertInto(USER_CLIENT)
                .columns(USER_CLIENT.USER_ID,
                        USER_CLIENT.IP,
                        USER_CLIENT.OS)
                .values(userId, ip, os).execute();

        if (result != SAVED)
            throw new RuntimeException();
    }

}
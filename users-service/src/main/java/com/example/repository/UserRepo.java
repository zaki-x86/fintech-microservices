package com.example.repository;

import com.example.Tables;
import com.example.dto.UserRequest;
import com.example.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepo {

    private final DSLContext dsl;

    public UserResponse findById(long id) {
        return dsl
                .selectFrom(Tables.USER)
                .where(Tables.USER.ID.eq(id))
                .fetchAnyInto(UserResponse.class);
    }

    public List<UserResponse> findAll() {
        return dsl
                .selectFrom(Tables.USER)
                .fetchInto(UserResponse.class);
    }

    public void create(UserRequest request) {
        dsl.insertInto(
                Tables.USER,
                Tables.USER.FIRSTNAME,
                Tables.USER.LASTNAME,
                Tables.USER.PASSWORD,
                Tables.USER.EMAIL,
                Tables.USER.BIRTH_DATE
        ).values(

                request.getFirstName(),
                request.getLastName(),
                request.getPassword(),
                request.getEmail(),
                request.getBirthDate()
        );
    }
}

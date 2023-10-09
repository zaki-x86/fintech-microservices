package com.example.repository;

import com.example.Tables;
import com.example.dto.UserRequest;
import com.example.dto.UserResponse;
import com.example.tables.User;
import com.example.tables.records.UserRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.InsertValuesStep5;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.example.Tables.*;
import static org.jooq.impl.DSL.row;

@Repository
@RequiredArgsConstructor
public class UserRepo {

    private final DSLContext dsl;

    public UserResponse findById(long id) {//TODO"
        return dsl
                .selectFrom(USER)
                .where(USER.ID.eq(id).and(USER.STATUS.ne("DELETED")))
                .fetchAnyInto(UserResponse.class);
    }


    public UserRecord findUserById(long id) {
        return dsl
                .selectFrom(USER)
                .where(USER.ID.eq(id).and(USER.STATUS.ne("DELETED")))
                .fetchOne();
    }

    public List<UserResponse> findAll() {
        return dsl
                .selectFrom(USER)
                .where(USER.STATUS.ne("DELETED"))//TODO: enums
                .fetchInto(UserResponse.class);
    }

    public int create(UserRequest request) {

        Long userId = Objects.requireNonNull(
                dsl.insertInto(USER)
                        .columns(USER.FIRSTNAME,
                                USER.LASTNAME,
                                USER.PASSWORD,
                                USER.EMAIL,
                                USER.BIRTH_DATE)
                        .values(request.getFirstName(),
                                request.getLastName(),
                                request.getPassword(),
                                request.getEmail(),
                                request.getBirthDate()).
                        returning(USER.ID).fetchOne()
        ).value1();

        return userId.intValue();
    }

    public void update(UserRecord user) {
        dsl.update(USER)
                .set(
                        row(USER.FIRSTNAME, USER.LASTNAME, USER.PASSWORD, USER.EMAIL, USER.BIRTH_DATE),
                        row(user.getFirstname(), user.getLastname(), user.getPassword(), user.getEmail(), user.getBirthDate()))
                .where(USER.ID.equal(user.getId()))
                .execute();
    }


    public void delete(long id) {

        dsl.update(USER)
                .set(
                        row(USER.STATUS),
                        row("DELETED"))
                .where(USER.ID.equal(id))
                .execute();
    }
}

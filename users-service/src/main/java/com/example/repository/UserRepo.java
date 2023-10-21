package com.example.repository;

import com.example.model.dto.UserAuthResponse;
import com.example.model.dto.UserRequest;
import com.example.model.dto.UserResponse;
import com.example.exception.CustomNotFoundException;
import com.example.model.enumaration.UserStatus;
import com.example.tables.records.UserRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.Tables.*;
import static org.jooq.impl.DSL.row;

@Repository
@RequiredArgsConstructor
public class UserRepo {

    private final DSLContext dsl;

    public UserResponse findById(long id) {  //TODO
        return Optional.ofNullable(
                dsl
                        .selectFrom(USER)
                        .where(USER.ID.eq(id).and(USER.STATUS.ne(UserStatus.DELETED.toString())))
                        .fetchAnyInto(UserResponse.class)
        ).orElseThrow(() -> new CustomNotFoundException(id));
    }


    public Optional<UserAuthResponse> findUserAuthByEmail(String email) {
        return Optional.ofNullable(
                dsl
                        .selectFrom(USER)
                        .where(USER.EMAIL.eq(email).and(USER.STATUS.ne(UserStatus.DELETED.toString())))
                        .fetchAnyInto(UserAuthResponse.class)
        );
    }


    public UserRecord findUserById(long id) {
        return Optional.ofNullable(
                dsl
                        .selectFrom(USER)
                        .where(USER.ID.eq(id).and(USER.STATUS.ne(UserStatus.DELETED.toString())))
                        .fetchOne()
        ).orElseThrow(() -> new CustomNotFoundException(id));
    }


    public List<UserResponse> findAll() {
        return dsl
                .selectFrom(USER)
                .where(USER.STATUS.ne(UserStatus.DELETED.toString()))
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

    public UserResponse update(UserRecord user) {
        return dsl.update(USER)
                .set(
                        row(USER.FIRSTNAME, USER.LASTNAME, USER.PASSWORD, USER.EMAIL, USER.BIRTH_DATE, USER.STATUS),
                        row(user.getFirstname(), user.getLastname(), user.getPassword(), user.getEmail(), user.getBirthDate(), user.getStatus()))
                .where(USER.ID.equal(user.getId()))
                .returning().fetchAnyInto(UserResponse.class);
    }


    public void delete(long id) {
        dsl.update(USER)
                .set(
                        row(USER.STATUS),
                        row(UserStatus.DELETED.toString()))
                .where(USER.ID.equal(id))
                .execute();
    }

    public UserResponse existsByEmail(long id, String email) {
        return dsl
                .selectFrom(USER)
                .where(USER.EMAIL.eq(email).and(USER.ID.ne(id)))
                .fetchAnyInto(UserResponse.class);
    }
}

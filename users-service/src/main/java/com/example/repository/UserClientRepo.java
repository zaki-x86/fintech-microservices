package com.example.repository;

import com.example.model.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Objects;

import static com.example.Tables.USER;

@Repository
@RequiredArgsConstructor
public class UserClientRepo {

    private final DSLContext dsl;


    public void save(int o, String name, String ip) {

//        dsl.insertInto(USER)
//                .columns(USER.FIRSTNAME,
//                        USER.LASTNAME,
//                        USER.PASSWORD,
//                        USER.EMAIL,
//                        USER.BIRTH_DATE)
//                .values(request.getFirstName(),
//                        request.getLastName(),
//                        request.getPassword(),
//                        request.getEmail(),
//                        request.getBirthDate()).

    }


//    public int create(UserRequest request) {
//
//        Long userId = Objects.requireNonNull(
//                dsl.insertInto(USER)
//                        .columns(USER.FIRSTNAME,
//                                USER.LASTNAME,
//                                USER.PASSWORD,
//                                USER.EMAIL,
//                                USER.BIRTH_DATE)
//                        .values(request.getFirstName(),
//                                request.getLastName(),
//                                request.getPassword(),
//                                request.getEmail(),
//                                request.getBirthDate()).
//                        returning(USER.ID).fetchOne()
//        ).value1();
//
//        return userId.intValue();
//    }
}

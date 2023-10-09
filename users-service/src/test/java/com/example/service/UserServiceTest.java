package com.example.service;

import com.example.dto.UserRequest;
import com.example.repository.UserRepo;
import com.example.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    private UserService userService;

    @BeforeEach
    void initUseCase() {
        userService = new UserServiceImpl(userRepo);
    }

    @Test
    public void create() {

        UserRequest userRequest = UserRequest.builder()
                .firstName("Samir")
                .lastName("Allahverdiyev")
                .password("123")
                .email("a_nazim2007@mail.ru")
                .birthDate(LocalDate.of(Year.of(1998).getValue(), Month.NOVEMBER, 9))
                .build();

        when(userRepo.create(any(UserRequest.class))).thenReturn(1);


        int i = userRepo.create(userRequest);

        assertTrue("sss", i == 1);
    }


}

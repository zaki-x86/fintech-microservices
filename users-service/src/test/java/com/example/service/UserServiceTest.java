package com.example.service;

import com.example.model.dto.UserRequest;
import com.example.model.dto.UserResponse;
import com.example.exception.CustomNotFoundException;
import com.example.repository.UserRepo;
import com.example.service.impl.UserServiceImpl;
import com.example.tables.records.UserRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;
    private UserService userService;
    private UserRequest DUMMY_USER_REQUEST;
    private UserResponse DUMMY_USER_RESPONSE;
    private UserRecord DUMMY_USER_RECORD;


    @BeforeEach
    void init() {
        userService = new UserServiceImpl(userRepo);

        DUMMY_USER_REQUEST = UserRequest.builder()
                .firstName("Samir")
                .lastName("Allahverdiyev")
                .password("123")
                .email("a_nazim2007@mail.ru")
                .birthDate(LocalDate.of(Year.of(1998).getValue(), Month.NOVEMBER, 9))
                .build();

        DUMMY_USER_RESPONSE = UserResponse.builder()
                .id(1)
                .firstName("Samir")
                .lastname("Allahverdiyev")
                .build();

        DUMMY_USER_RECORD = new UserRecord()
                .setId(1L)
                .setFirstname("Samir")
                .setLastname("Allahverdiyev")
                .setPassword("123")
                .setEmail("a_nazim2007@mail.ru")
                .setBirthDate(LocalDate.of(Year.of(1998).getValue(), Month.NOVEMBER, 9));
    }


    @Test
    public void testGetById() {
        int id = 1;
        when(userRepo.findById(id)).thenReturn(DUMMY_USER_RESPONSE);
        UserResponse userResponse = userService.getById(id);

        assertTrue("User Response is null", userResponse != null);
        assertEquals(userResponse.getId(), DUMMY_USER_RESPONSE.getId());
        assertEquals(userResponse.getFirstName(), DUMMY_USER_RESPONSE.getFirstName());
        assertEquals(userResponse.getLastname(), DUMMY_USER_RESPONSE.getLastname());
    }

    @Test
    public void testThrowsExceptionIfIdNotFound() {
        int id = 2;
        when(userRepo.findById(id)).thenThrow(CustomNotFoundException.class);
        assertThatThrownBy(() -> userService.getById(id))
                .isInstanceOf(CustomNotFoundException.class);
    }


    @Test
    public void testCreate() {
        int createdUserId = 1;
        when(userRepo.create(any(UserRequest.class))).thenReturn(createdUserId);
        int id = userService.create(DUMMY_USER_REQUEST);
        assertEquals(id, createdUserId);
    }


    //TODO: test other methods
}

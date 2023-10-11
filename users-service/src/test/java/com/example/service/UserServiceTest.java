package com.example.service;

import com.example.exception.AlreadyExistsException;
import com.example.model.dto.UserRequest;
import com.example.model.dto.UserResponse;
import com.example.exception.CustomNotFoundException;
import com.example.model.enumaration.UserStatus;
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
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
                .status(UserStatus.DISABLED.toString())
                .build();

        DUMMY_USER_RESPONSE = UserResponse.builder()
                .id(1)
                .firstName("Samir")
                .lastname("Allahverdiyev")
                .email("a_nazim2007@mail.ru")
                .birthDate(LocalDate.of(Year.of(1998).getValue(), Month.NOVEMBER, 9))
                .status(UserStatus.DISABLED.toString())
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
    public void testThrowsExceptionIfIdNotFoundInGetById() {
        int id = 2;
        when(userRepo.findById(id)).thenThrow(CustomNotFoundException.class);
        assertThatThrownBy(() -> userService.getById(id))
                .isInstanceOf(CustomNotFoundException.class);
    }


    @Test
    public void testGetAll() {
        List<UserResponse> dummyUserResponses = Arrays.asList(DUMMY_USER_RESPONSE, DUMMY_USER_RESPONSE, DUMMY_USER_RESPONSE);
        when(userRepo.findAll()).thenReturn(dummyUserResponses);
        List<UserResponse> userResponses =userService.getAll();
        assertEquals(userResponses.size(), dummyUserResponses.size());
    }


    @Test
    public void testCreate() {
        int createdUserId = 1;
        when(userRepo.create(any(UserRequest.class))).thenReturn(createdUserId);
        int id = userService.create(DUMMY_USER_REQUEST);
        assertEquals(id, createdUserId);
    }


    @Test
    public void testThrowsExceptionIfEmailDuplicatedInCreate() {
        when(userRepo.create(any(UserRequest.class))).thenThrow(AlreadyExistsException.class);
        assertThatThrownBy(() -> userRepo.create(DUMMY_USER_REQUEST))
                .isInstanceOf(AlreadyExistsException.class);
    }

    @Test
    public void testUpdate() {
        int id = 1;
        when(userRepo.findUserById(id)).thenReturn(DUMMY_USER_RECORD);
        when(userRepo.update(any(UserRecord.class))).thenReturn(DUMMY_USER_RESPONSE);

        UserResponse userResponse = userService.update(id, DUMMY_USER_REQUEST);
        assertTrue("User Response is null", userResponse != null);
        assertEquals(userResponse.getFirstName(), DUMMY_USER_REQUEST.getFirstName());
        assertEquals(userResponse.getLastname(), DUMMY_USER_REQUEST.getLastName());
        assertEquals(userResponse.getEmail(), DUMMY_USER_REQUEST.getEmail());
        assertEquals(userResponse.getBirthDate(), DUMMY_USER_REQUEST.getBirthDate());
        assertEquals(userResponse.getStatus(), DUMMY_USER_REQUEST.getStatus());
    }

    @Test
    public void testThrowsExceptionIfIdNotFoundInUpdate() {
        when(userRepo.findUserById(1)).thenThrow(CustomNotFoundException.class);
        assertThatThrownBy(() -> userService.update(1, DUMMY_USER_REQUEST))
                .isInstanceOf(CustomNotFoundException.class);
    }


    @Test
    public void testThrowsExceptionIfEmailDuplicatedInCreateInUpdate() {
        when(userRepo.findUserById(1)).thenThrow(AlreadyExistsException.class);
        assertThatThrownBy(() -> userService.update(1, DUMMY_USER_REQUEST))
                .isInstanceOf(AlreadyExistsException.class);
    }


    @Test
    public void testDelete() {
        try {
            userService.delete(1);
        } catch (Exception ex) {
            assertNull(ex);
        }
    }


}

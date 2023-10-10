package com.example.service;

import com.example.model.dto.UserRequest;
import com.example.model.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse getById(long id);

    List<UserResponse> getAll();

    int  create(UserRequest request);

    void update(long id, UserRequest request);

    void delete(long id);
}

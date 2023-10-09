package com.example.service.impl;

import com.example.dto.UserRequest;
import com.example.dto.UserResponse;
import com.example.repository.UserRepo;
import com.example.service.UserService;
import com.example.tables.records.UserRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    public UserResponse getById(long id) { //TODO: optional
        return userRepo.findById(id);
    }

    @Override
    public List<UserResponse> getAll() {
        return userRepo.findAll();
    }

    @Override
    public int create(UserRequest request) {
        return userRepo.create(request);
    }

    @Override
    public void update(long id, UserRequest request) {
        UserRecord user = userRepo.findUserById(id);
        changeIfPresent(user, request);
        userRepo.update(user);
    }

    private void changeIfPresent(UserRecord userResponse, UserRequest request) {
        Optional.ofNullable(request.getFirstName()).ifPresent(userResponse::setFirstname);
        Optional.ofNullable(request.getLastName()).ifPresent(userResponse::setLastname);
        Optional.ofNullable(request.getPassword()).ifPresent(userResponse::setPassword);
        Optional.ofNullable(request.getEmail()).ifPresent(userResponse::setEmail);
        Optional.ofNullable(request.getBirthDate()).ifPresent(userResponse::setBirthDate);
    }

    @Override
    public void delete(long id) {
        userRepo.delete(id);
    }
}

package com.example.service.impl;

import com.example.dto.UserRequest;
import com.example.dto.UserResponse;
import com.example.repository.UserRepo;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    public UserResponse getById(long id) {
        return userRepo.findById(id);
    }

    @Override
    public List<UserResponse> getAll() {
        return userRepo.findAll();
    }

    @Override
    public ResponseEntity<?> create(UserRequest request) {
        userRepo.create(request);
        return null;
    }


}

package com.example.service;

import com.example.exception.AlreadyExistsException;
import com.example.model.dto.UserRequest;
import com.example.model.dto.UserResponse;
import com.example.repository.UserRepo;
import com.example.tables.records.UserRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    public UserResponse getById(long id) {
        return userRepo.findById(id);
    }

    public List<UserResponse> getAll() {
        return userRepo.findAll();
    }

    public int create(UserRequest request) {
        if (isEmailUnique(0, request.getEmail()))
            throw new AlreadyExistsException(request.getEmail());
        return userRepo.create(request);
    }

    private boolean isEmailUnique(long id, String email) {
        return userRepo.existsByEmail(id, email) != null;
    }

    public UserResponse update(long id, UserRequest request) {
        UserRecord user = userRepo.findUserById(id);
        if (isEmailUnique(id, request.getEmail()))
            throw new AlreadyExistsException(request.getEmail());
        changeIfPresent(user, request);
        return userRepo.update(user);
    }

    private void changeIfPresent(UserRecord userResponse, UserRequest request) {
        Optional.ofNullable(request.getFirstName()).ifPresent(userResponse::setFirstname);
        Optional.ofNullable(request.getLastName()).ifPresent(userResponse::setLastname);
        Optional.ofNullable(request.getPassword()).ifPresent(userResponse::setPassword);
        Optional.ofNullable(request.getEmail()).ifPresent(userResponse::setEmail);
        Optional.ofNullable(request.getBirthDate()).ifPresent(userResponse::setBirthDate);
    }

    public void delete(long id) {
        userRepo.delete(id);
    }
}

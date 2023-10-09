package com.example.controller;


import com.example.dto.UserRequest;
import com.example.dto.UserResponse;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    //TODO: exception handler

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> get(@PathVariable long id) {
        var userResponse = userService.getById(id);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {//TODO: add filter and Pagenation
        var userResponses = userService.getAll();
        return ResponseEntity.ok(userResponses);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody UserRequest request) {
        int userId = userService.create(request);
        URI location = getLocation(userId);
        return ResponseEntity.created(location).build();
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody UserRequest request) {
        userService.update(id, request);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }


    public static <T> URI getLocation(T id) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}

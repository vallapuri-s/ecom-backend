package com.thoughtworks.ecombackend.controllers;


import com.thoughtworks.ecombackend.dto.OrderDto;
import com.thoughtworks.ecombackend.dto.UserDto;
import com.thoughtworks.ecombackend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserService userService;
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable final String id) {
        return ResponseEntity.ok(userService.get(id));
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody @Valid final UserDto userDto) {
        return new ResponseEntity<>(userService.create(userDto), HttpStatus.CREATED);
    }

}

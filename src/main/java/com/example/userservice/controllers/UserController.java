package com.example.userservice.controllers;

import com.example.userservice.dtos.SignUpRequestDto;
import com.example.userservice.dtos.UserDto;
import com.example.userservice.models.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private UserService userService;
    private UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public UserDto signup(@RequestBody SignUpRequestDto requestDto){
        User user = this.userService.signUp(requestDto.getEmail(), requestDto.getName(), requestDto.getPassword(), requestDto.getRoles());
        return UserDto.from(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id){
        return userRepository.findById(id).get();
    }
}

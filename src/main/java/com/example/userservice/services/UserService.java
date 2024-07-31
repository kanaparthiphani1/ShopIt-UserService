package com.example.userservice.services;

import com.example.userservice.models.Role;
import com.example.userservice.models.User;

import java.util.List;

public interface UserService {
    public User signUp(String email, String name, String password, List<Role> roles);
}

package com.example.userservice.dtos;

import com.example.userservice.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SignUpRequestDto {
    private String email;
    private String name;
    private String password;
    private List<Role> roles;
}

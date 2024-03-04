package com.soya.core_spring_security.dto;

import com.soya.core_spring_security.entity.Role;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class AccountDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String age;
    private Set<Role> userRoles = new HashSet<>();
}

package com.soya.core_spring_security.dto;

import lombok.Data;

@Data
public class AccountDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String age;
}

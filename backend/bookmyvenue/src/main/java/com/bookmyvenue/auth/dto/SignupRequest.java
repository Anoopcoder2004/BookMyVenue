package com.bookmyvenue.auth.dto;

import com.bookmyvenue.common.enums.Role;
import lombok.Data;

@Data
public class SignupRequest {
    private String name;
    private String email;
    private String password;
    private Role role; // optional (default USER)
}
package com.bookmyvenue.auth.service;

import com.bookmyvenue.auth.dto.*;

public interface AuthService {

    AuthResponse signup(SignupRequest request);

    AuthResponse login(LoginRequest request);
}
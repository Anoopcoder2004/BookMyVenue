package com.bookmyvenue.auth.service;

import com.bookmyvenue.auth.dto.*;
import com.bookmyvenue.common.response.ApiResponse;

public interface AuthService {

    ApiResponse<AuthResponse> signup(SignupRequest request);

    ApiResponse<AuthResponse> login(LoginRequest request);
}
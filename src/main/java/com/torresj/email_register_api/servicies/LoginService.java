package com.torresj.email_register_api.servicies;

import com.torresj.email_register_api.dtos.LoginResponseDto;

public interface LoginService {
    LoginResponseDto login(String username);
}

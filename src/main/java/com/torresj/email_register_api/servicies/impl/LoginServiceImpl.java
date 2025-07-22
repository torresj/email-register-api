package com.torresj.email_register_api.servicies.impl;

import com.torresj.email_register_api.UserType;
import com.torresj.email_register_api.dtos.LoginResponseDto;
import com.torresj.email_register_api.dtos.UserDto;
import com.torresj.email_register_api.servicies.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Value("${api.admin.name}")
    private final String adminName;

    @Override
    public LoginResponseDto login(String username) {
        UserDto user = new UserDto(username, username.equals(adminName) ? UserType.ADMIN : UserType.USER);
        return new LoginResponseDto("Authorized", user);
    }
}

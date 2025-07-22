package com.torresj.email_register_api.dtos;

import com.torresj.email_register_api.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private String username;
    private UserType type;
}

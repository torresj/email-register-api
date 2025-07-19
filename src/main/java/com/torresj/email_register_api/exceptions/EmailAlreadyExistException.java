package com.torresj.email_register_api.exceptions;

public class EmailAlreadyExistException extends Exception {
    public EmailAlreadyExistException(String email) {
        super("Email already exist: " + email);
    }
}

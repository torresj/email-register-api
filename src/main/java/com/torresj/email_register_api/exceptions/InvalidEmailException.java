package com.torresj.email_register_api.exceptions;

public class InvalidEmailException extends Exception {
    public InvalidEmailException(String email) {
        super("Invalid email: " + email);
    }
}

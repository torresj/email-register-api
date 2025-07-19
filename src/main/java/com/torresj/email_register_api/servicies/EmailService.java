package com.torresj.email_register_api.servicies;

import com.torresj.email_register_api.exceptions.EmailAlreadyExistException;
import com.torresj.email_register_api.exceptions.InvalidEmailException;

import java.util.List;

public interface EmailService {
    void register(String email) throws InvalidEmailException, EmailAlreadyExistException;
    List<String> getEmails();
}

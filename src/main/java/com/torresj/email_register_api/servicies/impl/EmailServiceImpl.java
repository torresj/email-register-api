package com.torresj.email_register_api.servicies.impl;

import com.torresj.email_register_api.entities.EmailEntity;
import com.torresj.email_register_api.exceptions.InvalidEmailException;
import com.torresj.email_register_api.repositories.EmailRepository;
import com.torresj.email_register_api.servicies.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;
    private final EmailValidationService emailValidationService;

    @Override
    public void register(String email) throws InvalidEmailException {
        log.info("Registering email {}", email);
        emailValidationService.validateEmail(email);
        var optionalEmail = emailRepository.findByEmail(email);
        if (optionalEmail.isEmpty()) {
            emailRepository.save(new EmailEntity(email));
        }
    }

    @Override
    public void remove(String email) throws InvalidEmailException {
        log.info("Removing email {}", email);
        emailValidationService.validateEmail(email);
        var optionalEmail = emailRepository.findByEmail(email);
        optionalEmail.ifPresent(emailRepository::delete);
    }

    @Override
    public List<String> getEmails() {
        log.info("Getting emails");
        return emailRepository.findAll().stream().map(EmailEntity::getEmail).toList();
    }
}

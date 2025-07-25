package com.torresj.email_register_api.controllers;

import com.torresj.email_register_api.dtos.DeleteRequestDto;
import com.torresj.email_register_api.dtos.RegisterRequestDto;
import com.torresj.email_register_api.exceptions.InvalidEmailException;
import com.torresj.email_register_api.servicies.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/v1/emails")
@Slf4j
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @Operation(summary = "Register email", security = @SecurityRequirement(name = "basicScheme"))
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Registered"
                    ),
            })
    @PostMapping()
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<String> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Email to be registered",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RegisterRequestDto.class)))
            @RequestBody RegisterRequestDto request
    ) throws InvalidEmailException {
        emailService.register(request.getEmail());
        return ResponseEntity.ok("{\"status\":\"Registered\"}");
    }

    @Operation(summary = "Get registered emails", security = @SecurityRequirement(name = "basicScheme"))
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "ok",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = String.class)))
                            }
                    ),
            })
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<String>> getEmails() {
        var emails = emailService.getEmails();
        return ResponseEntity.ok(emails);
    }

    @Operation(summary = "Get registered emails in a file", security = @SecurityRequirement(name = "basicScheme"))
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "ok",
                            content = {
                                @Content(
                                        mediaType = "text/plain",
                                        schema = @Schema(implementation = Resource.class)
                                )
                            }
                    ),
            })
    @GetMapping("/export")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Resource> getEmailsInFile() {
        var emails = emailService.getEmails();
        String content = String.join(", ", emails);

        ByteArrayResource resource = new ByteArrayResource(content.getBytes(StandardCharsets.UTF_8));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"emails.txt\"")
                .contentType(MediaType.TEXT_PLAIN)
                .contentLength(resource.contentLength())
                .body(resource);
    }

    @Operation(summary = "Delete email", security = @SecurityRequirement(name = "basicScheme"))
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Deleted"
                    ),
            })
    @DeleteMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> remove(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Email to be deleted",
                    required = true,
                    content = @Content(schema = @Schema(implementation = DeleteRequestDto.class)))
            @RequestBody DeleteRequestDto request
    ) throws InvalidEmailException {
        emailService.remove(request.getEmail());
        return ResponseEntity.ok("{\"status\":\"Deleted\"}");
    }
}

/*
 * @(#)GlobalExceptionHandler.java
 *
 * Copyright (c) J-Tech Solucoes em Informatica.
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of J-Tech.
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with J-Tech.
 */
package br.com.jtech.tasklist.config.infra.utils;



import br.com.jtech.tasklist.config.infra.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Create a global exception handler for intercepting all exceptions in the api.
 *
 * @author angelo.vicente
 * class GlobalExceptionHandler
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * This method handles spring validations.
     *
     * @param ex Exception thrown.
     * @return Return a {@link ApiError} with an array of errors.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException ex) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST);
        error.setMessage("Validation error");
        error.setTimestamp(LocalDateTime.now());
        error.setSubErrors(subErrors(ex));
        error.setDebugMessage(ex.getLocalizedMessage());
        return buildResponseEntity(error);
    }

    /**
     * Handles ResourceNotFoundException (404).
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex) {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        error.setTimestamp(LocalDateTime.now());
        return buildResponseEntity(error);
    }

    /**
     * Handles UnauthorizedException (401).
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiError> handleUnauthorized(UnauthorizedException ex) {
        ApiError error = new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage(), ex);
        error.setTimestamp(LocalDateTime.now());
        return buildResponseEntity(error);
    }

    /**
     * Handles ConflictException (409).
     */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiError> handleConflict(ConflictException ex) {
        ApiError error = new ApiError(HttpStatus.CONFLICT, ex.getMessage(), ex);
        error.setTimestamp(LocalDateTime.now());
        return buildResponseEntity(error);
    }

    /**
     * Handles BadRequestException (400).
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        error.setTimestamp(LocalDateTime.now());
        return buildResponseEntity(error);
    }

    /**
     * Handles generic RuntimeException (500).
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeException(RuntimeException ex) {
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", ex);
        error.setTimestamp(LocalDateTime.now());
        return buildResponseEntity(error);
    }

    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    private List<ApiSubError> subErrors(MethodArgumentNotValidException ex) {
        List<ApiSubError> errors = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            ApiValidationError api = new ApiValidationError(ex.getObjectName(), fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage());
            errors.add(api);
        }
        return errors;
    }

}
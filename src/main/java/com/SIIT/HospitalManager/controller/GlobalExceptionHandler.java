package com.SIIT.HospitalManager.controller;

import com.SIIT.HospitalManager.exception.BusinessException;
import com.SIIT.HospitalManager.exception.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.Objects;



    @RestControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler({BusinessException.class})
        public ResponseEntity<CustomErrorResponse> handle(BusinessException exception){
            CustomErrorResponse customErrorResponse = new CustomErrorResponse();
            customErrorResponse.setPrettyMessage(exception.getMessage());
            customErrorResponse.setErrorCode(exception.getHttpStatus().toString());
            return ResponseEntity.status(exception.getHttpStatus()).body(customErrorResponse);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public List<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException methodArgumentNotValidException){
            return methodArgumentNotValidException.getBindingResult().getFieldErrors().stream()
                    .map(entry -> Map.of("Invalid field: " + Objects.requireNonNull(entry.getField()), "Rejected value: " + entry.getRejectedValue() + ". Reason:" + entry.getDefaultMessage()))
                    .toList();
        }
    }


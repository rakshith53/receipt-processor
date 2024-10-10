package com.fetch.receipt_processor.exception;

import com.fetch.receipt_processor.DTO.ErrorResposeDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ReceiptNotFoundException.class)
    public ResponseEntity<ErrorResposeDTO> handleNotFound(ReceiptNotFoundException e) {
        ErrorResposeDTO errorResposeDTO = new ErrorResposeDTO(
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResposeDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResposeDTO> handleGeneralError(Exception e) {
        ErrorResposeDTO errorResposeDTO = new ErrorResposeDTO(
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResposeDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResposeDTO> handleValidationErrors(MethodArgumentNotValidException e){
        Map<String, String> errors = new HashMap<>();

        for(FieldError error: e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        ErrorResposeDTO errorResposeDTO = new ErrorResposeDTO(
                "The receipt is invalid",
                LocalDateTime.now(),
                errors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResposeDTO);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResposeDTO> handleBadRequest(BadRequestException e) {
        ErrorResposeDTO errorResponse = new ErrorResposeDTO(
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

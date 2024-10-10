package com.fetch.receipt_processor.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorResposeDTO {
    private String error;
    private LocalDateTime timestamp;
    private Map<String, String> validationErrors;

    public ErrorResposeDTO(String error, LocalDateTime timestamp) {
        this.error = error;
        this.timestamp = timestamp;
    }
}

package com.fetch.receipt_processor.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ReceiptResponseDTO {
    private UUID id;
}

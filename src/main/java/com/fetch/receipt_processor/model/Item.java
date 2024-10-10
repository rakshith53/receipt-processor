package com.fetch.receipt_processor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @NotNull
    @Pattern(regexp = "^[\\w\\s\\-]+$", message = "Invalid short description")
    private String shortDescription;

    @NotNull
    @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "Price must be a valid monetary value")
    private String price;
}

package com.fetch.receipt_processor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {

    @NotNull
    @Pattern(regexp =  "[\\w\\s\\-&]+$", message = "Invalid retailer name")
    private String retailer;

    @NotNull
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Invalid date format (expected YYYY-MM-DD)")
    private String purchaseDate;

    @NotNull
    @Pattern(regexp = "^\\d{2}:\\d{2}$", message = "Invalid time format (expected HH:MM)")
    private String purchaseTime;

    @NotNull
    @Size(min = 1, message = "At least one item is required")
    @Valid
    private List<Item> items;

    @NotNull
    @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "Total must be a valid monetary value")
    private String total;
}

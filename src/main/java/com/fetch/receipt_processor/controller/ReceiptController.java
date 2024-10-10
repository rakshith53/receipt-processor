package com.fetch.receipt_processor.controller;

import com.fetch.receipt_processor.DTO.PointsResponseDTO;
import com.fetch.receipt_processor.DTO.ReceiptResponseDTO;
import com.fetch.receipt_processor.exception.BadRequestException;
import com.fetch.receipt_processor.exception.ReceiptNotFoundException;
import com.fetch.receipt_processor.model.Receipt;
import com.fetch.receipt_processor.service.PointsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receipts")
@Validated
public class ReceiptController {

    @Autowired
    private PointsService pointsService;


    @PostMapping("/process")
    public ResponseEntity<ReceiptResponseDTO> processReceipt(@Valid @RequestBody Receipt receipt){
        try{
            ReceiptResponseDTO receiptResponseDTO = pointsService.processReceipt(receipt);
            return ResponseEntity.status(HttpStatus.OK).body(receiptResponseDTO);
        }
        catch (Exception e){
            throw new BadRequestException("The receipt is invalid.");
        }
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<PointsResponseDTO> getPoints(@PathVariable String id) {
        try{
            PointsResponseDTO responseDTO = pointsService.getPointsById(id);
            return ResponseEntity.ok(responseDTO);
        } catch (ReceiptNotFoundException e){
            throw new ReceiptNotFoundException(id);
        }

    }
            
}

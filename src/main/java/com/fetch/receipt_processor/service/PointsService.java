package com.fetch.receipt_processor.service;

import com.fetch.receipt_processor.DTO.PointsResponseDTO;
import com.fetch.receipt_processor.DTO.ReceiptResponseDTO;
import com.fetch.receipt_processor.exception.BadRequestException;
import com.fetch.receipt_processor.exception.ReceiptNotFoundException;
import com.fetch.receipt_processor.model.Item;
import com.fetch.receipt_processor.model.Receipt;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PointsService {

    private final Map<UUID, Map.Entry<Receipt, Integer> > store = new ConcurrentHashMap<>();

    public ReceiptResponseDTO processReceipt(Receipt receipt) {
        try {
            UUID id = UUID.randomUUID();
            int points = calculatePoints(receipt);
            store.put(id, Map.entry(receipt, points));
            return new ReceiptResponseDTO(id);
        } catch (Exception e){
            throw new BadRequestException("The receipt is invalid.");
        }


    }

    public int calculatePoints(Receipt receipt){
        int points = 0;

        points += receipt.getRetailer().replaceAll("[^a-zA-Z0-9]", "").length();

        double total = Double.parseDouble((receipt.getTotal()));
        if(total % 1 == 0){
            points += 50;
        }

        if(total % 0.25 == 0){
            points += 25;
        }

        points += (receipt.getItems().size()/2)*5;

        for(Item item: receipt.getItems()){
            if(item.getShortDescription().trim().length() % 3 == 0){
                double price = Double.parseDouble(item.getPrice());
                points += (int) Math.ceil(price * 0.2);
            }
        }

        int day = Integer.parseInt(receipt.getPurchaseDate().split("-")[2]);
        if(day % 2 != 0){
            points += 6;
        }

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime purchaseTime = LocalTime.parse(receipt.getPurchaseTime(), timeFormatter);
        LocalTime startTime = LocalTime.of(14, 0);
        LocalTime endTime = LocalTime.of(16, 0);
        if (purchaseTime.isAfter(startTime) && purchaseTime.isBefore(endTime)) {
            points += 10;
        }

        return points;
    }

    public PointsResponseDTO getPointsById(String id){
        try {
            UUID uuid = UUID.fromString(id);
            if(!store.containsKey(uuid)){
                throw new ReceiptNotFoundException(id);
            }
            return new PointsResponseDTO(store.get(uuid).getValue());

        } catch (Exception e) {
            throw new ReceiptNotFoundException(id);
        }
    }
}

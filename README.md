# Receipt Processor API

## Overview
The Receipt Processing API is a RESTful service that processes receipts and calculates points based on predefined rules. It exposes two main APIs:

* POST /receipts/process: Submits a receipt for processing.
* GET /receipts/{id}/points: Retrieves the points awarded for a specific receipt.

## Technology Stack

* Java 11+
* Spring Boot
* Maven
* Docker
* Postman (for API testing

## Project Structure
src/
 └── main/
     └── java/
         └── com/receipt_processor/
             ├── controller/
             │   └── ReceiptController.java
             ├── service/
             │   └── PointsService.java
             ├── model/
             │   ├── Receipt.java
             │   ├── Item.java
             ├── dto/
             │   ├── ReceiptResponseDTO.java
             │   └── PointsResponseDTO.java
             ├── exception/
             │   ├── ReceiptNotFoundException.java
             │   ├── BadRequestException.java
             │   └── InternalServerErrorException.java
             └── exception/
                 └── GlobalExceptionHandler.java

## API Flow and Interaction

### Post :

#### Request Handling

* A POST request is sent to **/receipts/process**, received by **ReceiptController**.
* The request body is validated based on the **Receipt** model.

#### Service Layer

* The controller delegates the processing logic to **PointsService**.
* A unique receipt ID (UUID) is generated and points are calculated based on:
  *  Alphanumeric characters in the retailer name.
  *  Total amount being a round number or divisible by 0.25.
  *  Item description length and purchase time.
* The receipt and calculated points are stored in memory.

#### Returning the Response

*  The receipt ID is returned to the client as part of a **ReceiptResponseDTO** with an HTTP 200 OK status.


### Get :

#### Request Handling

* A GET request is sent to **/receipts/{id}/points**, received by **ReceiptController**.
* The receipt ID is extracted from the URL and passed to the service layer.
  
#### Service Layer
* The service attempts to retrieve the points using the receipt ID from the in-memory store.
* If the receipt is found, the points are returned in a **PointsResponseDTO**.
  
#### Returning the Response
* If the receipt ID is invalid or the receipt is not found, a **ReceiptNotFoundException** is thrown and caught by the **GlobalExceptionHandler**.


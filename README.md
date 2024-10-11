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

* ### Post :

  * #### Request Handling

    * A POST request is sent to **/receipts/process**, received by **ReceiptController**.
    * The request body is validated based on the **Receipt** model.

  * #### Service Layer

    * The controller delegates the processing logic to **PointsService**.
    * A unique receipt ID (UUID) is generated and points are calculated based on:
       *  Alphanumeric characters in the retailer name.
       *  Total amount being a round number or divisible by 0.25.
       *  Item description length and purchase time.
    * The receipt and calculated points are stored in memory.

  * #### Returning the Response

    * The receipt ID is returned to the client as part of a **ReceiptResponseDTO** with an HTTP 200 OK status.


* ### Get :

  * #### Request Handling
  
    * A GET request is sent to **/receipts/{id}/points**, received by **ReceiptController**.
    * The receipt ID is extracted from the URL and passed to the service layer.
    
  * #### Service Layer
    
    * The service attempts to retrieve the points using the receipt ID from the in-memory store.
    * If the receipt is found, the points are returned in a **PointsResponseDTO**.
    
  * #### Returning the Response
    * If the receipt ID is invalid or the receipt is not found, a **ReceiptNotFoundException** is thrown and caught by the **GlobalExceptionHandler**.

## Error Handling

The API has robust error handling using Spring Boot’s @ControllerAdvice for global exception management.

### Common Errors:
  * 400 Bad Request: Returned when required fields are missing or have invalid formats.
  * 404 Not Found: Returned when the receipt ID is not found.


# How to run the program

## Prerequisites 
  * **Docker**: Make sure Docker is installed and running.

## Steps
 * **Clone the repository** :
    git clone https://github.com/rakshith53/receipt-processor
    cd receipt-processor
 * **Build docker image** :
    docker build -t receipt-processor .
 * **Run Docker Container**:
    docker run -p 8080:8080 receipt-processor
 * **Access the application** :
    The API will be running on **http://localhost:8080**. You can test the API endpoints using Postman or curl.


### Example API calls

  * **Post**:
    * end point : **/receipts/process**
    * ```
curl -X POST http://localhost:8080/receipts/process \
  -H "Content-Type: application/json" \
  -d '{
        "retailer": "Target",
        "purchaseDate": "2022-01-01",
        "purchaseTime": "13:01",
        "items": [
          {"shortDescription": "Mountain Dew 12PK", "price": "6.49"}
        ],
        "total": "6.49"
      }'

  * **Get**:
    * end point : **/receipts/{id}/points**
    * curl -X GET http://localhost:8080/receipts/{id}/points

Replace {id} with the actual receipt ID from the POST request.






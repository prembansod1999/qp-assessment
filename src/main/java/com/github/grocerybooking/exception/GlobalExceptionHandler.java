package com.github.grocerybooking.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.github.grocerybooking.entity.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InsufficientInventoryException.class)
    public ResponseEntity<ApiResponse> handleInsufficientInventory(InsufficientInventoryException ex) {
        logger.error("Error: {}", ex.getMessage());
        ApiResponse response = new ApiResponse(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);  // 400 Bad Request
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ApiResponse> handleItemNotFound(ItemNotFoundException ex) {
        logger.error("Error: {}", ex.getMessage());
        ApiResponse response = new ApiResponse(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);  // 404 Not Found
    }

    @ExceptionHandler(InvalidInventoryUpdateException.class)
    public ResponseEntity<ApiResponse> handleInvalidInventoryUpdate(InvalidInventoryUpdateException ex) {
        logger.error("Error: {}", ex.getMessage());
        ApiResponse response = new ApiResponse(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);  // 400 Bad Request
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleUnexpectedException(Exception ex) {
        logger.error("Unexpected error: {}", ex.getMessage());
        ApiResponse response = new ApiResponse("An unexpected error occurred.", false);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);  // 500 Internal Server Error
    }
}

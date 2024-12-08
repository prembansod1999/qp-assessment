package com.github.grocerybooking.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.github.grocerybooking.entity.ApiResponse;
import com.github.grocerybooking.entity.GroceryItem;
import com.github.grocerybooking.entity.Order;
import com.github.grocerybooking.entity.OrderRequest;
import com.github.grocerybooking.service.GroceryItemService;
import com.github.grocerybooking.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private GroceryItemService groceryItemService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/groceries")
    public ResponseEntity<ApiResponse> getAllAvailableGroceries() {
        logger.info("Fetching all available grocery items for the user.");
        List<GroceryItem> groceries = groceryItemService.getAllGroceries();
        ApiResponse response = new ApiResponse("Fetched available grocery items successfully", groceries, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/order")
    public ResponseEntity<ApiResponse> placeOrder(@RequestBody OrderRequest orderRequest) {
        logger.info("Received request to place an order for customer: {}", orderRequest.getCustomerName());
        Order order = orderService.placeOrder(orderRequest);
        ApiResponse response = new ApiResponse("Order placed successfully", order, true);
        logger.info("Order placed successfully with ID: {}", order.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

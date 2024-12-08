package com.github.grocerybooking.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.github.grocerybooking.entity.ApiResponse;
import com.github.grocerybooking.entity.GroceryItem;
import com.github.grocerybooking.service.GroceryItemService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private GroceryItemService groceryItemService;

    @PostMapping("/groceries")
    public ResponseEntity<ApiResponse> addGroceryItem(@RequestBody GroceryItem groceryItem) {
        logger.info("Received request to add a grocery item: {}", groceryItem.getName());
        GroceryItem addedItem = groceryItemService.addGroceryItem(groceryItem);
        ApiResponse response = new ApiResponse("Grocery item added successfully", addedItem, true);
        logger.info("Grocery item added successfully: {}", addedItem.getName());
        return new ResponseEntity<>(response, HttpStatus.CREATED); 
    }

    @GetMapping("/groceries")
    public ResponseEntity<ApiResponse> getAllGroceries() {
        logger.info("Fetching all grocery items.");
        List<GroceryItem> groceries = groceryItemService.getAllGroceries();
        ApiResponse response = new ApiResponse("Fetched grocery items successfully", groceries, true);
        logger.info("Fetched {} grocery items.", groceries.size());
        return new ResponseEntity<>(response, HttpStatus.OK); 
    }

    @PutMapping("/groceries/{id}")
    public ResponseEntity<ApiResponse> updateGroceryItem(@PathVariable Long id, @RequestBody GroceryItem groceryItem) {
        logger.info("Updating grocery item with id {}: {}", id, groceryItem.getName());
        GroceryItem updatedItem = groceryItemService.updateGroceryItem(id, groceryItem);
        ApiResponse response;
        if (updatedItem != null) {
            response = new ApiResponse("Grocery item updated successfully", updatedItem, true);
            logger.info("Grocery item updated successfully: {}", updatedItem.getName());
        } else {
            response = new ApiResponse("Grocery item not found", false);
            logger.warn("Grocery item with id {} not found for update.", id);
        }
        return new ResponseEntity<>(response, updatedItem != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/groceries/{id}")
    public ResponseEntity<ApiResponse> deleteGroceryItem(@PathVariable Long id) throws IllegalAccessException {
        logger.info("Request received to delete grocery item with id: {}", id);
        groceryItemService.deleteGroceryItem(id);
        ApiResponse response =new ApiResponse("Grocery item deleted successfully", true);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @PutMapping("/groceries/{id}/inventory")
    public ResponseEntity<ApiResponse> updateInventory(@PathVariable Long id, @RequestParam Integer quantity) {
        logger.info("Updating inventory for grocery item with ID: {}. New quantity: {}", id, quantity);

        GroceryItem updatedItem = groceryItemService.updateInventory(id, quantity);
        ApiResponse response = new ApiResponse("Inventory updated successfully for item: " + updatedItem.getName(), true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

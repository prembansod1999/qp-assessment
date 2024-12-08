package com.github.grocerybooking.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.grocerybooking.entity.GroceryItem;
import com.github.grocerybooking.exception.InvalidInventoryUpdateException;
import com.github.grocerybooking.exception.ItemNotFoundException;
import com.github.grocerybooking.repository.GroceryItemRepository;
import com.github.grocerybooking.service.GroceryItemService;

import java.util.List;

@Service
public class GroceryItemServiceImpl implements GroceryItemService {

    private static final Logger logger = LoggerFactory.getLogger(GroceryItemServiceImpl.class);

    @Autowired
    private GroceryItemRepository groceryItemRepository;

    @Override
    public List<GroceryItem> getAllGroceries() {
        logger.info("Fetching all grocery items from the database.");
        List<GroceryItem> groceries = groceryItemRepository.findAll();
        logger.info("Retrieved {} grocery items from the database.", groceries.size());
        return groceries;
    }

    @Override
    public GroceryItem addGroceryItem(GroceryItem groceryItem) {
        logger.info("Adding new grocery item: {}", groceryItem.getName());
        GroceryItem addedItem = groceryItemRepository.save(groceryItem);
        logger.info("Grocery item added: {}", addedItem.getName());
        return addedItem;
    }

    @Override
    public GroceryItem updateGroceryItem(Long id, GroceryItem groceryItem) {
        logger.info("Attempting to update grocery item with id: {}", id);
        if (groceryItemRepository.existsById(id)) {
            groceryItem.setId(id);
            GroceryItem updatedItem = groceryItemRepository.save(groceryItem);
            logger.info("Grocery item updated successfully: {}", updatedItem.getName());
            return updatedItem;
        } else {
            logger.warn("Grocery item with id {} not found for update.", id);
            return null;
        }
    }

    @Override
    public void deleteGroceryItem(Long id) throws IllegalAccessException {
        logger.info("Attempting to delete grocery item with id: {}", id);
        if (groceryItemRepository.existsById(id)) {
            groceryItemRepository.deleteById(id);
            logger.info("Grocery item with id {} deleted successfully.", id);
        } else {
            logger.warn("No grocery item found with id: {}", id);
            throw new ItemNotFoundException("No grocery item found with id: "+ id);
        }
    }

    @Override
    public List<GroceryItem> getGroceriesByIds(List<Long> ids) {
        logger.info("Fetching grocery items by IDs: {}", ids);
        List<GroceryItem> items = groceryItemRepository.findAllById(ids);
        logger.info("Found {} grocery items with provided IDs.", items.size());
        return items;
    }

    @Override
    public GroceryItem updateInventory(Long id, Integer quantity) {
        if (quantity < 0) {
            throw new InvalidInventoryUpdateException("Inventory quantity cannot be negative.");
        }

        GroceryItem groceryItem = groceryItemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item not found with ID: " + id));

        groceryItem.setQuantity(quantity); 
        return groceryItemRepository.save(groceryItem);
    }
}

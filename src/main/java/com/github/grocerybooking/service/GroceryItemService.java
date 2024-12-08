package com.github.grocerybooking.service;

import java.util.List;

import com.github.grocerybooking.entity.GroceryItem;

public interface GroceryItemService {

    List<GroceryItem> getAllGroceries();
    GroceryItem addGroceryItem(GroceryItem groceryItem);
    GroceryItem updateGroceryItem(Long id, GroceryItem groceryItem);
    void deleteGroceryItem(Long id) throws IllegalAccessException;
    List<GroceryItem> getGroceriesByIds(List<Long> ids);
    GroceryItem updateInventory(Long id, Integer quantity);
}

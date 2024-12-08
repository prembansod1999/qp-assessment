package com.github.grocerybooking.service;

import java.util.List;

import com.github.grocerybooking.entity.GroceryItem;
import com.github.grocerybooking.entity.Order;
import com.github.grocerybooking.entity.OrderRequest;

public interface OrderService {
    Order placeOrder(OrderRequest orderRequest);
    Double calculateTotalPrice(List<GroceryItem> groceryItems);
}

package com.github.grocerybooking.service.impl;

import com.github.grocerybooking.entity.GroceryItem;
import com.github.grocerybooking.entity.Order;
import com.github.grocerybooking.entity.OrderRequest;
import com.github.grocerybooking.exception.InsufficientInventoryException;
import com.github.grocerybooking.exception.ItemNotFoundException;
import com.github.grocerybooking.repository.GroceryItemRepository;
import com.github.grocerybooking.repository.OrderRepository;
import com.github.grocerybooking.service.OrderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private GroceryItemRepository groceryItemRepository;

 public Order placeOrder(OrderRequest orderRequest) {
        logger.info("Processing order for customer: {}", orderRequest.getCustomerName());

        double totalPrice = 0;

        for (GroceryItem orderItem : orderRequest.getGroceryItems()) {
            GroceryItem groceryItem = groceryItemRepository.findById(orderItem.getId())
                    .orElseThrow(() -> new ItemNotFoundException("Grocery item not found"));

            logger.info("Checking inventory for grocery item: {}", groceryItem.getName());

            if (groceryItem.getQuantity() < orderItem.getQuantity()) {
                logger.error("Insufficient inventory for item: {}", groceryItem.getName());
                throw new InsufficientInventoryException("Not enough inventory for " + groceryItem.getName());
            }

            if(orderItem.getQuantity()<=0){
                logger.error("Invalid Quantity for Item: {} ", orderItem.getQuantity());
                throw new InsufficientInventoryException("Please Provide Quantity Greater than 0 for "+groceryItem.getName());
            }
            groceryItem.setQuantity(groceryItem.getQuantity() - orderItem.getQuantity());
            groceryItemRepository.save(groceryItem);

            double groceryItemPrice = groceryItem.getPrice() * orderItem.getQuantity();
            totalPrice += groceryItemPrice;
            logger.info("Updated inventory for item: {}", groceryItem.getName());
            orderItem.setName(groceryItem.getName());
            orderItem.setPrice(groceryItemPrice);
        }

        Order order = new Order();
        order.setTotalPrice(totalPrice);
        order.setCustomerName(orderRequest.getCustomerName());
        order.setGroceryItem(orderRequest.getGroceryItems());
        logger.info("Order total price: {}", totalPrice);

        return orderRepository.save(order);
    }

    @Override
    public Double calculateTotalPrice(List<GroceryItem> groceryItems) {
        logger.info("Calculating total price for {} grocery items.", groceryItems.size());
        return groceryItems.stream().mapToDouble(GroceryItem::getPrice).sum();
    }
}

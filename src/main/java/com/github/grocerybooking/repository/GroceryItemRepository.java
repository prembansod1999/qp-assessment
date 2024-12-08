package com.github.grocerybooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.grocerybooking.entity.GroceryItem;

public interface GroceryItemRepository extends JpaRepository<GroceryItem, Long> {
}

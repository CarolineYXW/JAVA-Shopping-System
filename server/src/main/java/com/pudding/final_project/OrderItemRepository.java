package com.pudding.final_project;

import org.springframework.data.jpa.repository.JpaRepository;
 
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
} 
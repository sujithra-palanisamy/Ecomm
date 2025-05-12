package com.ecomm.purchase.dao;

import com.ecomm.purchase.dto.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}

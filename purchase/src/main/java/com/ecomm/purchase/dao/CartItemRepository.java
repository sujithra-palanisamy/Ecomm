package com.ecomm.purchase.dao;

import com.ecomm.purchase.dto.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}

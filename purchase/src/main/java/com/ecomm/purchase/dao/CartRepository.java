package com.ecomm.purchase.dao;

import com.ecomm.purchase.dto.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}

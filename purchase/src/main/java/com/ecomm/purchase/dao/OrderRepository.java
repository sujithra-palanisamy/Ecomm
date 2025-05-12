package com.ecomm.purchase.dao;

import com.ecomm.purchase.dto.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}

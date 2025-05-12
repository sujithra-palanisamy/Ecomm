package com.ecomm.purchase.dao;

import com.ecomm.purchase.dto.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}

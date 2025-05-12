package com.ecomm.purchase.dao;

import com.ecomm.purchase.dto.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}

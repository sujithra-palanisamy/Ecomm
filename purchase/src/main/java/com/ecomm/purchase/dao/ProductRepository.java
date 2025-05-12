package com.ecomm.purchase.dao;

import com.ecomm.purchase.dto.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}


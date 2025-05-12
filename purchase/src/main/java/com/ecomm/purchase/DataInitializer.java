package com.ecomm.purchase;

import com.ecomm.purchase.dao.ProductRepository;
import com.ecomm.purchase.dto.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;

    public DataInitializer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        if (productRepository.count() == 0) {
            Product p1 = new Product();
            p1.setName("Smartphone");
            p1.setDescription("Latest Android phone");
            p1.setPrice(BigDecimal.valueOf(699.99));
            p1.setStockQty(100);

            Product p2 = new Product();
            p2.setName("Wireless Headphones");
            p2.setDescription("Noise-cancelling headphones");
            p2.setPrice(BigDecimal.valueOf(199.99));
            p2.setStockQty(200);

            productRepository.saveAll(Arrays.asList(p1,p2));

            System.out.println("✅ Sample products added.");
        }
    }
}

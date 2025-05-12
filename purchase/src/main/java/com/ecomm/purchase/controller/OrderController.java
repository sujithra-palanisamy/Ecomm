package com.ecomm.purchase.controller;

import com.ecomm.purchase.dao.*;
import com.ecomm.purchase.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;
    private final ShipmentRepository shipmentRepository;

    public OrderController(CartRepository cartRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository, PaymentRepository paymentRepository, ShipmentRepository shipmentRepository) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.paymentRepository = paymentRepository;
        this.shipmentRepository = shipmentRepository;
    }

    @PostMapping("/{userId}/checkout")
    public ResponseEntity<String> checkout(@PathVariable Long userId, @RequestParam String paymentMethod) {
        Cart cart = cartRepository.findById(userId).orElse(null);
        if (cart == null || cart.getItems().isEmpty()) return ResponseEntity.badRequest().body("Cart is empty");

        Orders order = new Orders();
        order.setUserId(userId);
        order.setStatus("PENDING");

        BigDecimal total = BigDecimal.ZERO;
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItem.setOrder(order);
            order.getItems().add(orderItem);
            total = total.add(orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        }
        order.setTotalAmount(total);
        orderRepository.save(order);

        Payment payment = new Payment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getTotalAmount());
        payment.setMethod(paymentMethod);
        payment.setStatus("COMPLETED");
        payment.setPaymentDate(LocalDateTime.now());
        paymentRepository.save(payment);

        Shipment shipment = new Shipment();
        shipment.setOrderId(order.getId());
        shipment.setCarrier("DefaultCarrier");
        shipment.setStatus("INITIATED");
        shipment.setTrackingNumber(UUID.randomUUID().toString());
        shipment.setShippedDate(LocalDateTime.now());
        shipmentRepository.save(shipment);

        cart.getItems().clear();
        cartRepository.save(cart);
        return ResponseEntity.ok("Order placed. Payment processed and shipment initiated. Order ID: " + order.getId());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Orders> getOrder(@PathVariable Long orderId) {
        return orderRepository.findById(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

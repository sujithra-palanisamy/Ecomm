package com.ecomm.purchase.controller;

import com.ecomm.purchase.dao.CartItemRepository;
import com.ecomm.purchase.dao.CartRepository;
import com.ecomm.purchase.dao.ProductRepository;
import com.ecomm.purchase.dto.Cart;
import com.ecomm.purchase.dto.CartItem;
import com.ecomm.purchase.dto.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public CartController(CartRepository cartRepository, ProductRepository productRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<String> addToCart(@PathVariable Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null || quantity <= 0) return ResponseEntity.badRequest().body("Invalid product or quantity");

        Cart cart = cartRepository.findById(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            return cartRepository.save(newCart);
        });

        CartItem item = new CartItem();
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setCart(cart);
        cart.getItems().add(item);
        cartRepository.save(cart);
        return ResponseEntity.ok("Added to cart");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long userId) {
        Cart cart = cartRepository.findById(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            return cartRepository.save(newCart);
        });
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{userId}/remove/{productId}")
    public ResponseEntity<String> removeItem(@PathVariable Long userId, @PathVariable Long productId) {
        Cart cart = cartRepository.findById(userId).orElse(null);
        if (cart == null) return ResponseEntity.badRequest().body("Cart not found");

        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cartRepository.save(cart);
        return ResponseEntity.ok("Removed from cart");
    }
}

package com.example.librarymanagement.cart;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public Cart createCart() {
        return cartService.createCart();
    }

    @GetMapping("/{id}")
    public Cart getCart(@PathVariable Long id) {
        return cartService.getCart(id);
    }
    @PostMapping("/{cartId}/add")
    public Cart addBookToCart(
            @PathVariable Long cartId,
            @RequestParam Long bookId,
            @RequestParam int quantity) {

        return cartService.addBookToCart(cartId, bookId, quantity);
    }
    @DeleteMapping("/{cartId}/items/{cartItemId}")
    public Cart removeItemFromCart(
            @PathVariable Long cartId,
            @PathVariable Long cartItemId) {

        return cartService.removeItemFromCart(cartId, cartItemId);
    }
    @PutMapping("/{cartId}/items/{cartItemId}")
    public Cart updateCartItemQuantity(
            @PathVariable Long cartId,
            @PathVariable Long cartItemId,
            @RequestParam int quantity) {

        return cartService.updateCartItemQuantity(cartId, cartItemId, quantity);
    }
}
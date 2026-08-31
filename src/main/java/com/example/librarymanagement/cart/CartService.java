    package com.example.librarymanagement.cart;

    import org.springframework.stereotype.Service;
    import com.example.librarymanagement.entity.Book;
    import com.example.librarymanagement.repository.BookRepository;
    @Service
    public class CartService {

        private final CartRepository cartRepository;
        private final BookRepository bookRepository;

        public CartService(CartRepository cartRepository,
                           BookRepository bookRepository) {
            this.cartRepository = cartRepository;
            this.bookRepository = bookRepository;
        }

        public Cart createCart() {
            Cart cart = new Cart();
            return cartRepository.save(cart);
        }

        public Cart getCart(Long id) {
            return cartRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Cart not found"));
        }

        public Cart addBookToCart(Long cartId, Long bookId, int quantity) {

            Cart cart = getCart(cartId);

            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new RuntimeException("Book not found"));

            for (CartItem item : cart.getItems()) {

                if (item.getBook().getId().equals(bookId)) {
                    item.setQuantity(item.getQuantity() + quantity);
                    return cartRepository.save(cart);
                }
            }

            CartItem cartItem = new CartItem();
            cartItem.setBook(book);
            cartItem.setQuantity(quantity);
            cartItem.setCart(cart);

            cart.getItems().add(cartItem);

            return cartRepository.save(cart);
        }
        public Cart removeItemFromCart(Long cartId, Long cartItemId) {

            Cart cart = getCart(cartId);

            CartItem itemToRemove = cart.getItems().stream()
                    .filter(item -> item.getId().equals(cartItemId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Cart item not found"));

            cart.getItems().remove(itemToRemove);

            return cartRepository.save(cart);
        }
        public Cart updateCartItemQuantity(Long cartId, Long cartItemId, int quantity) {

            Cart cart = getCart(cartId);

            CartItem item = cart.getItems().stream()
                    .filter(i -> i.getId().equals(cartItemId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Cart item not found"));

            if (quantity <= 0) {
                throw new RuntimeException("Quantity must be greater than 0");
            }

            item.setQuantity(quantity);

            return cartRepository.save(cart);
        }
    }
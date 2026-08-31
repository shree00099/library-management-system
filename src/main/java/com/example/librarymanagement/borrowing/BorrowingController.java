package com.example.librarymanagement.borrowing;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrowings")
public class BorrowingController {

    private final BorrowingService borrowingService;

    public BorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    @PostMapping("/borrow")
    public Borrowing borrowBook(
            @RequestParam Long userId,
            @RequestParam Long bookId) {

        return borrowingService.borrowBook(userId, bookId);
    }

    @GetMapping("/user/{userId}")
    public List<Borrowing> getUserBorrowings(
            @PathVariable Long userId) {

        return borrowingService.getUserBorrowings(userId);
    }

    @PutMapping("/return/{borrowingId}")
    public Borrowing returnBook(
            @PathVariable Long borrowingId) {

        return borrowingService.returnBook(borrowingId);
    }
}
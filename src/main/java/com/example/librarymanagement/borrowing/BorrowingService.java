package com.example.librarymanagement.borrowing;

import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.repository.BookRepository;
import com.example.librarymanagement.user.User;
import com.example.librarymanagement.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowingService {

    private final BorrowingRepository borrowingRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BorrowingService(
            BorrowingRepository borrowingRepository,
            BookRepository bookRepository,
            UserRepository userRepository) {

        this.borrowingRepository = borrowingRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public Borrowing borrowBook(Long userId, Long bookId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (!book.isAvailable()) {
            throw new RuntimeException("Book is currently unavailable");
        }

        Borrowing borrowing = new Borrowing();

        borrowing.setUser(user);
        borrowing.setBook(book);
        borrowing.setBorrowedDate(LocalDate.now());
        borrowing.setReturned(false);

        book.setAvailable(false);
        bookRepository.save(book);

        return borrowingRepository.save(borrowing);
    }

    public List<Borrowing> getUserBorrowings(Long userId) {

        return borrowingRepository.findByUserIdAndReturnedFalse(userId);
    }

    public Borrowing returnBook(Long borrowingId) {

        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new RuntimeException("Borrowing record not found"));

        if (borrowing.isReturned()) {
            throw new RuntimeException("Book has already been returned");
        }

        borrowing.setReturned(true);
        borrowing.setReturnDate(LocalDate.now());

        Book book = borrowing.getBook();
        book.setAvailable(true);

        bookRepository.save(book);

        return borrowingRepository.save(borrowing);
    }
}
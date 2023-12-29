package com.example.library_backend.controller;

import com.example.library_backend.entity.Book;
import com.example.library_backend.responsemodels.ShelfCurrentLoansResponse;
import com.example.library_backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/secure/currentloans") // checked
    public List<ShelfCurrentLoansResponse> currentLoans() throws Exception {
        String userEmail = getCurrentUserEmail();
        return bookService.currentLoans(userEmail);
    }

    @GetMapping("/secure/currentloans/count") //checked
    public int currentLoansCount() {
        String userEmail = getCurrentUserEmail();
        return bookService.currentLoansCount(userEmail);
    }

    @GetMapping("/secure/ischeckedout/byuser") //checked
    public Boolean checkoutBookByUser(@RequestParam Long bookId) {
        String userEmail = getCurrentUserEmail();
        return bookService.checkoutBookByUser(userEmail, bookId);
    }

    @PutMapping("/secure/checkout")
    public Book checkoutBook(@RequestParam Long bookId) throws Exception {
        String userEmail = getCurrentUserEmail();
        return bookService.checkoutBook(userEmail, bookId);
    }

    @PutMapping("/secure/return") //checked
    public void returnBook(@RequestParam Long bookId) throws Exception {
        String userEmail = getCurrentUserEmail();
        bookService.returnBook(userEmail, bookId);
    }

    @PutMapping("/secure/renew/loan") //checked
    public void renewLoan(@RequestParam Long bookId) throws Exception {
        String userEmail = getCurrentUserEmail();
        bookService.renewLoan(userEmail, bookId);
    }

    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Retrieve the user's email or username from the authentication object
        // You may need to customize this based on your UserDetails implementation
        return authentication.getName();
    }
}

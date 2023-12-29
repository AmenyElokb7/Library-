package com.example.library_backend.controller;

import com.example.library_backend.requestmodels.AddBookRequest;
import com.example.library_backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PutMapping("/secure/increase/book/quantity") //checked
    public ResponseEntity<String> increaseBookQuantity(@AuthenticationPrincipal UserDetails userDetails,
                                                       @RequestParam Long bookId) {
        try {
            adminService.increaseBookQuantity(bookId);
            return ResponseEntity.ok("Book quantity increased successfully");
        } catch (Exception e) {
            // Return an error response in case of an exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error increasing book quantity: " + e.getMessage());
        }
    }


    @PutMapping("/secure/decrease/book/quantity") //checked
    public ResponseEntity<String> decreaseBookQuantity(@AuthenticationPrincipal UserDetails userDetails,
                                                       @RequestParam Long bookId) {
        try {
            adminService.decreaseBookQuantity(bookId);
            return ResponseEntity.ok("Book quantity decreased successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error decreasing book quantity: " + e.getMessage());
        }
    }


    @PostMapping("/secure/add/book") //checked
    public ResponseEntity<String> postBook(@AuthenticationPrincipal UserDetails userDetails,
                                           @RequestBody AddBookRequest addBookRequest) {
        try {
            adminService.postBook(addBookRequest);
            return ResponseEntity.ok("Book added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding the book: " + e.getMessage());
        }
    }

    @DeleteMapping("/secure/delete/book") //checked
    public ResponseEntity<String> deleteBook(@AuthenticationPrincipal UserDetails userDetails,
                                             @RequestParam Long bookId) {
        try {
            adminService.deleteBook(bookId);
            return ResponseEntity.ok("Book deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting the book: " + e.getMessage());
        }
    }

}

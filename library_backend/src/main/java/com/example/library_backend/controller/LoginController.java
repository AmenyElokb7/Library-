package com.example.library_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {

        @GetMapping("/login")
        public String login() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication.isAuthenticated()) {
                // You can return user-specific information here if needed
                return "Logged in as " + authentication.getName();
            } else {
                return "Authentication failed";
            }
        }
    @GetMapping("/userDetails")
    public ResponseEntity<?> getUserDetails(Authentication authentication) {
        // Assuming the 'Authentication' object contains the necessary user details
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("username", authentication.getName());
        userDetails.put("role", authentication.getAuthorities().toString()); // Simplified role extraction

        return ResponseEntity.ok(userDetails);
    }


}

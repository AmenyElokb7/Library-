package com.example.library_backend.controller;

import com.example.library_backend.entity.Message;
import com.example.library_backend.requestmodels.AdminQuestionRequest;
import com.example.library_backend.service.MessagesService;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/messages")
@AllArgsConstructor

public class MessagesController {

    private MessagesService messagesService;


    @PostMapping("/secure/add/message")
    public void postMessage(@AuthenticationPrincipal UserDetails userDetails,
                            @RequestBody Message messageRequest) {
        String userEmail = userDetails.getUsername();
        messagesService.postMessage(messageRequest, userEmail);
    }

    @PutMapping("/secure/admin/message")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void putMessage(@AuthenticationPrincipal UserDetails userDetails,
                           @RequestBody AdminQuestionRequest adminQuestionRequest) throws Exception {
        String userEmail = userDetails.getUsername();
        messagesService.putMessage(adminQuestionRequest, userEmail);
    }
    @GetMapping("/search/findByUserEmail")
    public ResponseEntity<?> findMessagesByUserEmail(
            @RequestParam String userEmail,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        // Logic to fetch messages from the service
        Page<Message> messagesPage = messagesService.findMessagesByUserEmail(userEmail, page, size);
        if(messagesPage.getContent().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("messages", messagesPage.getContent());
        response.put("currentPage", messagesPage.getNumber());
        response.put("totalItems", messagesPage.getTotalElements());
        response.put("totalPages", messagesPage.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

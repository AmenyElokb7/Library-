package com.example.library_backend.service;

import com.example.library_backend.dao.MessageRepository;
import com.example.library_backend.entity.Message;
import com.example.library_backend.requestmodels.AdminQuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class MessagesService {

    private MessageRepository messageRepository;

    @Autowired
    public MessagesService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void postMessage(Message messageRequest, String userEmail) {
        Message message = new Message(messageRequest.getTitle(), messageRequest.getQuestion());
        message.setUserEmail(userEmail);
        messageRepository.save(message);
    }
    public Page<Message> findMessagesByUserEmail(String userEmail, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return messageRepository.findByUserEmail(userEmail, pageable);
    }

    public void putMessage(AdminQuestionRequest adminQuestionRequest, String userEmail) throws Exception {
        Optional<Message> message = messageRepository.findById(adminQuestionRequest.getId());
        if (!message.isPresent()) {
            throw new Exception("Message not found");
        }

        message.get().setAdminEmail(userEmail);
        message.get().setResponse(adminQuestionRequest.getResponse());
        message.get().setClosed(true);
        messageRepository.save(message.get());
    }

}

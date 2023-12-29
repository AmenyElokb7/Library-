package com.example.library_backend.dao;

import com.example.library_backend.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findByUserEmail(@RequestParam("user_mail") String userEmail, Pageable pageable);
    Page<Message> findByClosed(@RequestParam("closed") boolean closed, Pageable pageable);

}

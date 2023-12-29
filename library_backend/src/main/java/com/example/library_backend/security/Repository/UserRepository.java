package com.example.library_backend.security.Repository;

import com.example.library_backend.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, String> {
    public AppUser findAppUserByUsername(String userName);

    Optional<Object> findByUsername(String username);
}

package com.example.library_backend.security.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.library_backend.security.entities.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
    AppUser findByUsername(String username);
}

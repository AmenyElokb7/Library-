package com.example.library_backend.security.Repository;

import com.example.library_backend.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<AppRole,String> {
    AppRole findByRole(String role);
}

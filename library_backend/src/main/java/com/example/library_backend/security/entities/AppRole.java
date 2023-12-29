package com.example.library_backend.security.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AppRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key for the role entity

    @Column(unique = true) // This ensures that each role name is unique within the table
    private String role; // The name of the role

    public String getRole() {
        return this.role;
    }

    public void setRole(String roleName) {
        this.role = roleName;
    }
}
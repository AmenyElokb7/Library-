package com.example.library_backend.security.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String mail;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id") // This specifies the column used for joining with the role table
    private AppRole role; // Now this is a single role rather than a list

    public void setAppRole(AppRole userRole) {
        this.role = userRole;
    }

    public AppRole getAppRole() {
        return this.role;
    }
}

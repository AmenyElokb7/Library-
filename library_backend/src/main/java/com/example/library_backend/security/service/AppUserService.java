package com.example.library_backend.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.library_backend.dao.RegisterUserDto;

import com.example.library_backend.security.Repository.AppUserRepository;
import com.example.library_backend.security.Repository.RoleRepository;
import com.example.library_backend.security.Repository.UserRepository;
import com.example.library_backend.security.entities.AppRole;
import com.example.library_backend.security.entities.AppUser;

import java.util.Collections;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private RoleRepository roleRepository;
    

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public AppUser registerUser(RegisterUserDto registerUserDto, String roleName) {
        // Create a new AppUser
        AppUser appUser = new AppUser();
        appUser.setUsername(registerUserDto.getUsername());
        appUser.setMail(registerUserDto.getMail());
    
        // Encode the user's password
        String encodedPassword = passwordEncoder.encode(registerUserDto.getPassword());
        appUser.setPassword(encodedPassword);
        // Find the role by name
        AppRole userRole = roleRepository.findByRole(roleName);
        if (userRole == null) {
            // If the role doesn't exist, create a new one
            userRole = new AppRole(null, roleName);
            roleRepository.save(userRole);
        }
    
        // Set the role to the user
        appUser.setAppRole(userRole);
    
        // Save the user
        userRepository.save(appUser);
    
        return appUser;
    }

    // You can create a method to retrieve UserDetails for authentication
    public User loadUserByUsername(String username) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser == null) {
            throw new RuntimeException("User not found: " + username);
        }

        // Set authorities (roles) for the user
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUser.getAppRole().getRole());
        return new User(appUser.getUsername(), appUser.getPassword(), Collections.singletonList(authority));
    }
}

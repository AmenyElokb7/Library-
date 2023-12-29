package com.example.library_backend.security.service;

import com.example.library_backend.security.Repository.AppUserRepository;
import com.example.library_backend.security.entities.AppRole;  // Make sure to include this import statement

import com.example.library_backend.security.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private AppUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        System.out.println("Authenticated user: " + username + " with roles: " + getAuthorities(user.getAppRole()));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                getAuthorities(user.getAppRole()) // Adjust to get a single role
        );
    }

    private Set<GrantedAuthority> getAuthorities(AppRole role) {
        // No need to add the "ROLE_" prefix since it's already present in the database.
        return Collections.singleton(new SimpleGrantedAuthority(role.getRole()));
    }
}

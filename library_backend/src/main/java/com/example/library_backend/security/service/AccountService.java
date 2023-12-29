package com.example.library_backend.security.service;

import com.example.library_backend.security.Repository.RoleRepository;
import com.example.library_backend.security.Repository.UserRepository;
import com.example.library_backend.security.entities.AppRole;
import com.example.library_backend.security.entities.AppUser;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Transactional // nzidouha besh tsavi wahdha
public class AccountService implements IAccountService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void addRole(String role) {
        if (roleRepository.findByRole(role) == null) {
            roleRepository.save(new AppRole(null, role));
        }
    }
    @Override
    public void addUser(String userName, String password, String mail) {
        if (userRepository.findAppUserByUsername(userName) != null) {
            throw new RuntimeException("User already exists");
        }
        userRepository.save(new AppUser(null, userName, passwordEncoder.encode(password), mail, null));
    }

    @Override
    public void addRoleToUser(String userName, String role) {
        AppUser user = userRepository.findAppUserByUsername(userName);
        AppRole user_role = roleRepository.findByRole(role);
        if (user_role == null) {
            throw new RuntimeException("Role does not exist");
        }
        user.setAppRole(user_role);
    }

    @Override
    public AppUser loadUserByUserName(String userName) {
        return userRepository.findAppUserByUsername(userName);
    }

    @Override
    public boolean roleExists(String string) {
        return roleRepository.findByRole(string) != null;
    }

    @Override
    public boolean userExists(String string) {
        return userRepository.findAppUserByUsername(string) != null;
    }
}

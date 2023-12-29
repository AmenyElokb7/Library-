package com.example.library_backend;

import com.example.library_backend.security.entities.AppRole;
import com.example.library_backend.security.entities.AppUser;
import com.example.library_backend.security.service.AccountService;
import com.example.library_backend.security.service.IAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class LibraryBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryBackendApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner commandLineRunner(IAccountService accountService, PasswordEncoder passwordEncoder){
        return args -> {
            // Add roles if they don't exist
            if (!accountService.roleExists("ROLE_USER")) {
                accountService.addRole("ROLE_USER");
            }
            if (!accountService.roleExists("ROLE_ADMIN")) {
                accountService.addRole("ROLE_ADMIN");
            }
    
            // Add users if they don't exist
            if (!accountService.userExists("user")) {
                accountService.addUser("user", "1234", "user@gmail.com");
                accountService.addRoleToUser("user", "ROLE_USER");
            }
            if (!accountService.userExists("admin")) {
                accountService.addUser("admin", "1234", "admin@gmail.com");
                accountService.addRoleToUser("admin", "ROLE_ADMIN");
            }
        };
    }
}

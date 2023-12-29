package com.example.library_backend.security.service;

import com.example.library_backend.security.entities.AppUser;

public interface IAccountService {

    public void addRole(String role);
    public void addUser(String userName, String password, String mail);
    public void addRoleToUser(String userName,String role);
    public AppUser loadUserByUserName(String userName);
    public boolean roleExists(String string);
    public boolean userExists(String string);
}


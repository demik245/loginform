package com.demik.LoginForm.Service;

import com.demik.LoginForm.Entity.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    void AddUser(User user);
    User findUserByUsername(String username);
}

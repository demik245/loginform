package com.demik.LoginForm.Service;

import com.demik.LoginForm.Entity.User;
import com.demik.LoginForm.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired



    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public void AddUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // Save the user to the database
        userRepo.save(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

}

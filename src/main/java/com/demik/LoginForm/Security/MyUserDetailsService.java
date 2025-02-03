package com.demik.LoginForm.Security;

import com.demik.LoginForm.Repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository; // Assume you have a JPA repository for users

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user from your database (this could be a JPA query, for example)
        com.demik.LoginForm.Entity.User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Create UserDetails object (Spring Security uses this for authentication)
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())  // Ensure the password is encoded
                .authorities("USER")  // Set roles/authorities (you can add more logic here if needed)
                .build();
    }
}

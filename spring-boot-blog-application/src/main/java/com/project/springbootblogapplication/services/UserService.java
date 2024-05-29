package com.project.springbootblogapplication.services;

import com.project.springbootblogapplication.models.User;
import com.project.springbootblogapplication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // save new account
    public User save(User user){
        // for new user update the creation date
        if(user.getId()==null){
            // encode the password
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
//            user.setCreated_at(LocalDateTime.now());
        }
        return userRepository.saveAndFlush(user);
    }

    // get user by id
    public Optional<User> getById(Long id){
        return userRepository.findById(id);
    }

    // get user by name
    public Optional<User> findByFullName(String username){
        return userRepository.findOneByFullName(username);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findOneByEmail(email);
    }

}

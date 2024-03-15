package com.project.springbootblogapplication.services;

import com.project.springbootblogapplication.models.User;
import com.project.springbootblogapplication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // save new account
    public User save(User user){
        // for new user update the creation date
        if(user.getUser_id()==null){
            user.setCreated_at(LocalDateTime.now());
        }
        return userRepository.save(user);
    }

    // get user by id
    public Optional<User> getById(Long id){
        return userRepository.findById(id);
    }

    // get user by name
    public Optional<User> findByUserName(String username){
        return userRepository.findOneByUsername(username);
    }
}

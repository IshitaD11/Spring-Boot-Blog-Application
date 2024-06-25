package com.project.springbootblogapplication.services;

import com.project.springbootblogapplication.models.PasswordResetToken;
import com.project.springbootblogapplication.repositories.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasswordResetTokenService {


    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    public Optional<PasswordResetToken> findByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }
}

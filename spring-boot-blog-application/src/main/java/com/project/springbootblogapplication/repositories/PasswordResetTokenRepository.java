package com.project.springbootblogapplication.repositories;

import com.project.springbootblogapplication.models.PasswordResetToken;
import com.project.springbootblogapplication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);

    Optional<PasswordResetToken> findByUser(User user);
}

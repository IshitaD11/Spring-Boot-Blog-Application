package com.project.springbootblogapplication.repositories;

import com.project.springbootblogapplication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findOneByUsername(String username);

    Optional<User> findOneByEmail(String email);
}

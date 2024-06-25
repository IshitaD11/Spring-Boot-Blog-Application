package com.project.springbootblogapplication.repositories;

import com.project.springbootblogapplication.models.Authority;
import com.project.springbootblogapplication.models.AuthorityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByAuthorityName(AuthorityType authorityType);
}

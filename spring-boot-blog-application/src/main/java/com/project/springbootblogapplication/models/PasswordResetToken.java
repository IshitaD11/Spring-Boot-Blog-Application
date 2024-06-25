package com.project.springbootblogapplication.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class PasswordResetToken extends BaseModel{

    private String token;
    private LocalDateTime expiryDateTime;

    @OneToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    private User user;

    public boolean isExpired(){
        return expiryDateTime.isBefore(LocalDateTime.now());
    }

}

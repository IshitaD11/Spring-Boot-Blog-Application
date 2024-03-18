package com.project.springbootblogapplication.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String username;
    private String email ;
    private String password;
    private LocalDateTime created_at;


    @OneToMany
    private List<Post> postList;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
    joinColumns = {@JoinColumn(name = "user_id" , referencedColumnName = "user_id")},
    inverseJoinColumns = {@JoinColumn(name = "authority_ID", referencedColumnName = "authority_ID")})
    private Set<Authority> authorities = new HashSet<>();

    @Override
    public String toString(){
        return "User{" + "username='" + username + "'" +
                ", email='" + email + "'" +
                ", authorities=" + authorities + "}" ;
    }
}

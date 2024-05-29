package com.project.springbootblogapplication.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseModel{

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long userId;

    private String fullName;
    private String email ;
    private String password;
//    private LocalDateTime created_at;


    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
    joinColumns = {@JoinColumn(name = "user_id" , referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")})
    private Set<Authority> authorities = new HashSet<>();

    @Override
    public String toString(){
        return "User{" + "fullName='" + fullName + "'" +
                ", email='" + email + "'" +
                ", authorities=" + authorities + "}" ;
    }

    public boolean isAdmin() {
        return authorities.stream().anyMatch(authority -> authority.getAuthorityName().equals(AuthorityType.ROLE_ADMIN));
    }
}

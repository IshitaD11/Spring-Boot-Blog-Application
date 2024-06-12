package com.project.springbootblogapplication.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class User extends BaseModel{

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long userId;

    private String fullName;
    private String email ;
    private String password;
    private Date dateOfBirth;
    private String companyName;
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

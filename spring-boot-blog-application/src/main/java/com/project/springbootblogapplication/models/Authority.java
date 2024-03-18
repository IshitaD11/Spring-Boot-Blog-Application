package com.project.springbootblogapplication.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity(name = "authority")
@Getter
@Setter
@NoArgsConstructor
public class Authority implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authority_id;

    private String authority_name;

    @ManyToMany(mappedBy = "authorities")
    private List<User> users;

    @Override
    public String toString(){
        return "Authority{" + "name='" + authority_name + "'" + "}";
    }
}

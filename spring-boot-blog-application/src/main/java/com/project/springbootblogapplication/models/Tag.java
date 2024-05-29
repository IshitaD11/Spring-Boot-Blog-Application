package com.project.springbootblogapplication.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "tags")
@Getter
@Setter
public class Tag extends BaseModel{

//    @Id
//    @GeneratedValue( strategy = GenerationType.IDENTITY)
//    private long tag_id;

    private String tagName;

    @ManyToMany(mappedBy = "tags")
    private List<Post> posts = new ArrayList<>();
}

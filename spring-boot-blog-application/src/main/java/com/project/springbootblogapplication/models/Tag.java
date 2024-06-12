package com.project.springbootblogapplication.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "tags")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Tag extends BaseModel{

//    @Id
//    @GeneratedValue( strategy = GenerationType.IDENTITY)
//    private long tag_id;

    private String tagName;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore // remove circular references
    private List<Post> posts = new ArrayList<>();
}

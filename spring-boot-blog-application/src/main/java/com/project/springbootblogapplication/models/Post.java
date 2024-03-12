package com.project.springbootblogapplication.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "posts")
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long post_id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private int author_id;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;


}

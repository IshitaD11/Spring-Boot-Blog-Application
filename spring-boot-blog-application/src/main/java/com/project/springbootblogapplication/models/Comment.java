package com.project.springbootblogapplication.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "comments")
@Getter
@Setter
public class Comment extends BaseModel{

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long commentId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User commentedBy;

    @Column(columnDefinition = "TEXT")
    private String commentText;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}

package com.project.springbootblogapplication.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "comments")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Comment extends BaseModel{

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User commentedBy;

    @Column(columnDefinition = "TEXT")
    private String commentText;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}

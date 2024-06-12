package com.project.springbootblogapplication.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "posts")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Post extends BaseModel{

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long postId;

    private String title;
    private String url;

    @Column(unique = true)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String problemStatement;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name = "post_tag", joinColumns = @JoinColumn(name = "post_id"),
    inverseJoinColumns = @JoinColumn(name = "tag_id")  )
    private List<Tag> tags = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String codeBlock;


//    @NotNull
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "post" , cascade = CascadeType.ALL )
    private List<Comment> comments = new ArrayList<>();


    public String getContentFirstLine(){
        if(content!=null && !content.isEmpty()){
            int index = Math.min(content.length(),100);
            return content.substring(0,index);
        }
        return content;
    }
}

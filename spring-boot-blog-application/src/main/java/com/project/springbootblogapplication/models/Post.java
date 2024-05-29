package com.project.springbootblogapplication.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "posts")
@Getter
@Setter
public class Post extends BaseModel{

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long postId;

    private String title;
    private String url;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name = "post_tag", joinColumns = @JoinColumn(name = "post_id"),
    inverseJoinColumns = @JoinColumn(name = "tag_id")  )
    private List<Tag> tags = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String content;


//    @NotNull
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "post" , cascade = CascadeType.ALL    )
    private List<Comment> comments = new ArrayList<>();


    public String getContentFirstLine(){
        if(content!=null && content.isEmpty()){
            int index = content.indexOf('\n');
            if(index != -1)
                return content.substring(0,index);
        }
        return content;
    }
}

package com.project.springbootblogapplication.config;

import com.project.springbootblogapplication.models.Post;
import com.project.springbootblogapplication.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

// run this to add new data to db when starting up, if no data is present already
@Component
public class SeedDataConfig implements CommandLineRunner {

    @Autowired
    private PostService postService;

    @Override
    public void run(String... args) throws Exception {
        List<Post> posts = postService.getAll();
        // if not data is available, create new dummy post
        if(posts.isEmpty()){
            Post dummyPost = new Post();
            dummyPost.setTitle("Dummy Post");
            dummyPost.setContent("This is content of dummy post");
            dummyPost.setAuthor_id(1);

            postService.save(dummyPost);
        }
    }

}

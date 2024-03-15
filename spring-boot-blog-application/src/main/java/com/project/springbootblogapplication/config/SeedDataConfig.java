package com.project.springbootblogapplication.config;

import com.project.springbootblogapplication.models.Post;
import com.project.springbootblogapplication.models.User;
import com.project.springbootblogapplication.services.PostService;
import com.project.springbootblogapplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

// run this to add new data to db when starting up, if no data is present already
@Component
public class SeedDataConfig implements CommandLineRunner {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {

        List<Post> posts = postService.getAll();
        // if not data is available, create new dummy post using admin user
        if(posts.isEmpty()){
            Post dummyPost = new Post();
            dummyPost.setTitle("Dummy Post");
            dummyPost.setContent("This is content of dummy post");

            // set user as admin user
            Optional<User> optionalAdminUser = userService.getById(1L);
            if(optionalAdminUser.isEmpty()) {
                User admin = new User();
                admin.setUsername("SYSADMIN");
                admin.setEmail("system.admin@cmp.com");
                admin.setPassword("adminpw");
                userService.save(admin);
                dummyPost.setUser(admin);
            }
            else{
                optionalAdminUser.ifPresent(dummyPost::setUser);
            }

            postService.save(dummyPost);
        }
    }

}

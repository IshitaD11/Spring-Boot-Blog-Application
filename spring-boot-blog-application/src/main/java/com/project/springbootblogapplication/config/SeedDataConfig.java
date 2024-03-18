package com.project.springbootblogapplication.config;

import com.project.springbootblogapplication.models.Authority;
import com.project.springbootblogapplication.models.Post;
import com.project.springbootblogapplication.models.User;
import com.project.springbootblogapplication.repositories.AuthorityRepository;
import com.project.springbootblogapplication.services.PostService;
import com.project.springbootblogapplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

// run this to add new data to db when starting up, if no data is present already
@Component
public class SeedDataConfig implements CommandLineRunner {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public void run(String... args) throws Exception {

        Optional<User> optionalAdminUser = userService.getById(1L);
        User admin;


        if(optionalAdminUser.isEmpty()){
            Authority adminAuthority = new Authority();
            adminAuthority.setAuthority_name("ROLE_ADMIN");
            authorityRepository.save(adminAuthority);

            Authority userAuthority = new Authority();
            userAuthority.setAuthority_name("ROLE_USER");
            authorityRepository.save(userAuthority);

            admin = new User();
            admin.setUsername("SYSADMIN");
            admin.setEmail("system.admin@cmp.com");
            admin.setPassword("adminpw");
            Set<Authority> authoritySetAdmin = new HashSet<>();
            authoritySetAdmin.add(adminAuthority);
            authoritySetAdmin.add(userAuthority);
            admin.setAuthorities(authoritySetAdmin);
            userService.save(admin);


            User otherUser = new User();
            otherUser.setUsername("user1");
            otherUser.setEmail("system.user@domain.com");
            otherUser.setPassword("userpw");
            Set<Authority> authoritySetUser = new HashSet<>();
            authoritySetUser.add(adminAuthority);
            authoritySetUser.add(userAuthority);
            admin.setAuthorities(authoritySetUser);
            userService.save(otherUser);
        }
        else{
            admin = optionalAdminUser.get();
        }
        List<Post> posts = postService.getAll();
        // if not data is available, create new dummy post using admin user
        if(posts.isEmpty()){
            Post dummyPost = new Post();
            dummyPost.setTitle("Dummy Post");
            dummyPost.setContent("This is content of dummy post");

            dummyPost.setUser(admin);
            postService.save(dummyPost);
        }
    }

}

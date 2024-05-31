package com.project.springbootblogapplication.config;

import com.project.springbootblogapplication.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SlugUpdateRunner implements CommandLineRunner {

    @Autowired
    PostService postService;

    @Override
    public void run(String... args) throws Exception {
        postService.updateSlugs();
    }
}

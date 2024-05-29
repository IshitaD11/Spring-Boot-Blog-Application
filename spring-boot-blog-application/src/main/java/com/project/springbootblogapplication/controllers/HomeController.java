package com.project.springbootblogapplication.controllers;

import com.project.springbootblogapplication.models.Post;
import com.project.springbootblogapplication.models.User;
import com.project.springbootblogapplication.services.PostService;
import com.project.springbootblogapplication.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

// this is for home page. corresponding to services.home.html
@Controller
@RequiredArgsConstructor
public class HomeController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(Model model, Authentication authentication){
        List<Post> posts = postService.getAll();
        model.addAttribute("posts",posts);

        // pass the username to show in navbar
        if(authentication!=null) {
            userService.findByEmail(authentication.getName()).ifPresent(currentUser -> model.addAttribute("fullName", currentUser.getFullName()));
        }
        return "home"; // html template for home page
    }
}

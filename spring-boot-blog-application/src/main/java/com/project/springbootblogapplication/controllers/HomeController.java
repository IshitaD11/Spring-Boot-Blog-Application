package com.project.springbootblogapplication.controllers;

import com.project.springbootblogapplication.models.Post;
import com.project.springbootblogapplication.models.Tag;
import com.project.springbootblogapplication.models.User;
import com.project.springbootblogapplication.services.PostService;
import com.project.springbootblogapplication.services.TagService;
import com.project.springbootblogapplication.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

// this is for home page. corresponding to services.home.html
@Controller
@RequiredArgsConstructor
public class HomeController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String home(Model model, Authentication authentication, @RequestParam(required = false) Set<Long> tagIds){
        List<Post> posts ;

        if (tagIds!=null && !tagIds.isEmpty()) {
            Set<Tag> selectedTags = tagIds.stream().map(tagService::getTagById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            posts = postService.findByTags(selectedTags);
        } else {
            posts = postService.findAll();
        }

        List<Tag> tags = tagService.findAll();
        model.addAttribute("posts", posts);
        model.addAttribute("tags", tags);

        // pass the username to show in navbar
        if(authentication!=null) {
            userService.findByEmail(authentication.getName()).ifPresent(currentUser -> model.addAttribute("fullName", currentUser.getFullName()));
        }
        return "home"; // html template for home page
    }
}

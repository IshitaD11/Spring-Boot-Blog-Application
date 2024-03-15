package com.project.springbootblogapplication.controllers;

import com.project.springbootblogapplication.models.Post;
import com.project.springbootblogapplication.models.User;
import com.project.springbootblogapplication.services.PostService;
import com.project.springbootblogapplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    // this is for each post
    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable Long id, Model model){
        // find post by id
        Optional<Post> optionalPost = postService.getById(id);
        // if post exists, then pass it in model
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            model.addAttribute("post",post);
            return "post"; //html template for individual post
        }
        return "404"; //return error page
    }

    // create a new post, if user not found return error page
    @GetMapping("/posts/new")
    public String createNewPost(Model model){
        Optional<User> optionalUser = userService.findByUserName("SYSADMIN");
        if(optionalUser.isPresent()){
            Post post = new Post();
            post.setUser(optionalUser.get());
            model.addAttribute("post",post);
            return "new_post";
        }
        return "404";
    }
}

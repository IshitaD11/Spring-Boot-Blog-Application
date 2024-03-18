package com.project.springbootblogapplication.controllers;

import com.project.springbootblogapplication.models.Post;
import com.project.springbootblogapplication.models.User;
import com.project.springbootblogapplication.services.PostService;
import com.project.springbootblogapplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    // added authentication
    @GetMapping("/posts/new")
    @PreAuthorize("isAuthenticated()")
    public String createNewPost(Model model){

        Post post = new Post();
        model.addAttribute("post",post);
        return "new_post";

    }

    // create new post, POST data back to DB
    @PostMapping("/posts/new")
    @PreAuthorize("isAuthenticated()")
    public String saveNewPost(@ModelAttribute Post post, Authentication authentication) throws Exception {
        // Get the currently logged-in user
        User currentUser = null;
        if(authentication!=null)
            currentUser = userService.findByEmail(authentication.getName()).orElse(null);

        if(currentUser == null)
            throw new Exception("User Account not found");

        // set the user in post
        post.setUser(currentUser);

        postService.save(post);

        // redirect to newly created post page
        return "redirect:/posts/" + post.getPost_id();
    }

    //edit post: get the post
    //New: changes done to give access to edit/delete to own user/admin
    @GetMapping("/posts/{id}/edit")
//    @PreAuthorize("isAuthenticated()")
    public String getPostForEdit(@PathVariable Long id, Model model, Authentication authentication){

        // find post by id
        Optional<Post> optionalPost = postService.getById(id);

        // if post exists and user is authenticated
        if(optionalPost.isPresent() && authentication!=null && authentication.isAuthenticated()){
            User currentUser = userService.findByEmail(authentication.getName()).orElse(null);
            Post post = optionalPost.get();

            //  if current user is own user/admin then allow
            if(currentUser != null && (currentUser.equals(post.getUser()) || currentUser.isAdmin())){
                model.addAttribute("post",post);
                return "post_edit";
            }
            else{
                return "access_denied";
            }
        }
        else{
            return "404";
        }
    }

    //edit post:update post
    @PostMapping("/posts/{id}")
    @PreAuthorize("isAuthenticated()")
    public String updatePost(@PathVariable Long id, Post post){

        // find post by id
        Optional<Post> optionalPost = postService.getById(id);
        // if post exists put it in model
        if(optionalPost.isPresent()) {
            Post existingPost = optionalPost.get();
            existingPost.setTitle(post.getTitle());
            existingPost.setContent(post.getContent());
            postService.save(existingPost);
        }
        return "redirect:/posts/" + id;
    }


    // delete post: Only admin has rights to delete post
    @GetMapping("/posts/{id}/delete")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String deletePost(@PathVariable Long id){
        // find post by id
        Optional<Post> optionalPost = postService.getById(id);
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();

            postService.delete(post);
            return "redirect:/";
        }
        else{
            return "404";
        }
    }

}

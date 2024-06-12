package com.project.springbootblogapplication.controllers;

import com.project.springbootblogapplication.models.Post;
import com.project.springbootblogapplication.models.Tag;
import com.project.springbootblogapplication.models.User;
import com.project.springbootblogapplication.services.PostService;
import com.project.springbootblogapplication.services.TagService;
import com.project.springbootblogapplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import com.project.springbootblogapplication.utils.SlugUtil;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private TagService tagService;

    // this is for each post
    @GetMapping("/posts/{slug}")
    public String getPost(@PathVariable String slug, Model model){
        // find post by id
        Optional<Post> optionalPost = postService.findBySlug(slug);
        // if post exists, then pass it in model
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            model.addAttribute("post",post);
            List<Tag> predefinedTags = tagService.findAll();
            model.addAttribute("tags",predefinedTags);
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
        List<Tag> predefinedTags = tagService.findAll();
        model.addAttribute("post",post);
        model.addAttribute("tags",predefinedTags);
        return "new_post";

    }

    // create new post, POST data back to DB
    @PostMapping("/posts/new")
    @PreAuthorize("isAuthenticated()")
    public String saveNewPost(@ModelAttribute Post post, Authentication authentication, @RequestParam("tags") List<Long> tagIds) throws Exception {

        System.out.println("Incoming tag IDs: " + tagIds); // Debug: Log incoming tag IDs

        // Get the currently logged-in user
        User currentUser = null;
        if(authentication!=null)
            currentUser = userService.findByEmail(authentication.getName()).orElse(null);

        if(currentUser == null)
            throw new Exception("User Account not found");

        // set the user in post
        post.setUser(currentUser);

        List<Tag> tags = tagService.findAllById(tagIds);
        post.setTags(tags);

        postService.save(post);

        // redirect to newly created post page
        return "redirect:/posts/" + post.getSlug();
    }

    //edit post: get the post
    //New: changes done to give access to edit/delete to own user/admin
    @GetMapping("/posts/{slug}/edit")
    public String getPostForEdit(@PathVariable String slug, Model model, Authentication authentication){

        // find post by id
        Optional<Post> optionalPost = postService.findBySlug(slug);

        // if post exists and user is authenticated
        if(optionalPost.isPresent() && authentication!=null && authentication.isAuthenticated()){
            User currentUser = userService.findByEmail(authentication.getName()).orElse(null);
            Post post = optionalPost.get();

            //  if current user is own user/admin then allow
            if(currentUser != null && (currentUser.equals(post.getUser()) || currentUser.isAdmin())){
                List<Tag> predefinedTags = tagService.findAll();
                model.addAttribute("post",post);
                model.addAttribute("tags", predefinedTags);
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
    @PostMapping("/posts/{slug}")
    @PreAuthorize("isAuthenticated()")
    public String updatePost(@PathVariable String slug, Post post,
                             @RequestParam("tags") List<Long> tagIds
//            ,
//                             @RequestParam("processedContent") String processedContent
    ){

        System.out.println("Incoming tag IDs: " + tagIds); // Debug: Log incoming tag IDs
//        System.out.println("Processed content: " + processedContent); // Debug: Log processed content

        // find post by id
        Optional<Post> optionalPost = postService.findBySlug(slug);

        // if post exists put it in model
        if(optionalPost.isPresent()) {
            Post existingPost = optionalPost.get();
            existingPost.setTitle(post.getTitle());
            existingPost.setContent(post.getContent());
            existingPost.setCodeBlock(post.getCodeBlock());
            existingPost.setProblemStatement(post.getProblemStatement());
            existingPost.setUrl(post.getUrl());
            List<Tag> tags = tagService.findAllById(tagIds);

            System.out.println("Tags found: " + tags);

            existingPost.setTags(tags);
            postService.save(existingPost);
        }
        return "redirect:/posts/" + slug;
    }


    // delete post: Only admin has rights to delete post
    @GetMapping("/posts/{slug}/delete")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String deletePost(@PathVariable String slug){
        // find post by id
        Optional<Post> optionalPost = postService.findBySlug(slug);
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

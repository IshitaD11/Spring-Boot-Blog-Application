package com.project.springbootblogapplication.controllers;

import com.project.springbootblogapplication.models.Post;
import com.project.springbootblogapplication.models.Tag;
import com.project.springbootblogapplication.models.User;
import com.project.springbootblogapplication.services.PostService;
import com.project.springbootblogapplication.services.TagService;
import com.project.springbootblogapplication.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            model.addAttribute("showNewPostIcon",true);
            return "post";
        }
        return "404"; //return error page
    }

    // create a new post, if user not found return error page
    // added authentication
    @GetMapping("/posts/new")
    @PreAuthorize("isAuthenticated()")
    public String createNewPost(Model model, HttpSession session){

        // Check if form is already Submitted, prevents resubmission
        Boolean formSubmitted = (Boolean) session.getAttribute("formSubmitted");
        if (formSubmitted != null && formSubmitted) {
            return "redirect:/";
        }

        Post post = new Post();
        List<Tag> predefinedTags = tagService.findAll();
        model.addAttribute("post",post);
        model.addAttribute("tags",predefinedTags);
        return "new_post";

    }

    // create new post, POST data back to DB
    @PostMapping("/posts/new")
    @PreAuthorize("isAuthenticated()")
    public String saveNewPost(@ModelAttribute Post post,
                              Authentication authentication,
                              @RequestParam("tags") List<Long> tagIds,
                              RedirectAttributes redirectAttributes,
                              HttpSession session) throws Exception {

        // Check if form is already Submitted, prevents resubmission
        Boolean formSubmitted = (Boolean) session.getAttribute("formSubmitted");
        if (formSubmitted != null && formSubmitted) {
            return "redirect:/";
        }

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
        redirectAttributes.addAttribute("slug", post.getSlug());

        // Set the formSubmitted attribute in the session
        session.setAttribute("formSubmitted", true);

        return "redirect:/posts/{slug}";
    }

    //edit post: get the post
    //New: changes done to give access to edit/delete to own user/admin
    @GetMapping("/posts/{slug}/edit")
    public String getPostForEdit(@PathVariable String slug,
                                 Model model,
                                 Authentication authentication,
                                 HttpSession session){

        // Check if form is already Submitted, prevents resubmission
        Boolean formEdited = (Boolean) session.getAttribute("formEdited");
        if (formEdited != null && formEdited) {
            return "redirect:/";
        }

        // find post by id
        Optional<Post> optionalPost = postService.findBySlug(slug);

        // if post exists and user is authenticated
        if(optionalPost.isPresent() && authentication!=null && authentication.isAuthenticated()){
            User currentUser = userService.findByEmail(authentication.getName()).orElse(null);
            Post post = optionalPost.get();

            //  if current user its own user/admin then allow
            if(currentUser != null && (currentUser.equals(post.getUser()) || currentUser.isAdmin())){
                List<Tag> predefinedTags = tagService.findAll();
                model.addAttribute("post",post);
                model.addAttribute("tags", predefinedTags);
                // Set attribute to indicate form is being edited
                session.setAttribute("formEdited", true);
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
                             @RequestParam("tags") List<Long> tagIds){

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

            existingPost.setTags(tags);
            postService.save(existingPost);
        }
        return "redirect:/posts/" + slug;
    }


    // delete post: Only admin has rights to delete post
    // change to own post's user has access to delete
    @GetMapping("/posts/{slug}/delete")
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String deletePost(@PathVariable String slug, Authentication authentication){
        // find post by id
        Optional<Post> optionalPost = postService.findBySlug(slug);

        // if post exists and user is authenticated
        if(optionalPost.isPresent() && authentication!=null && authentication.isAuthenticated()) {
            User currentUser = userService.findByEmail(authentication.getName()).orElse(null);
            Post post = optionalPost.get();

            //  if current user is own user/admin then allow
            if (currentUser != null && (currentUser.equals(post.getUser()) || currentUser.isAdmin())) {
                postService.delete(post);
                return "redirect:/";
            } else {
                return "access_denied";
            }
        }
        else{
            return "404";
        }
    }

}

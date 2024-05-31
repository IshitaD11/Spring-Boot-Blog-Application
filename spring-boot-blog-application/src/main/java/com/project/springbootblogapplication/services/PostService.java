package com.project.springbootblogapplication.services;

import com.project.springbootblogapplication.models.Post;
import com.project.springbootblogapplication.repositories.PostRepository;
import com.project.springbootblogapplication.utils.SlugUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    // get post by id
    public Optional<Post> getById(Long id){
        return postRepository.findById(id);
    }

    // get all posts
    public List<Post> getAll(){
        return postRepository.findAll();
    }

    // save a post
    public Post save(Post post){
        if(post.getSlug() == null) {
            // Generate and set the slug
            String slug = SlugUtil.toSlug(post.getTitle());
            post.setSlug(generateUniqueSlug(slug));
        }
        return postRepository.save(post);
    }

    // generated unique slugs, if not unique, add a number to make it unique
    private String generateUniqueSlug(String baseSlug) {
        String slug = baseSlug;
        int count = 1;
        while (postRepository.findBySlug(slug).isPresent()) {
            slug = baseSlug + "-" + count;
            count++;
        }
        return slug;
    }

    // delete post
    public void delete(Post post){
        postRepository.delete(post);
    }

    public Optional<Post> findBySlug(String slug) {
        return postRepository.findBySlug(slug);
    }

    public void updateSlugs() {
        List<Post> posts = postRepository.findAll();
        for(Post post:posts){
            save(post);
        }
    }
}

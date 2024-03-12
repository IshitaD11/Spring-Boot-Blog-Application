package com.project.springbootblogapplication.services;

import com.project.springbootblogapplication.models.Post;
import com.project.springbootblogapplication.repositories.PostRepository;
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
        // new post
        if(post.getPost_id() == null){
            post.setCreated_at(LocalDateTime.now());
            post.setUpdated_at(LocalDateTime.now());
        }
        else{
            post.setUpdated_at(LocalDateTime.now());
        }
        return postRepository.save(post);
    }
}

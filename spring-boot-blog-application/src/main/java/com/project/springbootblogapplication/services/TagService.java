package com.project.springbootblogapplication.services;

import com.project.springbootblogapplication.models.Tag;
import com.project.springbootblogapplication.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public Tag save(Tag tag){
        return tagRepository.save(tag);
    }


}

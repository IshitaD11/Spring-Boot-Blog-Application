package com.project.springbootblogapplication.services;

import com.project.springbootblogapplication.models.Tag;
import com.project.springbootblogapplication.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public Tag save(Tag tag){
        return tagRepository.save(tag);
    }


    public Optional<Tag> findByTagName(String tagName) {
        return tagRepository.findByTagName(tagName);
    }

    public List<Tag> findAllById(List<Long> tagIds) {
        return tagRepository.findAllById(tagIds);
    }

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }
}

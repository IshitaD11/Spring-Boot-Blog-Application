package com.project.springbootblogapplication.config;

import com.project.springbootblogapplication.models.Tag;
import com.project.springbootblogapplication.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class TagsInsertRunner implements CommandLineRunner {

    @Autowired
    private TagService tagService;

    @Override
    public void run(String... args) throws Exception {
        List<String> predefinedTags = Arrays.asList("Array", "Hash Table", "Math", "Bit Manipulation", "Prefix Sum",
        "Leetcode", "InterviewBit", "Dynamic Programming", "Greedy", "Graph", "Two Pointers", "String", "Queue",
                "Stack", "Priority Queue", "Heap", "Recursion", "BFS", "DFS", "Binary Tree", "Tree");

        for (String tagName : predefinedTags) {
            if (tagService.findByTagName(tagName).isEmpty()) {
                Tag tag = new Tag();
                tag.setTagName(tagName);
                tagService.save(tag);
            }
        }
    }
}

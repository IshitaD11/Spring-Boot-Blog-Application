package com.project.springbootblogapplication.controllers;

import com.project.springbootblogapplication.DTOs.PostDTO;
import com.project.springbootblogapplication.DTOs.ResponseStatus;
import com.project.springbootblogapplication.DTOs.SearchRequestDTO;
import com.project.springbootblogapplication.DTOs.SearchResponseDTO;
import com.project.springbootblogapplication.models.Post;
import com.project.springbootblogapplication.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.project.springbootblogapplication.models.Tag;
import com.project.springbootblogapplication.services.TagService;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class SearchController {

    @Autowired
    PostService postService;

    @Autowired
    TagService tagService;

    @GetMapping("/search")
    public String searchPage(Model model, @ModelAttribute("searchRequest") SearchRequestDTO searchRequest) {

        String searchTerm = searchRequest.getSearchTerm();
        List<Tag> tags = tagService.findAll();
        model.addAttribute("tags", tags);

        System.out.println("searchRequestDTO: " + searchRequest.getSearchTerm());
        List<PostDTO> searchResults = getPostDTOByTitle(searchTerm);

        System.out.println("searchResults count" + searchResults.size());

        // Create a SearchResponseDTO
        SearchResponseDTO searchResponseDTO = new SearchResponseDTO();
        searchResponseDTO.setStatus(ResponseStatus.SUCCESS);
        searchResponseDTO.setMessage("Search successful");
        searchResponseDTO.setPostDTOS(searchResults);

        System.out.println("searchResults count" + searchResponseDTO.getPostDTOS().size());

        // Add the searchResponseDTO to the model
        model.addAttribute("searchResponse", searchResponseDTO);

        return "search";
    }

    @GetMapping("/search/suggestions")
    @ResponseBody
    public List<PostDTO> getSuggestions(@RequestParam("query") String query) {

        return getPostDTOByTitle(query);
    }

    public List<PostDTO> getPostDTOByTitle(String query){
        List<Post> posts = postService.searchPostsByTitle(query);

        return posts.stream()
                .map(post -> {
                    PostDTO postDTO = new PostDTO();
                    postDTO.setId(post.getId());
                    postDTO.setTitle(post.getTitle());
                    postDTO.setSlug(post.getSlug());
                    return postDTO;
                })
                .toList();
    }

}

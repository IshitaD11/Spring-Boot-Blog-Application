package com.project.springbootblogapplication.DTOs;


import com.project.springbootblogapplication.models.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchResponseDTO {
    private ResponseStatus status;
    private String message;
    private List<PostDTO> postDTOS;

}
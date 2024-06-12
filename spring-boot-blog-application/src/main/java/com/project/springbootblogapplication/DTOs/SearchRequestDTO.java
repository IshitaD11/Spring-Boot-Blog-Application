package com.project.springbootblogapplication.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequestDTO {
    private String searchTerm;
    private String tag;
}

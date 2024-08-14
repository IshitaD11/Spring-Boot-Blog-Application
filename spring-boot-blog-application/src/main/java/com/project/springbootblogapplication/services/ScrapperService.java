package com.project.springbootblogapplication.services;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@Service
public class ScrapperService {

    private static final String GRAPHQL_URL = "https://leetcode.com/graphql";


    public String fetchProblemDescription(String url) {
        if(url == null || url.isEmpty())
            return null;
        if(!url.startsWith("https://leetcode.com/"))
            return null;

        String[] urlParts = url.split("/");

        String titleSlug = urlParts[urlParts.length-1].contains("-") ? urlParts[urlParts.length - 1] : urlParts[urlParts.length - 2];

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Referer", "https://leetcode.com");

        JSONObject body = new JSONObject();
        body.put("query", GraphQLQuery.SELECT_PROBLEM_QUERY);
        body.put("variables", new JSONObject().put("titleSlug", titleSlug));

        HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                GRAPHQL_URL,
                HttpMethod.POST,
                entity,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonResponse = new JSONObject(response.getBody());
            if (jsonResponse.has("errors")) {
                return "Error: " + jsonResponse.getJSONArray("errors").toString();
            }

            JSONObject data = jsonResponse.getJSONObject("data");
            JSONObject question = data.getJSONObject("question");

            return question.getString("content");
        } else {
            System.out.println("Failed to fetch data: " + response.getStatusCode());
            return "";
        }
    }
}

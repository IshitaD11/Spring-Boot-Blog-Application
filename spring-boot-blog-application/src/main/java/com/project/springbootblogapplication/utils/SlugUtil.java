package com.project.springbootblogapplication.utils;
import java.text.Normalizer;

public class SlugUtil {
    public static String toSlug(String input) {
        String nonLatin = "[^\\w-]";
        String whitespace = "[\\s]";

        // Normalize the input string
        String slug = Normalizer.normalize(input, Normalizer.Form.NFD);

        // Remove non-ASCII characters
        slug = slug.replaceAll(nonLatin, "");

        // Replace whitespace characters with hyphens
        slug = slug.replaceAll(whitespace, "-");

        // Remove consecutive hyphens
        slug = slug.replaceAll("-{2,}", "-");

        // Convert the slug to lowercase
        slug = slug.toLowerCase();

        return slug;
    }
}

package com.project.springbootblogapplication.repositories;

import com.project.springbootblogapplication.models.Post;
import com.project.springbootblogapplication.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findBySlug(String slug);

    @Query("SELECT DISTINCT p FROM posts p JOIN p.tags t WHERE t IN :tags")
    List<Post> findDistinctByTagsIn(List<Tag> tags);

    @Query("SELECT p FROM posts p JOIN p.tags t WHERE t IN :tags GROUP BY p HAVING COUNT(t) = :tagCount")
    List<Post> findByTagsContainingAll(@Param("tags") Set<Tag> tags, @Param("tagCount") long tagCount);

    List<Post> findByTitleContainingIgnoreCase(String title);
}

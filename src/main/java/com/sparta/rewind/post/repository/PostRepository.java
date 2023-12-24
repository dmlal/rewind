package com.sparta.rewind.post.repository;

import com.sparta.rewind.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();
    @Transactional
    void deleteByCreatedAtBefore(LocalDateTime before30MinutesAgo);
}

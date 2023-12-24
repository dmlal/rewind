package com.sparta.rewind.post.dto.response;

import com.sparta.rewind.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {

    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public PostResponseDto(Post savePost) {
        this.id = savePost.getId();
        this.title = savePost.getTitle();
        this.content = savePost.getContent();
        this.createdAt = savePost.getCreatedAt();
        this.modifiedAt = savePost.getModifiedAt();
    }
}

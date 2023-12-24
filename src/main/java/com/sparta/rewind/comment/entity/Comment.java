package com.sparta.rewind.comment.entity;

import com.sparta.rewind.comment.dto.request.CommentRequestDto;
import com.sparta.rewind.global.entity.TimeEntity;
import com.sparta.rewind.post.entity.Post;
import com.sparta.rewind.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comments")
public class Comment extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comments_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment(CommentRequestDto requestDto, User authUser, Post post) {
        this.content = requestDto.getContent();
        this.user = authUser;
        this.post = post;
    }

    public void update(String content) {
        this.content = content;
    }
}

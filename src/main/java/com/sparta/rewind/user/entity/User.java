package com.sparta.rewind.user.entity;

import com.sparta.rewind.comment.entity.Comment;
import com.sparta.rewind.global.entity.TimeEntity;
import com.sparta.rewind.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class User extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    String username;

    String password;

    @Column(unique = true)
    String email;

    @OneToMany(mappedBy = "user")
    private List<Post> postList;

    @OneToMany(mappedBy = "user")
    private List<Comment> commentList;

    public User(String username, String encode, String email) {
        this.username = username;
        this.password = encode;
        this.email = email;
    }
}

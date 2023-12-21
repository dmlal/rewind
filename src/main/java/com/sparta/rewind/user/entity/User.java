package com.sparta.rewind.user.entity;

import com.sparta.rewind.global.entity.TimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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


    public User(String username, String encode, String email) {
        super();
    }
}

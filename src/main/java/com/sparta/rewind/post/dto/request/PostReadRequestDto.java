package com.sparta.rewind.post.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class PostReadRequestDto {

    private final String title;
    private final String username;
    private final LocalDateTime createdAt;
}

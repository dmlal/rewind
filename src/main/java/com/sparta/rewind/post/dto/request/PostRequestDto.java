package com.sparta.rewind.post.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostRequestDto {

    @Size(min = 1, max = 500)
    private final String title;

    @Size(max = 5000)
    private final String content;

}

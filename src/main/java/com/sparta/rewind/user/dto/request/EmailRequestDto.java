package com.sparta.rewind.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class EmailRequestDto {
    @NotNull
    private String email;
}

package com.sparta.rewind.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
public class EmailVerificationRequestDto {

    @NotNull
    private  String email;

    @NotNull
    private  String authCode;
}

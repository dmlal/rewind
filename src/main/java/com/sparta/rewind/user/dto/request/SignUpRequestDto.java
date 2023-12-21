package com.sparta.rewind.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignUpRequestDto {

    @Pattern(regexp = "^[a-zA-Z0-9]{3,12}$", message = "영어 대소문자와 숫자로만 구성되어야 합니다.")
    @Size(min = 3, max = 12, message = "아이디는 3~12자리입니다.")
    @NotBlank
    private String username;

    @Pattern(regexp = "^[a-z0-9]{4,12}$", message = "영어 소문자와 숫자로만 구성되어야 합니다.")
    @Size(min = 4, max = 12, message = "아이디는 4~12자리입니다.")
    @NotBlank
    private String password;
    @NotBlank
    private String checkPassword;

    @Pattern(regexp = "^[a-zA-Z0-9-_]+@(naver\\.com|gmail\\.com)$", message = "naver.com 또는 gmail.com 이메일이 아닙니다.")
    private String email;

}

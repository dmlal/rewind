package com.sparta.rewind.global;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    /*
        상태코드 입력
     */

    // 200 OK
    SEND_CODE(200, "인증메일 전송 성공"),
    SUCCESS_AUTHCODE(200, "인증코드 성공"),

    // 201 CREATED
    SIGNUP(201, "회원가입 성공")


    ;

    private final int httpStatus;
    private final String message;
}

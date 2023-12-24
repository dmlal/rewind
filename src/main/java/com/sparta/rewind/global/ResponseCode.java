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
    GET_POSTS(200, "게시글 목록 조회"),
    MODIFIED_POST(200, "게시글 수정 성공"),
    MODIFIED_COMMENT(200, "댓글 수정 성공"),
    DELETED_POST(200, "게시글 삭제 성공"),
    DELETED_COMMENT(200, "댓글 삭제 성공"),

    // 201 CREATED
    SIGNUP(201, "회원가입 성공"),
    CREATED_POST(201, "게시글 작성 완료"),
    CREATED_COMMENT(201, "댓글 작성 완료")


    ;

    private final int httpStatus;
    private final String message;
}

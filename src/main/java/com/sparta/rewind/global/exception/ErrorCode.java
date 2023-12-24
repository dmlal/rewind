package com.sparta.rewind.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /*
    에러코드 적기
     */
    // 400 Bad Request
    INVALID_VALUE(400, "INVALID VALUE"),
    DOSE_NOT_MATCH_PASSWORD(400, "DOSE NOT MATCH PASSWORD"),
    CAN_N0T_SEND_AUTHENTICATION_MAIL(400, "CAN N0T SEND AUTHENTICATION MAIL"),
    FAIL_TO_AUTHENTICATION(400, "FAIL TO AUTHENTICATION"),
    FAIL_TO_AUTHORIZATION(400, "FAIL TO AUTHORIZATION"),

    // 404 NOT FOUND
    MISSING_OR_EXPIRED_AUTHCODE(404,"MISSING OR EXPIRED AUTHCODE"),
    NOT_FOUND_TOKEN(404, "NOT FOUND TOKEN"),
    NOT_FOUND_USERNAME(404, "NOT FOUND USERNAME"),

    // 409 CONFLICT : 중복
    ALREADY_EXISTS_USERNAME(409,"ALREADY EXISTS USERNAME"),


    // 422 Custom ErrorCode
    CAN_NOT_INCLUDE_USERNAME(422, "CAN NOT INCLUDE USERNAME"),

    // 433 Custom Security ErrorCode
    INVALID_JWT_SIGNATURE(433 ,"Invalid JWT signature"),
    EXPIRED_JWT_TOKEN(433,"Expired JWT token"),
    UNSUPPORTED_JWT_TOKEN(433, "Unsupported JWT token"),
    EMPTY_JWT_CLAIMS(433, "JWT claims is empty"),

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR(500, "Internal Server Error")


    ;

    private final int httpStatus;
    private final String message;
}

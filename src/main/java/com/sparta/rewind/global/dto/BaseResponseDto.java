package com.sparta.rewind.global.dto;

import com.sparta.rewind.global.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BaseResponseDto<T> {

    private final String msg;
    private final Integer statusCode;
    private final T data;

    public static <T> BaseResponseDto<T> of(String msg, Integer statusCode, T data) {
        return new BaseResponseDto<>(msg, statusCode, data);
    }

    public static <T> BaseResponseDto<T> of(ResponseCode responseCode, T data) {
        return new BaseResponseDto<>(
                responseCode.getMessage(),
                responseCode.getHttpStatus(),
                data
        );
    }

}

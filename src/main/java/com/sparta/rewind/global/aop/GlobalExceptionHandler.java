package com.sparta.rewind.global.aop;

import com.sparta.rewind.global.dto.BaseResponseDto;
import com.sparta.rewind.global.exception.ApiException;
import com.sparta.rewind.global.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // RuntimeException 에 대한 처리
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponseDto<Void>> runtimeExceptionHandler(RuntimeException e) {
        log.error("Runtime Exceptions :", e);
        return ResponseEntity.internalServerError()
                .body(
                        BaseResponseDto.of(ErrorCode.INTERNAL_SERVER_ERROR.getMessage(),
                                ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus(),null)
                );
    }

    // 객체나 파라미터 데이터값이 유효하지 않은 경우
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        log.error("handleMethodArgumentNotValidException", e);
        BindingResult bindingResult = e.getBindingResult();
        HashMap<String, String> errors = new HashMap<>();
        bindingResult.getAllErrors()
                .forEach(error -> errors.put(((FieldError) error).getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest()
                .body(
                        BaseResponseDto.of(
                                ErrorCode.INVALID_VALUE.getMessage(),
                                ErrorCode.INVALID_VALUE.getHttpStatus(),
                                errors
                        )
                );
    }

    // 요청에 맞는 파라미터가 아닌 경우
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e,
                                                                          HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                BaseResponseDto.of("누락된 파라미터 " + e.getParameterName(),
                        ErrorCode.INVALID_VALUE.getHttpStatus(), null)
        );
    }


    // ApiException 처리
    public ResponseEntity<BaseResponseDto<Void>> apiExceptionHandler(ApiException e) {
        log.error("Runtime Exceptions :", e);
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(
                        BaseResponseDto.of(e.getErrorCode().getMessage(),
                                e.getErrorCode().getHttpStatus(), null)
                );
    }
}

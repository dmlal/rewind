package com.sparta.rewind.user.controller;

import com.sparta.rewind.global.dto.BaseResponseDto;
import com.sparta.rewind.global.exception.ApiException;
import com.sparta.rewind.global.exception.ErrorCode;
import com.sparta.rewind.user.dto.request.EmailRequestDto;
import com.sparta.rewind.user.dto.request.EmailVerificationRequestDto;
import com.sparta.rewind.user.dto.request.SignUpRequestDto;
import com.sparta.rewind.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sparta.rewind.global.ResponseCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponseDto<String>> signup(@Valid @RequestBody SignUpRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseEntity.status(SIGNUP.getHttpStatus())
                .body(
                        BaseResponseDto.of(
                        SIGNUP,""
                        )
                );
    }

    @GetMapping("/signup/check")
    public Boolean checkDuplicatedUsername(@RequestParam String username) {
        return userService.checkDuplicatedUsername(username);
    }

    // 이메일 인증
    @PostMapping("/emails/verification-requests")
    public ResponseEntity<BaseResponseDto<String>> sendMessage(@Valid @RequestBody EmailRequestDto requestDto) {
        userService.sendCodeToEmail(requestDto.getEmail());
        return ResponseEntity.status(SEND_CODE.getHttpStatus())
                .body(
                        BaseResponseDto.of(
                                SEND_CODE ,""
                        )
                );
    }

    @PostMapping("/emails/verifications")
    public ResponseEntity<BaseResponseDto<String>> verificationEmail (@Valid @RequestBody EmailVerificationRequestDto requestDto) {
        Boolean isVerified = userService.vefiryCode(requestDto.getEmail(), requestDto.getAuthCode());

        if (isVerified) {
            return ResponseEntity.status(SUCCESS_AUTHCODE.getHttpStatus())
                    .body(
                            BaseResponseDto.of(
                                    SUCCESS_AUTHCODE, ""
                            )
                    );
        }else {
            throw new ApiException(ErrorCode.FAIL_TO_AUTHENTICATION);
        }
    }

}

package com.sparta.rewind.user.service;

import com.sparta.rewind.global.exception.ApiException;
import com.sparta.rewind.global.exception.ErrorCode;
import com.sparta.rewind.user.dto.request.SignUpRequestDto;
import com.sparta.rewind.user.entity.User;
import com.sparta.rewind.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final MailService mailService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignUpRequestDto requestDto) {
        if (userRepository.existsByUsername(requestDto.getUsername())) {
            throw new ApiException(ErrorCode.ALREADY_EXISTS_USERNAME);
        }
        // 닉네임과 같은 값이 포함된 경우 회원가입에 실패로 만들기
        if (requestDto.getPassword().contains(requestDto.getUsername())) {
            throw new ApiException(ErrorCode.CAN_NOT_INCLUDE_USERNAME);
        }
        // 비밀번호 확인은 비밀번호와 정확하게 일치하기
        if (!requestDto.getPassword().equals(requestDto.getCheckPassword())) {
            throw new ApiException(ErrorCode.DOSE_NOT_MATCH_PASSWORD);
        }

        User user = new User(requestDto.getUsername(), passwordEncoder.encode(requestDto.getPassword()),
                requestDto.getEmail());

        userRepository.save(user);
    }

    public Boolean checkDuplicatedUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public void sendCodeToEmail(String email) {
        String title = "이메일 인증 번호";
        String authCode = String.valueOf((int) (Math.random() * 900) + 100);
        int expiredCodeTime = 5 * 60;

        redisTemplate.opsForValue().set(email, authCode, Duration.ofSeconds(expiredCodeTime));

        // 레디스 잘 돌아가는지 확인차 찍어본 코드
//        String redisAuthCode = redisTemplate.opsForValue().get(email);
//        System.out.println("Redis에 저장된 인증 코드: " + redisAuthCode);

        mailService.sendMail(email, title, authCode);
    }


    public Boolean vefiryCode(String email, String authCode) {

        String redisAuthCode = redisTemplate.opsForValue().get(email);

        if (redisAuthCode == null) {
            throw new ApiException(ErrorCode.MISSING_OR_EXPIRED_AUTHCODE);
        }

        return redisAuthCode.equals(authCode);
    } 
}

package com.sparta.rewind.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.rewind.global.dto.request.LoginRequestDto;
import com.sparta.rewind.global.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.net.URLEncoder;
import java.rmi.ServerException;

import static com.sparta.rewind.global.security.jwt.JwtUtil.AUTH_HEADER;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/users/login");
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication)
        throws IOException, ServerException {

        String username = ((UserDetailsImpl) authentication.getPrincipal()).getUsername();

        String accessToken = jwtUtil.createToken(username);

        // 쿠키에 직접 넣기
        accessToken = URLEncoder.encode(accessToken, "utf-8").replaceAll("\\+", "%20");
        Cookie cookie = new Cookie(AUTH_HEADER, accessToken);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Override
    protected void unsuccessfulAuthentication (HttpServletRequest request, HttpServletResponse response,
                                               AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(401);
    }
}


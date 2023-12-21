package com.sparta.rewind.global.security;

import com.sparta.rewind.global.exception.ApiException;
import com.sparta.rewind.global.exception.ErrorCode;
import com.sparta.rewind.user.entity.User;
import com.sparta.rewind.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USERNAME));

        return new UserDetailsImpl(user);
    }

}

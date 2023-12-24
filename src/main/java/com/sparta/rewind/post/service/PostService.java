package com.sparta.rewind.post.service;

import com.sparta.rewind.global.exception.ApiException;
import com.sparta.rewind.global.exception.ErrorCode;
import com.sparta.rewind.post.dto.request.PostRequestDto;
import com.sparta.rewind.post.dto.response.PostResponseDto;
import com.sparta.rewind.post.entity.Post;
import com.sparta.rewind.post.repository.PostRepository;
import com.sparta.rewind.user.entity.User;
import com.sparta.rewind.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, User authUser) {
        User user = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new ApiException(ErrorCode.FAIL_TO_AUTHORIZATION)
        );

        Post post = new Post(requestDto, user);
        Post savePost = postRepository.save(post);

        return new PostResponseDto(savePost);
    }


    public List<PostResponseDto> getPostList() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(PostResponseDto::new).toList();
    }

    public PostResponseDto getPost(Long postId) {
        Post post = findByPostId(postId);

        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto updateDto(Long postId, PostRequestDto requestDto, User authUser) {
        Post post = findByPostId(postId);
        checkPermission(authUser, post);

        post.update(requestDto);

        return new PostResponseDto(post);
    }

    @Transactional
    public void deletePost(Long postId, User authUser) {
        Post post = findByPostId(postId);
        checkPermission(authUser, post);

        postRepository.delete(post);
    }

    private Post findByPostId(Long postId) {
        Post post =  postRepository.findById(postId).orElseThrow(
                () -> new ApiException(ErrorCode.NOT_FOUND_USERNAME)
        );
        return post;
    }

    private static void checkPermission(User authUser, Post post) {
        if (!authUser.getId().equals(post.getUser().getId())) {
            throw new ApiException(ErrorCode.FAIL_TO_AUTHORIZATION);
        }
    }
}

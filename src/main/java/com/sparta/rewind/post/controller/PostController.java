package com.sparta.rewind.post.controller;

import com.sparta.rewind.global.ResponseCode;
import com.sparta.rewind.global.annotation.AuthUser;
import com.sparta.rewind.global.dto.BaseResponseDto;
import com.sparta.rewind.global.security.jwt.JwtUtil;
import com.sparta.rewind.post.dto.request.PostRequestDto;
import com.sparta.rewind.post.dto.response.PostResponseDto;
import com.sparta.rewind.post.service.PostService;
import com.sparta.rewind.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final JwtUtil jwtUtil;


    @PostMapping
    public ResponseEntity<BaseResponseDto<PostResponseDto>> createPost(@Valid @RequestBody PostRequestDto requestDto, @AuthUser User authUser) {
        PostResponseDto postResponseDto = postService.createPost(requestDto, authUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                BaseResponseDto.of(ResponseCode.CREATED_POST, postResponseDto));
    }

    @GetMapping("/lists")
    public ResponseEntity<BaseResponseDto<List<PostResponseDto>>> getPostList() {
        List<PostResponseDto> responseDtos = postService.getPostList();
        return ResponseEntity.status(HttpStatus.OK).body(
                BaseResponseDto.of(ResponseCode.GET_POSTS, responseDtos)
        );
    }

    @GetMapping("/{postId}")
    public ResponseEntity<BaseResponseDto<PostResponseDto>> getPost(@PathVariable Long postId) {
        PostResponseDto responseDto = postService.getPost(postId);
        return ResponseEntity.status(HttpStatus.OK).body(
                BaseResponseDto.of(ResponseCode.GET_POSTS, responseDto)
        );
    }

    @PutMapping("/{postId}")
    public ResponseEntity<BaseResponseDto<PostResponseDto>> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto,
                                                                       @AuthUser User authUser) {
        PostResponseDto responseDto = postService.updateDto(postId, requestDto, authUser);

        return ResponseEntity.status(HttpStatus.OK).body(
                BaseResponseDto.of(ResponseCode.MODIFIED_POST, responseDto)
        );
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<BaseResponseDto<String>> deletePost(@PathVariable Long postId, @AuthUser User authUser) {
        postService.deletePost(postId, authUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                BaseResponseDto.of(ResponseCode.DELETED_POST, "")
        );
    }

}

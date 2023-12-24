package com.sparta.rewind.comment.controller;

import com.sparta.rewind.comment.dto.request.CommentRequestDto;
import com.sparta.rewind.comment.dto.response.CommentResponseDto;
import com.sparta.rewind.comment.service.CommentService;
import com.sparta.rewind.global.ResponseCode;
import com.sparta.rewind.global.annotation.AuthUser;
import com.sparta.rewind.global.dto.BaseResponseDto;
import com.sparta.rewind.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<BaseResponseDto<CommentResponseDto>> createComment(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto,
                                                                             @AuthUser User authUser) {
        CommentResponseDto responseDto = commentService.createComment(postId, requestDto, authUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                BaseResponseDto.of(ResponseCode.CREATED_POST, responseDto));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<BaseResponseDto<CommentResponseDto>> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto,
                                                                             @AuthUser User authUser) {
        CommentResponseDto responseDto = commentService.updateComment(commentId, requestDto, authUser);

        return ResponseEntity.status(HttpStatus.OK).body(
                BaseResponseDto.of(ResponseCode.MODIFIED_COMMENT, responseDto));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<BaseResponseDto<String>> deleteComment(@PathVariable Long commentId, @AuthUser User authUser) {
        commentService.deleteComment(commentId, authUser);

        return ResponseEntity.status(HttpStatus.OK).body(
                BaseResponseDto.of(ResponseCode.DELETED_COMMENT, ""));
    }
}

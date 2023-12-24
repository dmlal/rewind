package com.sparta.rewind.comment.service;

import com.sparta.rewind.comment.dto.request.CommentRequestDto;
import com.sparta.rewind.comment.dto.response.CommentResponseDto;
import com.sparta.rewind.comment.entity.Comment;
import com.sparta.rewind.comment.repository.CommentRepository;
import com.sparta.rewind.global.exception.ApiException;
import com.sparta.rewind.global.exception.ErrorCode;
import com.sparta.rewind.post.entity.Post;
import com.sparta.rewind.post.repository.PostRepository;
import com.sparta.rewind.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    public CommentResponseDto createComment(Long postId, CommentRequestDto requestDto, User authUser) {
        Post post = findByPostId(postId);

        Comment comment = new Comment(requestDto, authUser, post);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, User authUser) {
        Comment comment = findByCommentId(commentId);
        checkPermission(authUser, comment);

        comment.update(requestDto.getContent());

        return new CommentResponseDto(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, User authUser) {
        Comment comment = findByCommentId(commentId);
        checkPermission(authUser, comment);

        commentRepository.delete(comment);
    }

    private Comment findByCommentId(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ApiException(ErrorCode.NOT_FOUND_USERNAME));
        return comment;
    }

    private Post findByPostId(Long postId) {
        Post post =  postRepository.findById(postId).orElseThrow(
                () -> new ApiException(ErrorCode.NOT_FOUND_USERNAME)
        );
        return post;
    }

    private static void checkPermission(User authUser, Comment comment) {
        if (!authUser.getId().equals(comment.getUser().getId())) {
            throw new ApiException(ErrorCode.FAIL_TO_AUTHORIZATION);
        }
    }
}

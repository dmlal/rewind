package com.sparta.rewind.post.service;

import com.sparta.rewind.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final PostService postService;
    private final PostRepository postRepository;


    // 90일은 너무 깁니다. 30분으로 하시죠   1분마다 30분이 지난 게시물을 지웁니다.
    @Transactional
    @Scheduled(cron = "0 * * * * *")
    public void deleteOldPost() {
        LocalDateTime before30MinutesAgo = LocalDateTime.now().minusMinutes(30);
        postRepository.deleteByCreatedAtBefore(before30MinutesAgo);
    }
}

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

    private final PostRepository postRepository;


    /*
    90일은 너무 깁니다. 30분으로 하시죠
    1분마다 30분이 지난 게시물을 지웁니다.
    (원활한 테스트를 위해 30분으로 설정하였습니다.)
     */
    @Transactional
    @Scheduled(cron = "0 * * * * *")
    public void deleteOldPost() {
        LocalDateTime postHalfHourAgo = LocalDateTime.now().minusMinutes(30);
        postRepository.deleteByCreatedAtBefore(postHalfHourAgo);
    }

    // 90일이 지난 게시물을 자정마다 지우는 진짜 스케줄러
//    @Scheduled(cron = "0 0 0 * * *")
//    public void deleteOldPost() {
//        LocalDateTime post90DaysAgo = LocalDateTime.now().minusDays(90);
//        postRepository.deleteByCreatedAtBefore(post90DaysAgo);
//    }
}

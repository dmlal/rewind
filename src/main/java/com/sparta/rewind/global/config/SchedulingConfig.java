package com.sparta.rewind.global.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

@EnableScheduling
@Configuration
public class SchedulingConfig {
    @PostConstruct
    public void timezoneSetting() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}

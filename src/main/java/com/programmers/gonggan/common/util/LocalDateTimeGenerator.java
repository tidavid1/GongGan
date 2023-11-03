package com.programmers.gonggan.common.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LocalDateTimeGenerator implements LocalDateTimeValueStrategy {
    @Override
    public LocalDateTime generateLocalDateTime() {
        return LocalDateTime.now();
    }
}

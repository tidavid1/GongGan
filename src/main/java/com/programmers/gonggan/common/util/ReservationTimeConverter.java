package com.programmers.gonggan.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationTimeConverter {
    public static LocalDateTime convert(LocalDateTime dateTime) {
        if (dateTime.getMinute() != 0 || dateTime.getSecond() != 0 || dateTime.getNano() != 0) {
            dateTime = LocalDateTime.of(dateTime.toLocalDate(), LocalTime.of(dateTime.getHour(), 0));
        }
        return dateTime;
    }
}

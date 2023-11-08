package com.programmers.gonggan.domain.reservation.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ReservationControllerRequestDto {
    private final long placeId;
    private final String email;
    private final LocalDateTime startAt;
    private final LocalDateTime endAt;
}

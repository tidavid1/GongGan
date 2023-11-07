package com.programmers.gonggan.domain.reservation.dto;

import com.programmers.gonggan.domain.place.entity.Place;
import com.programmers.gonggan.domain.reservation.entity.Reservation;
import com.programmers.gonggan.domain.reservation.model.Email;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class ReservationServiceRequestDto {
    private final UUID reservationId;
    private final Long placeId;
    private final Email email;
    private final LocalDateTime startAt;
    private final LocalDateTime endAt;

    public Reservation toEntity(Place place, LocalDateTime createdAt) {
        return Reservation.builder()
                .place(place)
                .email(email)
                .startAt(startAt)
                .endAt(endAt)
                .createdAt(createdAt)
                .build();
    }
}

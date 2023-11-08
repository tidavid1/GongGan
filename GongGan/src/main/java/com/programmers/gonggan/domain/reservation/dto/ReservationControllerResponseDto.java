package com.programmers.gonggan.domain.reservation.dto;

import com.programmers.gonggan.domain.reservation.entity.Reservation;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ReservationControllerResponseDto {
    private final UUID reservationId;
    private final Long placeId;
    private final String email;
    private final LocalDateTime startAt;
    private final LocalDateTime endAt;
    private final String status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private ReservationControllerResponseDto(UUID reservationId, Long placeId, String email, LocalDateTime startAt, LocalDateTime endAt, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.reservationId = reservationId;
        this.placeId = placeId;
        this.email = email;
        this.startAt = startAt;
        this.endAt = endAt;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ReservationControllerResponseDto of(Reservation reservation) {
        return new ReservationControllerResponseDto(
                reservation.getReservationId(),
                reservation.getPlace().getPlaceId(),
                reservation.getEmail(),
                reservation.getStartAt(),
                reservation.getEndAt(),
                reservation.getStatus().name(),
                reservation.getCreatedAt(),
                reservation.getEndAt()
        );
    }
}

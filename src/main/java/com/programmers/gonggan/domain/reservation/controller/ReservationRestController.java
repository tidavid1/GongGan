package com.programmers.gonggan.domain.reservation.controller;

import com.programmers.gonggan.common.model.CommonResult;
import com.programmers.gonggan.common.util.ReservationTimeConverter;
import com.programmers.gonggan.domain.reservation.dto.ReservationControllerRequestDto;
import com.programmers.gonggan.domain.reservation.dto.ReservationControllerResponseDto;
import com.programmers.gonggan.domain.reservation.dto.ReservationServiceRequestDto;
import com.programmers.gonggan.domain.reservation.entity.Reservation;
import com.programmers.gonggan.domain.reservation.model.Email;
import com.programmers.gonggan.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservations")
public class ReservationRestController {
    private final ReservationService reservationService;

    @PostMapping
    public CommonResult<String> createReservation(@RequestBody ReservationControllerRequestDto requestDto) {
        reservationService.createReservation(ReservationServiceRequestDto.builder()
                .placeId(requestDto.getPlaceId())
                .email(Email.from(requestDto.getEmail()))
                .startAt(ReservationTimeConverter.convert(requestDto.getStartAt()))
                .endAt(ReservationTimeConverter.convert(requestDto.getEndAt()))
                .build());
        return CommonResult.getSuccessResult();
    }

    @GetMapping
    public CommonResult<List<ReservationControllerResponseDto>> findReservationsBy(
            @RequestParam @Nullable String email, @RequestParam @Nullable Long placeId) {
        List<Reservation> reservations;
        if (email != null) {
            reservations = reservationService.findReservationsByEmail(ReservationServiceRequestDto.builder()
                    .email(Email.from(email))
                    .build());
        } else if (placeId != null) {
            reservations = reservationService.findReservationsByPlace(ReservationServiceRequestDto.builder()
                    .placeId(placeId)
                    .build());
        } else {
            reservations = List.of();
        }
        return CommonResult.getListResult(reservations.stream()
                .map(ReservationControllerResponseDto::of)
                .toList());
    }

    @GetMapping("/{reservationId}")
    public CommonResult<ReservationControllerResponseDto> findReservationById(
            @PathVariable String reservationId) {
        return CommonResult.getSingleResult(ReservationControllerResponseDto.of(
                reservationService.findReservationById(
                        ReservationServiceRequestDto.builder()
                                .reservationId(UUID.fromString(reservationId))
                                .build())));
    }

    @PutMapping("/{reservationId}")
    public CommonResult<String> updateReservationById(
            @PathVariable String reservationId, @RequestBody @Nullable ReservationControllerRequestDto requestDto) {
        if (requestDto != null) {
            reservationService.updateReservationTimes(ReservationServiceRequestDto.builder()
                    .reservationId(UUID.fromString(reservationId))
                    .startAt(requestDto.getStartAt())
                    .endAt(requestDto.getEndAt())
                    .build());
        } else {
            reservationService.updateReservationStatusToCancel(ReservationServiceRequestDto.builder()
                    .reservationId(UUID.fromString(reservationId))
                    .build());
        }
        return CommonResult.getSuccessResult();
    }
}

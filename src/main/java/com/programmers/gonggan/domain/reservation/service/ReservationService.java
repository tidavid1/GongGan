package com.programmers.gonggan.domain.reservation.service;

import com.programmers.gonggan.common.util.LocalDateTimeValueStrategy;
import com.programmers.gonggan.domain.place.entity.Place;
import com.programmers.gonggan.domain.place.exception.PlaceNotFoundException;
import com.programmers.gonggan.domain.place.repository.PlaceRepository;
import com.programmers.gonggan.domain.reservation.dto.ReservationServiceRequestDto;
import com.programmers.gonggan.domain.reservation.entity.Reservation;
import com.programmers.gonggan.domain.reservation.exception.ReservationAlreadyCanceledException;
import com.programmers.gonggan.domain.reservation.exception.ReservationNotDuplicateException;
import com.programmers.gonggan.domain.reservation.exception.ReservationNotFoundException;
import com.programmers.gonggan.domain.reservation.exception.ReservationTimeSetWrongException;
import com.programmers.gonggan.domain.reservation.model.Status;
import com.programmers.gonggan.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PlaceRepository placeRepository;
    private final LocalDateTimeValueStrategy localDateTimeValueStrategy;

    public Reservation createReservation(ReservationServiceRequestDto reservationServiceRequestDto) {
        // 시작~종료 시간 준수 확인
        verifyReservationTime(reservationServiceRequestDto.getStartAt(), reservationServiceRequestDto.getEndAt());
        // 중복 시간 존재 여부 확인
        List<Reservation> reservations = findReservationsByPlaceAndDateTimes(reservationServiceRequestDto);
        verifyDuplicatedReservation(reservations, reservationServiceRequestDto.getStartAt(), reservationServiceRequestDto.getEndAt());

        Place place = placeRepository.findByPlaceId(reservationServiceRequestDto.getPlaceId()).orElseThrow(PlaceNotFoundException::new);
        return reservationRepository.save(reservationServiceRequestDto.toEntity(place, localDateTimeValueStrategy.generateLocalDateTime()));
    }

    @Transactional(readOnly = true)
    public Reservation findReservationById(ReservationServiceRequestDto reservationServiceRequestDto) {
        return reservationRepository.findById(reservationServiceRequestDto.getReservationId()).orElseThrow(ReservationNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Reservation> findReservationsByEmail(ReservationServiceRequestDto reservationServiceRequestDto) {
        return reservationRepository.findByEmail(reservationServiceRequestDto.getEmail().getEmail());
    }

    @Transactional(readOnly = true)
    public List<Reservation> findReservationsByPlaceAndDateTimes(ReservationServiceRequestDto reservationServiceRequestDto) {
        Place place = placeRepository.findByPlaceId(reservationServiceRequestDto.getPlaceId()).orElseThrow(PlaceNotFoundException::new);
        return reservationRepository.findByPlaceAndDateTimes(place, reservationServiceRequestDto.getStartAt(), reservationServiceRequestDto.getEndAt());
    }

    @Transactional(readOnly = true)
    public List<Reservation> findReservationsByPlace(ReservationServiceRequestDto reservationServiceRequestDto) {
        Place place = placeRepository.findByPlaceId(reservationServiceRequestDto.getPlaceId()).orElseThrow(PlaceNotFoundException::new);
        return reservationRepository.findByPlace(place);
    }

    @Transactional(readOnly = true)
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    public void updateReservationTimes(ReservationServiceRequestDto reservationServiceRequestDto) {
        verifyReservationTime(reservationServiceRequestDto.getStartAt(), reservationServiceRequestDto.getEndAt());
        Reservation reservation = findReservationById(reservationServiceRequestDto);
        List<Reservation> reservations = reservationRepository.findByPlaceAndDateTimes(reservation.getPlace(), reservationServiceRequestDto.getStartAt(), reservationServiceRequestDto.getEndAt());
        reservations = new ArrayList<>(reservations);
        reservations.remove(reservation);
        verifyDuplicatedReservation(reservations, reservationServiceRequestDto.getStartAt(), reservationServiceRequestDto.getEndAt());
        reservation.updateReservationTimes(reservationServiceRequestDto.getStartAt(), reservationServiceRequestDto.getEndAt(), localDateTimeValueStrategy.generateLocalDateTime());
    }

    public void updateReservationStatusToCancel(ReservationServiceRequestDto reservationServiceRequestDto) {
        Reservation reservation = findReservationById(reservationServiceRequestDto);
        if (reservation.getStatus().equals(Status.CANCELED)) {
            throw new ReservationAlreadyCanceledException();
        }
        reservation.cancelReservation(localDateTimeValueStrategy.generateLocalDateTime());
    }

    public void deleteReservationById(ReservationServiceRequestDto reservationServiceRequestDto) {
        Reservation reservation = findReservationById(reservationServiceRequestDto);
        reservationRepository.delete(reservation);
    }

    public void deleteAllReservations() {
        reservationRepository.deleteAll();
    }


    private void verifyReservationTime(LocalDateTime startAt, LocalDateTime endAt) {
        if (startAt.isAfter(endAt) || startAt.isEqual(endAt)) {
            throw new ReservationTimeSetWrongException();
        }
    }

    private void verifyDuplicatedReservation(List<Reservation> reservations, LocalDateTime startAt, LocalDateTime endAt) {
        reservations.stream()
                .filter(reservation -> reservation.getStatus().equals(Status.APPROVED))
                .filter(reservation -> reservation.isDuplicated(startAt) || reservation.isDuplicated(endAt))
                .findAny()
                .ifPresent(ignore -> {
                    throw new ReservationNotDuplicateException();
                });
    }
}


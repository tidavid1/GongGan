package com.programmers.gonggan.domain.reservation.service;

import com.programmers.gonggan.common.exception.ErrorCode;
import com.programmers.gonggan.common.util.LocalDateTimeValueStrategy;
import com.programmers.gonggan.domain.place.entity.Place;
import com.programmers.gonggan.domain.place.model.Category;
import com.programmers.gonggan.domain.place.repository.PlaceRepository;
import com.programmers.gonggan.domain.reservation.dto.ReservationServiceRequestDto;
import com.programmers.gonggan.domain.reservation.entity.Reservation;
import com.programmers.gonggan.domain.reservation.exception.ReservationNotDuplicateException;
import com.programmers.gonggan.domain.reservation.exception.ReservationNotFoundException;
import com.programmers.gonggan.domain.reservation.exception.ReservationTimeSetWrongException;
import com.programmers.gonggan.domain.reservation.model.Email;
import com.programmers.gonggan.domain.reservation.model.Status;
import com.programmers.gonggan.domain.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Reservation Service Test")
class ReservationServiceTest {

    private static final LocalDateTime DATE_TIME = LocalDateTime.now();
    private static final Long PLACE_ID = 1L;
    private static final Email EMAIL = Email.from("test@gmail.com");
    private static final LocalDateTime START_AT = LocalDateTime.now();
    private static final LocalDateTime END_AT = START_AT.plusHours(3);
    private static final Place PLACE = Place.builder()
            .name("name")
            .address("address")
            .description("description")
            .createdAt(LocalDateTime.now())
            .category(Category.OFFICE)
            .build();
    private static final Reservation RESERVATION = Reservation.builder()
            .place(PLACE)
            .email(EMAIL)
            .startAt(START_AT)
            .endAt(END_AT)
            .createdAt(DATE_TIME)
            .build();

    @InjectMocks
    private ReservationService reservationService;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private PlaceRepository placeRepository;
    @Mock
    private LocalDateTimeValueStrategy localDateTimeValueStrategy = () -> DATE_TIME;

    @Test
    void testCreateReservationSuccess() {
        // Arrange
        ReservationServiceRequestDto requestDto = ReservationServiceRequestDto.builder()
                .placeId(1L)
                .email(EMAIL)
                .startAt(START_AT)
                .endAt(END_AT)
                .build();
        when(reservationRepository.findByPlaceAndDateTimes(any(Place.class), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(List.of());
        when(placeRepository.findByPlaceId(PLACE_ID)).thenReturn(Optional.of(PLACE));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(RESERVATION);
        // Act
        Reservation actualResult = reservationService.createReservation(requestDto);
        // Assert
        verify(reservationRepository).findByPlaceAndDateTimes(any(Place.class), any(LocalDateTime.class), any(LocalDateTime.class));
        verify(placeRepository, times(2)).findByPlaceId(any(Long.TYPE));
        verify(reservationRepository).save(any(Reservation.class));
        assertThat(actualResult).isNotNull();
        assertThat(actualResult).isEqualTo(RESERVATION);
    }

    @DisplayName("Test createReservation Fail When verifyReservationTime throws Error ")
    @Test
    void testCreateReservationFailWhenDateTimeSetWrong() {
        // Arrange
        ReservationServiceRequestDto requestDto = ReservationServiceRequestDto.builder()
                .placeId(1L)
                .email(EMAIL)
                .startAt(START_AT)
                .endAt(START_AT)
                .build();
        // Act & Assert
        Throwable actualResult = assertThrows(ReservationTimeSetWrongException.class, () -> reservationService.createReservation(requestDto));
        assertThat(actualResult).hasMessage(ErrorCode.RESERVATION_TIME_SET_WRONG.getMessage());
    }

    @DisplayName("Test createReservation Fail When verifyDuplicatedReservation throws Error ")
    @Test
    void testCreateReservationFailWhenDuplicatedReservationCreated() {
        // Arrange
        ReservationServiceRequestDto requestDto = ReservationServiceRequestDto.builder()
                .placeId(1L)
                .email(EMAIL)
                .startAt(START_AT.minusHours(1))
                .endAt(END_AT.minusHours(1))
                .build();
        when(placeRepository.findByPlaceId(PLACE_ID)).thenReturn(Optional.of(PLACE));
        when(reservationRepository.findByPlaceAndDateTimes(any(Place.class), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(List.of(RESERVATION));
        // Act & Assert
        Throwable actualResult = assertThrows(ReservationNotDuplicateException.class, () -> reservationService.createReservation(requestDto));
        assertThat(actualResult).hasMessage(ErrorCode.RESERVATION_NOT_DUPLICATE.getMessage());
    }

    @Test
    void testFindReservationByIdSuccess() {
        // Arrange
        ReservationServiceRequestDto requestDto = ReservationServiceRequestDto.builder()
                .reservationId(UUID.randomUUID())
                .build();
        when(reservationRepository.findById(any(UUID.class))).thenReturn(Optional.of(RESERVATION));
        // Act
        Reservation actualResult = reservationService.findReservationById(requestDto);
        // Assert
        verify(reservationRepository).findById(any(UUID.class));
        assertThat(actualResult).isNotNull();
        assertThat(actualResult).isEqualTo(RESERVATION);
    }

    @Test
    void testFindReservationByIdFail() {
        // Arrange
        ReservationServiceRequestDto requestDto = ReservationServiceRequestDto.builder()
                .reservationId(UUID.randomUUID())
                .build();
        when(reservationRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        // Act & Assert
        Throwable actualResult = assertThrows(ReservationNotFoundException.class, () -> reservationService.findReservationById(requestDto));
        assertThat(actualResult).hasMessage(ErrorCode.RESERVATION_NOT_FOUND.getMessage());
    }

    @Test
    void testFindReservationsByEmailSuccess() {
        // Arrange
        ReservationServiceRequestDto requestDto = ReservationServiceRequestDto.builder()
                .email(EMAIL)
                .build();
        when(reservationRepository.findByEmail(any(String.class))).thenReturn(List.of(RESERVATION));
        // Act
        List<Reservation> actualResult = reservationService.findReservationsByEmail(requestDto);
        // Assert
        verify(reservationRepository).findByEmail(any(String.class));
        assertThat(actualResult).isNotNull();
        assertThat(actualResult).contains(RESERVATION);
    }

    @Test
    void testFindReservationsByPlaceAndDateTimeSuccess() {
        // Arrange
        ReservationServiceRequestDto requestDto = ReservationServiceRequestDto.builder()
                .placeId(PLACE_ID)
                .email(EMAIL)
                .startAt(START_AT)
                .endAt(END_AT)
                .build();
        when(placeRepository.findByPlaceId(any(Long.TYPE))).thenReturn(Optional.of(PLACE));
        when(reservationRepository.findByPlaceAndDateTimes(any(Place.class), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(List.of(RESERVATION));
        // Act
        List<Reservation> actualResult = reservationService.findReservationsByPlaceAndDateTimes(requestDto);
        // Assert
        verify(placeRepository).findByPlaceId(PLACE_ID);
        verify(reservationRepository).findByPlaceAndDateTimes(any(Place.class), any(LocalDateTime.class), any(LocalDateTime.class));
        assertThat(actualResult).contains(RESERVATION);
    }

    @Test
    void testFindReservationsByPlaceSuccess() {
        // Arrange
        ReservationServiceRequestDto requestDto = ReservationServiceRequestDto.builder()
                .placeId(PLACE_ID)
                .build();
        when(placeRepository.findByPlaceId(any(Long.TYPE))).thenReturn(Optional.of(PLACE));
        when(reservationRepository.findByPlace(any(Place.class))).thenReturn(List.of(RESERVATION));
        // Act
        List<Reservation> actualResult = reservationService.findReservationsByPlace(requestDto);
        // Assert
        verify(placeRepository).findByPlaceId(PLACE_ID);
        verify(reservationRepository).findByPlace(any(Place.class));
        assertThat(actualResult).contains(RESERVATION);
    }

    @Test
    void testUpdateReservationTimesSuccess() {
        // Arrange
        ReservationServiceRequestDto requestDto = ReservationServiceRequestDto.builder()
                .reservationId(UUID.randomUUID())
                .startAt(START_AT)
                .endAt(END_AT.plusHours(1))
                .build();
        Reservation expectedResult = Reservation.builder()
                .place(PLACE)
                .email(EMAIL)
                .startAt(START_AT)
                .endAt(END_AT)
                .createdAt(DATE_TIME)
                .build();
        when(reservationRepository.findById(any(UUID.class))).thenReturn(Optional.of(expectedResult));
        when(reservationRepository.findByPlaceAndDateTimes(any(Place.class), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(List.of());
        // Act
        reservationService.updateReservationTimes(requestDto);
        // Assert
        assertThat(expectedResult.getEndAt()).isEqualTo(END_AT.plusHours(1));
    }

    @Test
    void testUpdateReservationStatusToCancelSuccess() {
        // Arrange
        ReservationServiceRequestDto requestDto = ReservationServiceRequestDto.builder()
                .reservationId(UUID.randomUUID())
                .build();
        Reservation expectedResult = Reservation.builder()
                .place(PLACE)
                .email(EMAIL)
                .startAt(START_AT)
                .endAt(END_AT)
                .createdAt(DATE_TIME)
                .build();
        when(reservationRepository.findById(any(UUID.class))).thenReturn(Optional.of(expectedResult));
        // Act
        reservationService.updateReservationStatusToCancel(requestDto);
        // Assert
        assertThat(expectedResult.getStatus()).isEqualTo(Status.CANCELED);
    }
}
package com.programmers.gonggan.domain.reservation.repository;

import com.programmers.gonggan.domain.place.entity.Place;
import com.programmers.gonggan.domain.place.model.Category;
import com.programmers.gonggan.domain.place.repository.PlaceRepository;
import com.programmers.gonggan.domain.reservation.entity.Reservation;
import com.programmers.gonggan.domain.reservation.model.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Test ReservationRepository: Integration")
@Testcontainers
class ReservationRepositoryTest {

    private static final LocalDateTime CREATED_AT = LocalDateTime.now();
    private static final String NAME = "name";
    private static final String ADDRESS = "address";
    private static final Category CATEGORY = Category.OFFICE;
    private static final Place PLACE = Place.builder()
            .name(NAME)
            .address(ADDRESS)
            .description("description")
            .category(CATEGORY)
            .createdAt(CREATED_AT)
            .build();
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private PlaceRepository placeRepository;

    @BeforeEach
    void init() {
        placeRepository.save(PLACE);
    }

    @Test
    void testSaveReservation() {
        // Arrange
        Place expectedPlace = placeRepository.findByName(NAME).get(0);
        Reservation expectedResult = Reservation.builder()
                .place(expectedPlace)
                .email(Email.from("test@gmail.com"))
                .startAt(CREATED_AT)
                .endAt(CREATED_AT.plusHours(3))
                .createdAt(CREATED_AT)
                .build();
        // Act
        Reservation actualResult = reservationRepository.save(expectedResult);
        // Assert
        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getStartAt()).isEqualTo(CREATED_AT);
        assertThat(actualResult.getEndAt()).isEqualTo(CREATED_AT.plusHours(3));
        assertThat(actualResult.getCreatedAt()).isEqualTo(CREATED_AT);
        assertThat(actualResult.getPlace()).isEqualTo(expectedPlace);
        assertThat(actualResult.getEmail()).isEqualTo("test@gmail.com");
    }

    @DisplayName("Test findById when ID exist")
    @Test
    void testFindByIdWhenIdExist() {
        // Arrange
        Place place = placeRepository.findByName(NAME).get(0);
        Reservation expectedResult = reservationRepository.save(Reservation.builder()
                .place(place)
                .email(Email.from("test@gmail.com"))
                .startAt(CREATED_AT)
                .endAt(CREATED_AT.plusHours(3))
                .createdAt(CREATED_AT)
                .build());
        // Act
        Optional<Reservation> actualResult = reservationRepository.findById(expectedResult.getReservationId());
        // Assert
        assertThat(actualResult).isPresent();
        assertThat(actualResult).contains(expectedResult);
    }

    @DisplayName("Test findById when ID Not Found ")
    @Test
    void testFindByIdWhenIdNotFound(){
        // Arrange
        Place place = placeRepository.findByName(NAME).get(0);
        Reservation reservation = reservationRepository.save(Reservation.builder()
                .place(place)
                .email(Email.from("test@gmail.com"))
                .startAt(CREATED_AT)
                .endAt(CREATED_AT.plusHours(3))
                .createdAt(CREATED_AT)
                .build());
        // Act
        Optional<Reservation> actualResult = reservationRepository.findById(UUID.randomUUID());
        // Assert
        assertThat(actualResult).isEmpty();
    }

    @Test
    void testFindByEmail(){
        // Arrange
        Place place = placeRepository.findByName(NAME).get(0);
        Reservation expectedResult = reservationRepository.save(Reservation.builder()
                .place(place)
                .email(Email.from("test@gmail.com"))
                .startAt(CREATED_AT)
                .endAt(CREATED_AT.plusHours(3))
                .createdAt(CREATED_AT)
                .build());
        // Act
        List<Reservation> actualResult = reservationRepository.findByEmail("test@gmail.com");
        // Assert
        assertThat(actualResult).isNotNull();
        assertThat(actualResult).contains(expectedResult);
    }

    @Test
    void testFindByPlace(){
        // Arrange
        Place place = placeRepository.findByName(NAME).get(0);
        Reservation expectedResult = reservationRepository.save(Reservation.builder()
                .place(place)
                .email(Email.from("test@gmail.com"))
                .startAt(CREATED_AT)
                .endAt(CREATED_AT.plusHours(3))
                .createdAt(CREATED_AT)
                .build());
        // Act
        List<Reservation> actualResult = reservationRepository.findByPlace(place);
        // Assert
        assertThat(actualResult).isNotNull();
        assertThat(actualResult).contains(expectedResult);
    }

    @Test
    void testFindByPlaceAndDateTimes(){
        // Arrange
        Place place = placeRepository.findByName(NAME).get(0);
        Reservation expectedResult = reservationRepository.save(Reservation.builder()
                .place(place)
                .email(Email.from("test@gmail.com"))
                .startAt(CREATED_AT)
                .endAt(CREATED_AT.plusHours(3))
                .createdAt(CREATED_AT)
                .build());
        // Act
        List<Reservation> actualResult = reservationRepository.findByPlaceAndDateTimes(place, CREATED_AT, CREATED_AT);
        // Assert
        assertThat(actualResult).isNotNull();
        assertThat(actualResult).contains(expectedResult);
    }
}
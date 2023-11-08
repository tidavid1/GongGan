package com.programmers.gonggan.domain.reservation.controller;

import com.programmers.gonggan.domain.place.entity.Place;
import com.programmers.gonggan.domain.place.model.Category;
import com.programmers.gonggan.domain.reservation.dto.ReservationServiceRequestDto;
import com.programmers.gonggan.domain.reservation.entity.Reservation;
import com.programmers.gonggan.domain.reservation.model.Email;
import com.programmers.gonggan.domain.reservation.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationRestController.class)
class ReservationRestControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private ReservationService reservationService;

    @Test
    void testCreateReservationSuccess() throws Exception {
        // Arrange
        var post = post("/api/v1/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("""
                        {
                            "placeId": 2,
                            "email": "test@gmail.com",
                            "startAt": "2023-11-08T11:32:23.336195",
                            "endAt":"2023-11-08T14:32:23.336195"
                        }
                        """);
        // Act & Assert
        mvc.perform(post)
                .andExpect(status().isOk());
    }

    @Test
    void testFindReservationsByEmail() throws Exception{
        // Arrange
        var get = get("/api/v1/reservations")
                .param("email", "test@gmail.com");
        // Act & Assert
        mvc.perform(get)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void testFindReservationsByPlaceId() throws Exception{
        // Arrange
        var get = get("/api/v1/reservations")
                .param("placeId", String.valueOf(2L));
        // Act & Assert
        mvc.perform(get)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void testFindReservationsBy() throws Exception{
        // Arrange
        var get = get("/api/v1/reservations");
        // Act & Assert
        mvc.perform(get)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void testFindReservationById() throws Exception{
        // Arrange
        given(reservationService.findReservationById(any(ReservationServiceRequestDto.class))).willReturn(
                Reservation.builder()
                        .email(Email.from("test@gmail.com"))
                        .place(Place.builder()
                                .name("place")
                                .address("address")
                                .category(Category.STUDIO)
                                .createdAt(LocalDateTime.now())
                                .build())
                        .startAt(LocalDateTime.now())
                        .endAt(LocalDateTime.now().plusHours(3))
                        .createdAt(LocalDateTime.now())
                        .build()
        );
        var get = get(String.format("/api/v1/reservations/%s", UUID.randomUUID()));
        // Act & Assert
        mvc.perform(get)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isMap());
    }
}
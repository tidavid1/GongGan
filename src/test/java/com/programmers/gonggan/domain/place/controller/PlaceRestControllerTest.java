package com.programmers.gonggan.domain.place.controller;

import com.programmers.gonggan.domain.place.dto.PlaceServiceRequestDto;
import com.programmers.gonggan.domain.place.entity.Place;
import com.programmers.gonggan.domain.place.model.Category;
import com.programmers.gonggan.domain.place.service.PlaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Test Place RestController")
@WebMvcTest(PlaceRestControllerTest.class)
class PlaceRestControllerTest {

    private static final String NAME = "name";
    private static final Category CATEGORY = Category.OFFICE;
    private static final String CATEGORY_STR = CATEGORY.name();
    private static final Place PLACE = Place.builder()
            .placeId(1L)
            .name(NAME)
            .address("address")
            .category(CATEGORY)
            .createdAt(LocalDateTime.now())
            .build();
    @InjectMocks
    private PlaceRestController placeRestController;
    private MockMvc mvc;
    @Mock
    private PlaceService placeService;

    @BeforeEach
    void init() {
        mvc = MockMvcBuilders.standaloneSetup(placeRestController).build();
    }

    @Test
    void testFindPlacesByWhenNothingGiven() throws Exception {
        // Arrange
        given(placeService.findAllPlaces()).willReturn(List.of(PLACE));
        var get = get("/api/v1/places");
        // Act & Assert
        mvc.perform(get)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    public void testFindPlacesByCategory() throws Exception {
        // Arrange
        given(placeService.findPlacesByCategory(any(PlaceServiceRequestDto.class))).willReturn(List.of(PLACE));
        var get = get("/api/v1/places").param("category", CATEGORY_STR);
        // Act & Assert
        mvc.perform(get)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    public void testFindPlacesByName() throws Exception {
        // Arrange
        given(placeService.findPlacesByName(any(PlaceServiceRequestDto.class))).willReturn(List.of(PLACE));
        var get = get("/api/v1/places").param("name", NAME);
        // Act & Assert
        mvc.perform(get)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data").isArray());
    }
}
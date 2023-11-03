package com.programmers.gonggan.domain.place.service;

import com.programmers.gonggan.common.exception.ErrorCode;
import com.programmers.gonggan.common.util.LocalDateTimeValueStrategy;
import com.programmers.gonggan.domain.place.dto.PlaceServiceRequestDto;
import com.programmers.gonggan.domain.place.entity.Place;
import com.programmers.gonggan.domain.place.exception.PlaceAlreadyExistException;
import com.programmers.gonggan.domain.place.exception.PlaceNotFoundException;
import com.programmers.gonggan.domain.place.model.Category;
import com.programmers.gonggan.domain.place.repository.PlaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Place Service Test")
class PlaceServiceTest {

    private static final String NAME = "name";
    private static final String ADDRESS = "address";
    private static final Category CATEGORY = Category.OFFICE;
    private static final String DESCRIPTION = "description";
    private static final long PLACE_ID = 1L;
    private static final Place PLACE = Place.builder()
            .placeId(PLACE_ID)
            .name(NAME)
            .address(ADDRESS)
            .category(CATEGORY)
            .description(DESCRIPTION)
            .createdAt(LocalDateTime.parse("2023-11-03T12:49:30"))
            .build();

    @InjectMocks
    private PlaceService placeService;
    @Mock
    private PlaceRepository placeRepository;
    private final LocalDateTimeValueStrategy localDateTimeValueStrategy = () -> LocalDateTime.parse("2023-11-03T12:49:30");

    @BeforeEach
    void init() {
        placeService = new PlaceService(placeRepository, localDateTimeValueStrategy);
    }

    @Test
    void testCreatePlaceSuccess() {
        // Arrange
        PlaceServiceRequestDto expectedDTO = PlaceServiceRequestDto.builder()
                .name(NAME)
                .address(ADDRESS)
                .category(CATEGORY)
                .description(DESCRIPTION)
                .build();
        when(placeRepository.findByNameAndAddress(any(String.class), any(String.class))).thenReturn(Optional.empty());
        when(placeRepository.save(any(Place.class))).thenReturn(PLACE);
        // Act
        Place actualResult = placeService.createPlace(expectedDTO);
        // Assert
        verify(placeRepository).save(any(Place.class));
        verify(placeRepository).findByNameAndAddress(any(String.class), any(String.class));
        assertThat(actualResult).isEqualTo(PLACE);
    }

    @Test
    void testCreatePlaceFail() {
        // Arrange
        PlaceServiceRequestDto expectedDTO = PlaceServiceRequestDto.builder()
                .name(NAME)
                .address(ADDRESS)
                .category(CATEGORY)
                .description(DESCRIPTION)
                .build();
        when(placeRepository.findByNameAndAddress(any(String.class), any(String.class))).thenReturn(Optional.of(PLACE));
        // Act & Assert
        Throwable actualResult = assertThrows(PlaceAlreadyExistException.class, () -> placeService.createPlace(expectedDTO));
        assertThat(actualResult).hasMessage(ErrorCode.PLACE_ALREADY_EXIST.getMessage());
    }

    @Test
    void testFindPlaceByIdSuccess() {
        // Arrange
        PlaceServiceRequestDto expectedDTO = PlaceServiceRequestDto.builder()
                .placeId(PLACE_ID)
                .build();
        when(placeRepository.findByPlaceId(any(Long.TYPE))).thenReturn(Optional.of(PLACE));
        // Act
        Place actualResult = placeService.findPlaceById(expectedDTO);
        // Assert
        verify(placeRepository).findByPlaceId(any(Long.TYPE));
        assertThat(actualResult).isEqualTo(PLACE);
    }

    @Test
    void testFindPlaceByIdFail() {
        // Arrange
        PlaceServiceRequestDto expectedDTO = PlaceServiceRequestDto.builder()
                .placeId(2L)
                .build();
        when(placeRepository.findByPlaceId(any(Long.TYPE))).thenReturn(Optional.empty());
        // Act & Assert
        Throwable actualResult = assertThrows(PlaceNotFoundException.class, () -> placeService.findPlaceById(expectedDTO));
        assertThat(actualResult).hasMessage(ErrorCode.PLACE_NOT_FOUND.getMessage());
    }
}
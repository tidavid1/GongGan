package com.programmers.gonggan.domain.place.repository;

import com.programmers.gonggan.domain.place.entity.Place;
import com.programmers.gonggan.domain.place.model.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Test PlaceRepository: Integration")
@Testcontainers
class PlaceRepositoryTest {

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
    private PlaceRepository placeRepository;

    @Test
    void testSavePlace() {
        // Act
        Place actualResult = placeRepository.save(PLACE);
        // Assert
        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getPlaceId()).isNotNull();

        assertThat(actualResult.getName()).isEqualTo(PLACE.getName());
        assertThat(actualResult.getAddress()).isEqualTo(PLACE.getAddress());
        assertThat(actualResult.getDescription()).isEqualTo(PLACE.getDescription());
        assertThat(actualResult.getCategory()).isEqualTo(PLACE.getCategory());
        assertThat(actualResult.getCreatedAt()).isEqualTo(PLACE.getCreatedAt());
    }

    @DisplayName("test findByPlaceId when placeId exist")
    @Test
    void testFindByPlaceIdExist() {
        // Assert
        Place expectedResult = placeRepository.save(PLACE);
        // Act
        Optional<Place> actualResult = placeRepository.findByPlaceId(expectedResult.getPlaceId());
        // Assert
        assertThat(actualResult).isPresent();
        assertThat(actualResult).contains(expectedResult);
    }

    @DisplayName("test findByPlaceId when placeId Not Found")
    @Test
    void testFindByPlaceIdNotFound() {
        // Act
        Optional<Place> actualResult = placeRepository.findByPlaceId(Long.MAX_VALUE);
        // Assert
        assertThat(actualResult).isEmpty();
    }

    @Test
    void testFindByCategory() {
        // Assert
        Place expectedResult = placeRepository.save(PLACE);
        // Act
        List<Place> actualResult = placeRepository.findByCategory(CATEGORY);
        // Assert
        assertThat(actualResult).isNotNull();
        assertThat(actualResult).contains(expectedResult);
    }

    @Test
    void testFindByName() {
        // Assert
        Place expectedResult = placeRepository.save(PLACE);
        // Act
        List<Place> actualResult = placeRepository.findByName(NAME);
        // Assert
        assertThat(actualResult).isNotNull();
        assertThat(actualResult).contains(expectedResult);
    }

    @Test
    void testFindByNameAndAddress() {
        // Assert
        Place expectedResult = placeRepository.save(PLACE);
        // Act
        Optional<Place> actualResult = placeRepository.findByNameAndAddress(NAME, ADDRESS);
        // Assert
        assertThat(actualResult).isPresent();
        assertThat(actualResult).contains(expectedResult);
    }
}
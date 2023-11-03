package com.programmers.gonggan.domain.place.dto;

import com.programmers.gonggan.domain.place.entity.Place;
import com.programmers.gonggan.domain.place.model.Category;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class PlaceControllerResponseDto {
    private final Long placeId;
    private final String name;
    private final String address;
    private final String description;
    private final Category category;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static PlaceControllerResponseDto of(Place place) {
        return new PlaceControllerResponseDto(
                place.getPlaceId(),
                place.getName(),
                place.getAddress(),
                place.getDescription(),
                place.getCategory(),
                place.getCreatedAt(),
                place.getUpdatedAt()
        );
    }
}

package com.programmers.gonggan.domain.place.dto;

import com.programmers.gonggan.domain.place.entity.Place;
import com.programmers.gonggan.domain.place.model.Category;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PlaceControllerResponseDto {
    private final Long placeId;
    private final String name;
    private final String address;
    private final String description;
    private final Category category;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private PlaceControllerResponseDto(Long placeId, String name, String address, String description, Category category, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.placeId = placeId;
        this.name = name;
        this.address = address;
        this.description = description;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

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

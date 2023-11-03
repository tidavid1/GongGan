package com.programmers.gonggan.domain.place.dto;

import com.programmers.gonggan.domain.place.entity.Place;
import com.programmers.gonggan.domain.place.model.Category;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class PlaceServiceRequestDto {
    private final Long placeId;
    private final String name;
    private final String address;
    private final String description;
    private final Category category;

    public Place toEntity(LocalDateTime createdAt) {
        return Place.builder()
                .name(name)
                .address(address)
                .description(description)
                .category(category)
                .createdAt(createdAt)
                .build();
    }

    public boolean isNameExist() {
        return name != null && !name.isEmpty();
    }

    public boolean isAddressExist() {
        return address != null && !address.isEmpty();
    }

    public boolean isDescriptionExist() {
        return description != null && !description.isEmpty();
    }
}
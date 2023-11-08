package com.programmers.gonggan.domain.place.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PlaceControllerRequestDto {
    private final Long placeId;
    private final String name;
    private final String address;
    private final String description;
    private final String category;
}

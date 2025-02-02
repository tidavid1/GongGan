package com.programmers.gonggan.domain.place.controller;

import com.programmers.gonggan.common.model.CommonResult;
import com.programmers.gonggan.domain.place.dto.PlaceControllerResponseDto;
import com.programmers.gonggan.domain.place.dto.PlaceServiceRequestDto;
import com.programmers.gonggan.domain.place.entity.Place;
import com.programmers.gonggan.domain.place.model.Category;
import com.programmers.gonggan.domain.place.service.PlaceService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/places")
public class PlaceRestController {
    private final PlaceService placeService;

    @GetMapping
    public CommonResult<List<PlaceControllerResponseDto>> findPlacesBy(
            @RequestParam @Nullable String name, @RequestParam @Nullable String category) {
        List<Place> result;
        if (name != null) {
            result = placeService.findPlacesByName(PlaceServiceRequestDto.builder()
                    .name(name)
                    .build());
        } else if (category != null) {
            result = placeService.findPlacesByCategory(PlaceServiceRequestDto.builder()
                    .category(Category.of(category))
                    .build());
        } else {
            result = placeService.findAllPlaces();
        }
        return CommonResult.getListResult(result.stream()
                .map(PlaceControllerResponseDto::of)
                .toList());
    }

    @GetMapping("/{placeId}")
    public CommonResult<PlaceControllerResponseDto> findPlaceById(@PathVariable Long placeId) {
        return CommonResult.getSingleResult(
                PlaceControllerResponseDto.of(
                        placeService.findPlaceById(PlaceServiceRequestDto.builder()
                                .placeId(placeId)
                                .build())));
    }
}

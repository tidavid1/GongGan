package com.programmers.gonggan.domain.place.controller;

import com.programmers.gonggan.common.model.CommonResult;
import com.programmers.gonggan.domain.place.dto.PlaceControllerResponseDto;
import com.programmers.gonggan.domain.place.dto.PlaceServiceRequestDto;
import com.programmers.gonggan.domain.place.entity.Place;
import com.programmers.gonggan.domain.place.model.Category;
import com.programmers.gonggan.domain.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/v1/places")
@RestController
@RequiredArgsConstructor
public class PlaceRestController {
    private final PlaceService placeService;

    @GetMapping
    public CommonResult<List<PlaceControllerResponseDto>> findPlacesBy(
            @RequestParam Optional<String> name, @RequestParam Optional<String> category) {
        List<Place> result = name.map(nameStr -> placeService.findPlacesByName(
                        PlaceServiceRequestDto.builder()
                                .name(nameStr)
                                .build()))
                .orElseGet(() -> category.map(categoryStr -> placeService.findPlacesByCategory(
                                PlaceServiceRequestDto.builder()
                                        .category(Category.valueOf(categoryStr))
                                        .build()))
                        .orElseGet(placeService::findAllPlaces));
        return CommonResult.getListResult(result.stream()
                .map(PlaceControllerResponseDto::of)
                .toList());
    }
}

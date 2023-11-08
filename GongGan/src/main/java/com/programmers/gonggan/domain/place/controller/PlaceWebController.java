package com.programmers.gonggan.domain.place.controller;

import com.programmers.gonggan.domain.place.dto.PlaceControllerRequestDto;
import com.programmers.gonggan.domain.place.dto.PlaceServiceRequestDto;
import com.programmers.gonggan.domain.place.model.Category;
import com.programmers.gonggan.domain.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/places")
public class PlaceWebController {
    private final PlaceService placeService;

    @GetMapping("/create")
    public String createPlaceForm(Model model) {
        model.addAttribute("categories", Category.values());
        return "place/createForm";
    }

    @GetMapping("/list")
    public String listPlacesForm(Model model) {
        model.addAttribute("places", placeService.findAllPlaces());
        return "place/list";
    }

    @GetMapping("/update")
    public String updatePlaceForm(Model model) {
        model.addAttribute("places", placeService.findAllPlaces());
        return "place/updateForm";
    }

    @GetMapping("/delete")
    public String deletePlaceForm(Model model) {
        model.addAttribute("places", placeService.findAllPlaces());
        return "place/deleteForm";
    }

    @GetMapping("/delete-all")
    public String deleteAllPlacesForm() {
        return "place/deleteAllForm";
    }

    @PostMapping
    public String newPlace(PlaceControllerRequestDto placeControllerRequestDto) {
        placeService.createPlace(PlaceServiceRequestDto.builder()
                .name(placeControllerRequestDto.getName())
                .address(placeControllerRequestDto.getAddress())
                .description(placeControllerRequestDto.getDescription())
                .category(Category.valueOf(placeControllerRequestDto.getCategory()))
                .build());
        return "redirect:/";
    }

    @PutMapping
    public String updatePlace(PlaceControllerRequestDto placeControllerRequestDto) {
        placeService.updatePlace(PlaceServiceRequestDto.builder()
                .placeId(placeControllerRequestDto.getPlaceId())
                .name(placeControllerRequestDto.getName())
                .address(placeControllerRequestDto.getAddress())
                .description(placeControllerRequestDto.getDescription())
                .build());
        return "redirect:/";
    }

    @DeleteMapping
    public String deletePlace(PlaceControllerRequestDto placeControllerRequestDto) {
        placeService.deletePlace(PlaceServiceRequestDto.builder()
                .placeId(placeControllerRequestDto.getPlaceId())
                .build());
        return "redirect:/";
    }

    @DeleteMapping("/all")
    public String deleteAllPlaces() {
        placeService.deleteAllPlaces();
        return "redirect:/";
    }
}

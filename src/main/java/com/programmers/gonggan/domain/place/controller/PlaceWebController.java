package com.programmers.gonggan.domain.place.controller;

import com.programmers.gonggan.domain.place.dto.PlaceControllerRequestDto;
import com.programmers.gonggan.domain.place.dto.PlaceServiceRequestDto;
import com.programmers.gonggan.domain.place.model.Category;
import com.programmers.gonggan.domain.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.function.Consumer;

@Slf4j
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
    public String newPlace(PlaceControllerRequestDto placeControllerRequestDto, Model model) {
        return execute(placeService::createPlace
                , PlaceServiceRequestDto.builder()
                        .name(placeControllerRequestDto.getName())
                        .address(placeControllerRequestDto.getAddress())
                        .description(placeControllerRequestDto.getDescription())
                        .category(Category.valueOf(placeControllerRequestDto.getCategory()))
                        .build()
                , model);
    }

    @PutMapping
    public String updatePlace(PlaceControllerRequestDto placeControllerRequestDto, Model model) {
        return execute(placeService::updatePlace
                , PlaceServiceRequestDto.builder()
                        .placeId(placeControllerRequestDto.getPlaceId())
                        .name(placeControllerRequestDto.getName())
                        .address(placeControllerRequestDto.getAddress())
                        .description(placeControllerRequestDto.getDescription())
                        .build()
                , model);
    }

    @DeleteMapping
    public String deletePlace(PlaceControllerRequestDto placeControllerRequestDto, Model model) {
        return execute(placeService::deletePlace
                , PlaceServiceRequestDto.builder()
                        .placeId(placeControllerRequestDto.getPlaceId())
                        .build()
                , model);
    }

    @DeleteMapping("/all")
    public String deleteAllPlaces() {
        placeService.deleteAllPlaces();
        return "redirect:/";
    }

    private String execute(Consumer<PlaceServiceRequestDto> consumer, PlaceServiceRequestDto placeServiceRequestDto, Model model) {
        try {
            consumer.accept(placeServiceRequestDto);
        } catch (Exception e) {
            log.warn(e.toString());
            model.addAttribute("name", e.getClass().getName());
            model.addAttribute("message", e.getMessage());
            return "/error";
        }
        return "redirect:/";
    }
}

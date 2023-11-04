package com.programmers.gonggan.domain.place.service;

import com.programmers.gonggan.common.exception.ErrorCode;
import com.programmers.gonggan.common.util.LocalDateTimeValueStrategy;
import com.programmers.gonggan.domain.place.dto.PlaceServiceRequestDto;
import com.programmers.gonggan.domain.place.entity.Place;
import com.programmers.gonggan.domain.place.exception.PlaceAlreadyExistException;
import com.programmers.gonggan.domain.place.exception.PlaceNotFoundException;
import com.programmers.gonggan.domain.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final LocalDateTimeValueStrategy localDateTimeValueStrategy;

    public Place createPlace(PlaceServiceRequestDto placeServiceRequestDto) {
        placeRepository.findByNameAndAddress(placeServiceRequestDto.getName(), placeServiceRequestDto.getAddress())
                .ifPresent(place -> {
                    log.warn(ErrorCode.PLACE_ALREADY_EXIST.getMessage());
                    throw new PlaceAlreadyExistException();
                });
        return placeRepository.save(placeServiceRequestDto.toEntity(localDateTimeValueStrategy.generateLocalDateTime()));
    }

    @Transactional(readOnly = true)
    public Place findPlaceById(PlaceServiceRequestDto placeServiceRequestDto) {
        return placeRepository.findByPlaceId(placeServiceRequestDto.getPlaceId()).orElseThrow(() -> {
            log.warn(ErrorCode.PLACE_NOT_FOUND.getMessage());
            return new PlaceNotFoundException();
        });
    }

    @Transactional(readOnly = true)
    public List<Place> findPlacesByName(PlaceServiceRequestDto placeServiceRequestDto) {
        return placeRepository.findByName(placeServiceRequestDto.getName());
    }

    @Transactional(readOnly = true)
    public List<Place> findPlacesByCategory(PlaceServiceRequestDto placeServiceRequestDto) {
        return placeRepository.findByCategory(placeServiceRequestDto.getCategory());
    }

    @Transactional(readOnly = true)
    public List<Place> findAllPlaces() {
        return placeRepository.findAll();
    }

    public void updatePlace(PlaceServiceRequestDto placeServiceRequestDto) {
        Place place = findPlaceById(placeServiceRequestDto);
        if (placeServiceRequestDto.isNameExist()) {
            place.updateName(placeServiceRequestDto.getName(), localDateTimeValueStrategy.generateLocalDateTime());
        }
        if (placeServiceRequestDto.isAddressExist()) {
            place.updateAddress(placeServiceRequestDto.getAddress(), localDateTimeValueStrategy.generateLocalDateTime());
        }
        if (placeServiceRequestDto.isDescriptionExist()) {
            place.updateDescription(placeServiceRequestDto.getDescription(), localDateTimeValueStrategy.generateLocalDateTime());
        }
    }

    public void deletePlace(PlaceServiceRequestDto placeServiceRequestDto) {
        Place place = findPlaceById(placeServiceRequestDto);
        placeRepository.delete(place);
    }

    public void deleteAllPlaces() {
        placeRepository.deleteAll();
    }
}

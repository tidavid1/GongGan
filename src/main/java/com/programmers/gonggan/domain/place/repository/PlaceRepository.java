package com.programmers.gonggan.domain.place.repository;

import com.programmers.gonggan.domain.place.entity.Place;
import com.programmers.gonggan.domain.place.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    Optional<Place> findByPlaceId(@NonNull Long placeId);

    List<Place> findByCategory(@NonNull Category category);

    List<Place> findByName(@NonNull String name);

    Optional<Place> findByNameAndAddress(@NonNull String name, @NonNull String address);
}
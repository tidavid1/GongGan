package com.programmers.gonggan.domain.reservation.repository;

import com.programmers.gonggan.domain.place.entity.Place;
import com.programmers.gonggan.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    List<Reservation> findByEmail(@NonNull String email);

    List<Reservation> findByPlace(@NonNull Place place);

    @Query("SELECT r FROM Reservation r WHERE r.place = ?1 AND ((DAY(r.startAt) = DAY(?2) OR DAY(r.endAt) = DAY(?2)) OR (DAY(r.startAt) = DAY(?3) OR DAY(r.endAt) = DAY(?3)))")
    List<Reservation> findByPlaceAndDateTimes(@NonNull Place place, @NonNull LocalDateTime startAt, @NonNull LocalDateTime endAt);
}
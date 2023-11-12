package com.programmers.gonggan.domain.reservation.entity;

import com.programmers.gonggan.domain.place.entity.Place;
import com.programmers.gonggan.domain.reservation.model.Email;
import com.programmers.gonggan.domain.reservation.model.Status;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "reservation_id", nullable = false, updatable = false)
    private UUID reservationId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "place_id", nullable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Place place;

    @Column(name = "email", nullable = false)
    private String email;

    @Enumerated
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @Builder
    protected Reservation(Place place, Email email, LocalDateTime startAt, LocalDateTime endAt, LocalDateTime createdAt) {
        this.place = place;
        this.email = email.getEmail();
        this.status = Status.APPROVED;
        this.startAt = startAt;
        this.endAt = endAt;
        this.createdAt = createdAt;
    }

    public void updateReservationTimes(LocalDateTime startAt, LocalDateTime endAt, LocalDateTime updatedAt) {
        this.startAt = startAt;
        this.endAt = endAt;
        this.updatedAt = updatedAt;
    }

    public void cancelReservation(LocalDateTime updatedAt) {
        this.status = Status.CANCELED;
        this.updatedAt = updatedAt;
    }

    public boolean isDuplicated(LocalDateTime startAt, LocalDateTime endAt) {
        if (this.startAt.isEqual(startAt) || this.endAt.isEqual(endAt)){
            return true;
        }
        return !(((startAt.isAfter(this.endAt) || startAt.isEqual(this.endAt)) && endAt.isAfter(this.endAt)) ||
                (startAt.isBefore(this.startAt) && (endAt.isEqual(this.startAt) || endAt.isBefore(this.startAt))));
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Reservation that = (Reservation) o;
        return getReservationId() != null && Objects.equals(getReservationId(), that.getReservationId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
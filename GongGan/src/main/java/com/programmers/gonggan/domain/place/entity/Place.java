package com.programmers.gonggan.domain.place.entity;

import com.programmers.gonggan.domain.place.model.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "place")
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id", nullable = false)
    private Long placeId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description")
    private String description;

    @Enumerated
    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    protected Place(String name, String address, String description, Category category, LocalDateTime createdAt) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.category = category;
        this.createdAt = createdAt;
    }

    public void updateName(String name, LocalDateTime updatedAt) {
        this.name = name;
        this.updatedAt = updatedAt;
    }

    public void updateDescription(String description, LocalDateTime updatedAt) {
        this.description = description;
        this.updatedAt = updatedAt;
    }

    public void updateAddress(String address, LocalDateTime updatedAt) {
        this.address = address;
        this.updatedAt = updatedAt;
    }
}
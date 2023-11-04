package com.programmers.gonggan.domain.place.model;

import lombok.Getter;

@Getter
public enum Category {
    CONCERT_HOLE("콘서트홀"),
    PARTY_ROOM("파티룸"),
    PRACTICE_ROOM("연습실"),
    SHARED_KITCHEN("공유 주방"),
    STUDIO("스튜디오"),
    CONFERENCE_ROOM("회의실"),
    OFFICE("사무실"),
    STUDY_ROOM("스터디룸");

    private final String name;

    Category(String name) {
        this.name = name;
    }
}

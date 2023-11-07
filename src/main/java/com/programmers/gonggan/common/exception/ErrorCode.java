package com.programmers.gonggan.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    UNKNOWN(-9999, "알 수 없는 오류"),
    PLACE_NOT_FOUND(1000, "공간을 찾을 수 없습니다."),
    PLACE_ALREADY_EXIST(1001, "이미 존재하는 공간입니다."),
    CATEGORY_NOT_FOUND(1002, "카테고리를 찾을 수 없습니다."),
    EMAIL_TYPE_NOT_MATCH(1003, "이메일 형식이 잘못되었습니다."),
    RESERVATION_NOT_FOUND(1004, "예약을 찾을 수 없습니다."),
    RESERVATION_NOT_DUPLICATE(1005, "이미 예약이 존재합니다."),
    RESERVATION_TIME_SET_WRONG(1006, "예약 시간 설정이 잘못되었습니다."),
    RESERVATION_ALREADY_CANCELED(1007, "이미 취소된 예약입니다.");

    private final int code;
    private final String message;
}

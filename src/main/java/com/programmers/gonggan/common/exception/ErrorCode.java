package com.programmers.gonggan.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    UNKNOWN(-9999, "알 수 없는 오류"),
    PLACE_NOT_FOUND(1000, "공간을 찾을 수 없습니다."),
    PLACE_ALREADY_EXIST(1001, "이미 존재하는 공간입니다.");

    private final int code;
    private final String message;
}

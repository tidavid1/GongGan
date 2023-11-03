package com.programmers.gonggan.domain.place.exception;

import com.programmers.gonggan.common.exception.ErrorCode;

public class PlaceAlreadyExistException extends RuntimeException {
    public PlaceAlreadyExistException() {
        super(ErrorCode.PLACE_ALREADY_EXIST.getMessage());
    }
}

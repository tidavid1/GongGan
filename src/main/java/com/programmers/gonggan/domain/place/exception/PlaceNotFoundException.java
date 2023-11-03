package com.programmers.gonggan.domain.place.exception;

import com.programmers.gonggan.common.exception.ErrorCode;

public class PlaceNotFoundException extends RuntimeException {
    public PlaceNotFoundException() {
        super(ErrorCode.PLACE_NOT_FOUND.getMessage());
    }
}

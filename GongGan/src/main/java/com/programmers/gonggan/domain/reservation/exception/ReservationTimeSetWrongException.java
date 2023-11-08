package com.programmers.gonggan.domain.reservation.exception;

import com.programmers.gonggan.common.exception.ErrorCode;

public class ReservationTimeSetWrongException extends RuntimeException {
    public ReservationTimeSetWrongException() {
        super(ErrorCode.RESERVATION_TIME_SET_WRONG.getMessage());
    }
}

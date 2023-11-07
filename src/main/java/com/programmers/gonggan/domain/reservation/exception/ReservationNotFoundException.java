package com.programmers.gonggan.domain.reservation.exception;

import com.programmers.gonggan.common.exception.ErrorCode;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException() {
        super(ErrorCode.RESERVATION_NOT_FOUND.getMessage());
    }
}

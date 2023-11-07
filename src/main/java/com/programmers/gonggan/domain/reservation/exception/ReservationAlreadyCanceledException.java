package com.programmers.gonggan.domain.reservation.exception;

import com.programmers.gonggan.common.exception.ErrorCode;

public class ReservationAlreadyCanceledException extends RuntimeException {
    public ReservationAlreadyCanceledException() {
        super(ErrorCode.RESERVATION_ALREADY_CANCELED.getMessage());
    }
}

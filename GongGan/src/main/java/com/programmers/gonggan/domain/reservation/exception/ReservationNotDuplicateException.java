package com.programmers.gonggan.domain.reservation.exception;

import com.programmers.gonggan.common.exception.ErrorCode;

public class ReservationNotDuplicateException extends RuntimeException {
    public ReservationNotDuplicateException() {
        super(ErrorCode.RESERVATION_NOT_DUPLICATE.getMessage());
    }
}

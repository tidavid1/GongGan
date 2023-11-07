package com.programmers.gonggan.domain.reservation.exception;

import com.programmers.gonggan.common.exception.ErrorCode;

public class EmailTypeNotMatchException extends IllegalArgumentException {
    public EmailTypeNotMatchException() {
        super(ErrorCode.EMAIL_TYPE_NOT_MATCH.getMessage());
    }
}

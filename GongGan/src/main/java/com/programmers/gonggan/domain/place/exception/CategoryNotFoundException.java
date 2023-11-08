package com.programmers.gonggan.domain.place.exception;

import com.programmers.gonggan.common.exception.ErrorCode;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException() {
        super(ErrorCode.CATEGORY_NOT_FOUND.getMessage());
    }
}

package com.programmers.gonggan.common.exception;

import com.programmers.gonggan.common.model.CommonResult;
import com.programmers.gonggan.domain.place.exception.CategoryNotFoundException;
import com.programmers.gonggan.domain.place.exception.PlaceAlreadyExistException;
import com.programmers.gonggan.domain.place.exception.PlaceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult<String> defaultException(Exception e) {
        return CommonResult.getFailResult(ErrorCode.UNKNOWN.getCode(), ErrorCode.UNKNOWN.getMessage());
    }

    @ExceptionHandler(PlaceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult<String> placeNotFoundException(PlaceNotFoundException e) {
        return CommonResult.getFailResult(ErrorCode.PLACE_NOT_FOUND.getCode(), ErrorCode.PLACE_NOT_FOUND.getMessage());
    }

    @ExceptionHandler(PlaceAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult<String> placeAlreadyExistException(PlaceAlreadyExistException e) {
        return CommonResult.getFailResult(ErrorCode.PLACE_ALREADY_EXIST.getCode(), ErrorCode.PLACE_ALREADY_EXIST.getMessage());
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult<String> categoryNotFoundException(CategoryNotFoundException e) {
        return CommonResult.getFailResult(ErrorCode.CATEGORY_NOT_FOUND.getCode(), ErrorCode.CATEGORY_NOT_FOUND.getMessage());
    }
}

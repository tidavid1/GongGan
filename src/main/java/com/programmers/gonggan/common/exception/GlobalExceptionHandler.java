package com.programmers.gonggan.common.exception;

import com.programmers.gonggan.common.model.CommonResult;
import com.programmers.gonggan.domain.place.exception.CategoryNotFoundException;
import com.programmers.gonggan.domain.place.exception.PlaceAlreadyExistException;
import com.programmers.gonggan.domain.place.exception.PlaceNotFoundException;
import com.programmers.gonggan.domain.reservation.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult<String> defaultException(Exception e) {
        return CommonResult.getFailResult(ErrorCode.UNKNOWN.getCode(), e.getMessage());
    }

    @ExceptionHandler(PlaceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult<String> placeNotFoundException(PlaceNotFoundException e) {
        return CommonResult.getFailResult(ErrorCode.PLACE_NOT_FOUND.getCode(), e.getMessage());
    }

    @ExceptionHandler(PlaceAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult<String> placeAlreadyExistException(PlaceAlreadyExistException e) {
        return CommonResult.getFailResult(ErrorCode.PLACE_ALREADY_EXIST.getCode(), e.getMessage());
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult<String> categoryNotFoundException(CategoryNotFoundException e) {
        return CommonResult.getFailResult(ErrorCode.CATEGORY_NOT_FOUND.getCode(), e.getMessage());
    }

    @ExceptionHandler(EmailTypeNotMatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult<String> emailTypeNotMatchException(EmailTypeNotMatchException e) {
        return CommonResult.getFailResult(ErrorCode.EMAIL_TYPE_NOT_MATCH.getCode(), e.getMessage());
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult<String> reservationNotFoundException(ReservationNotFoundException e) {
        return CommonResult.getFailResult(ErrorCode.RESERVATION_NOT_FOUND.getCode(), e.getMessage());
    }

    @ExceptionHandler(ReservationNotDuplicateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult<String> reservationNotDuplicateException(ReservationNotDuplicateException e) {
        return CommonResult.getFailResult(ErrorCode.RESERVATION_NOT_DUPLICATE.getCode(), e.getMessage());
    }

    @ExceptionHandler(ReservationTimeSetWrongException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult<String> reservationTimeSetWrongException(ReservationTimeSetWrongException e) {
        return CommonResult.getFailResult(ErrorCode.RESERVATION_TIME_SET_WRONG.getCode(), e.getMessage());
    }

    @ExceptionHandler(ReservationAlreadyCanceledException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult<String> reservationAlreadyCanceledException(ReservationAlreadyCanceledException e) {
        return CommonResult.getFailResult(ErrorCode.RESERVATION_ALREADY_CANCELED.getCode(), e.getMessage());
    }
}

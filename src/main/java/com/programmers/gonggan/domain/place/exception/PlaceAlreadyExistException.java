package com.programmers.gonggan.domain.place.exception;

public class PlaceAlreadyExistException extends RuntimeException {
    public PlaceAlreadyExistException(String message) {
        super(message);
    }
}

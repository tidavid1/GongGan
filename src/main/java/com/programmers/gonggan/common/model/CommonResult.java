package com.programmers.gonggan.common.model;

import lombok.Getter;

import java.util.List;

@Getter
public class CommonResult<T> {
    private final boolean isSuccess;
    private final int code;
    private final T data;

    private CommonResult(boolean isSuccess, int code, T data) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.data = data;
    }

    public static CommonResult<String> getSuccessResult() {
        return new CommonResult<>(true, 0, "성공");
    }

    public static CommonResult<String> getFailResult(int code, String message) {
        return new CommonResult<>(false, code, message);
    }

    public static <T> CommonResult<T> getSingleResult(T data) {
        return new CommonResult<>(true, 0, data);
    }

    public static <T> CommonResult<List<T>> getListResult(List<T> data) {
        return new CommonResult<>(true, 0, data);
    }
}

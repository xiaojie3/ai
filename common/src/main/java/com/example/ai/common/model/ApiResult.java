package com.example.ai.common.model;

import lombok.Data;

@Data
public class ApiResult<T> {

    private int code = 200;
    private T data;
    private String msg;

    public static <T> ApiResult<T> of(T data) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setData(data);
        return apiResult;
    }

    public static <T> ApiResult<T> of(T data, String msg) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }
}

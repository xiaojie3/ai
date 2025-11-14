package com.example.demo.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResult<T> {

    private int code = 200;
    private T data;
    private String msg;

    public ApiResult() {}

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

    public static <T> ApiResult<T> error(int code, String msg) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setCode(code);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public static <T> ApiResult<T> error(int code, String msg, T data) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setCode(code);
        apiResult.setMsg(msg);
        apiResult.setData(data);
        return apiResult;
    }
}

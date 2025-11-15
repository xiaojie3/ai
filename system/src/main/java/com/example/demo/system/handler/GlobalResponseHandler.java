package com.example.demo.system.handler;

import com.example.demo.common.model.ApiResult;
import lombok.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 全局统一响应包装器：自动将 Controller 返回值包装为 ResponseEntity<ApiResult>
 */
@ControllerAdvice// 指定拦截的 Controller 包路径
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    /**
     * 是否启用当前处理器（true=启用）
     */
    @Override
    public boolean supports(MethodParameter returnType, @NonNull Class converterType) {
        Class<?> returnClass = returnType.getParameterType();
        return !(returnClass.isAssignableFrom(ResponseEntity.class)
                || returnClass.isAssignableFrom(ApiResult.class));
    }

    /**
     * 响应处理：包装为 ResponseEntity<ApiResult>
     */
    @Override
    public Object beforeBodyWrite(Object body, @NonNull MethodParameter returnType,
                                  @NonNull MediaType selectedContentType, @NonNull Class selectedConverterType,
                                  @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {
        return ApiResult.success(body);
    }
}

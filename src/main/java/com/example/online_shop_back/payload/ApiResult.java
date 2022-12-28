package com.example.online_shop_back.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult<T> {

    private Boolean success = false;
    private String message;
    private String description;
    private T data;
    private List<ErrorData> errors;


    private ApiResult(Boolean success) {
        this.success = success;
    }

    public ApiResult(T data, Boolean success) {
        this.data = data;
        this.success = success;
    }

    public ApiResult(T data, Boolean success, String message) {
        this.data = data;
        this.success = success;
        this.message = message;
    }

    public ApiResult(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiResult(String message) {
        this.message = message;
        this.success = Boolean.TRUE;
    }

    public ApiResult(String errorMsg, Integer errorCode) {
        this.success = false;
        this.errors = Collections.singletonList(new ErrorData(errorMsg, errorCode));
    }

    public ApiResult(String errorMsg, Integer errorCode, String description) {
        this.success = false;
        this.errors = Collections.singletonList(new ErrorData(errorMsg, errorCode));
        this.description = description;
    }

    public ApiResult(List<ErrorData> errors) {
        this.success = false;
        this.errors = errors;
    }

    public static <E> ApiResult<E> successResponse(E data) {
        return new ApiResult<>(data, true);
    }

    public static <E> ApiResult<E> successResponse(E data, String message) {
        return new ApiResult<>(data, true, message);
    }

    public static <E> ApiResult<E> successResponse() {
        return new ApiResult<>(true);
    }

    public static ApiResult<String> successResponse(String message) {
        return new ApiResult<>(message);
    }

    public static ApiResult<ErrorData> errorResponse(String errorMsg, Integer errorCode) {
        return new ApiResult<>(errorMsg, errorCode);
    }

    public static ApiResult<ErrorData> errorResponse(String errorMsg, Integer errorCode, String description) {
        return new ApiResult<>(errorMsg, errorCode, description);
    }

    public static ApiResult<ErrorData> errorResponse(List<ErrorData> errors) {
        return new ApiResult<>(errors);
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exception) throws X {
        if (data != null && success) {
            return data;
        } else {
            throw exception.get();
        }
    }

}



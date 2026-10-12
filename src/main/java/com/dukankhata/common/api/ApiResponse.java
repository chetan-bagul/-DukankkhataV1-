package com.dukankhata.common.api;

public record ApiResponse<T>(
        boolean success,
        T data,
        String message
) {
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, data, null);
    }

    public static <T> ApiResponse<T> success(T data) { return new ApiResponse<>(true, data, null); }
    public static <T> ApiResponse<T> success(String message) { return new ApiResponse<>(true, null, message); }
    public static <T> ApiResponse<T> error(int status, String code, String message, Object errors) { return new ApiResponse<>(false, null, message); }
}

package com.griotold.backend_onboarding.presentation;

public record ApiResponse<T>(
        String message,
        T date
) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("API 요청에 성공했습니다", data);
    }

    public static ApiResponse<Void> success() {
        return new ApiResponse<>("API 요청에 성공했습니다", null);
    }
}

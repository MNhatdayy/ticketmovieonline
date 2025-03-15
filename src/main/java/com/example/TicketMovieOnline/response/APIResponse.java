package com.example.TicketMovieOnline.response;

public class APIResponse<T> {
    private boolean success;
    private String message;
    private T data;
    public APIResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
    public static <T> APIResponse<T> success(T data, String message) {
        return new APIResponse<>(true, message, data);
    }
    public static <T> APIResponse<T> failure(T data, String message) {
        return new APIResponse<>(false,message,null);
    }
    public boolean isSuccess() {
        return success;
    }
    public String getMessage() {
        return message;
    }
    public T getData() {
        return data;
    }
}

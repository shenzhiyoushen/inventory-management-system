package com.example.springbootuserbackend.common;

import lombok.Data;

/**
 * 统一返回结果封装
 */
@Data
public class Result<T> {
    // 响应码：200成功，500失败，401未登录
    private Integer code;
    // 响应信息
    private String message;
    // 响应数据
    private T data;

    public Result() {
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 成功（无数据）
    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null);
    }

    // 成功（有数据）
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    // 失败
    public static <T> Result<T> error(String message) {
        return new Result<>(500, message, null);
    }

    // 自定义返回
    public static <T> Result<T> result(Integer code, String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
}
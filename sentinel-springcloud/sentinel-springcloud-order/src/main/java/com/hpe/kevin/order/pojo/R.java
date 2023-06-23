package com.hpe.kevin.order.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class R<T> {
    private Integer code;
    private String message;
    private T data;

    public R(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static R error(Integer code, String message) {
        return new R(code, message);
    }
}

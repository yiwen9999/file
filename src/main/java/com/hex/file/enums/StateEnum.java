package com.hex.file.enums;

import lombok.Getter;

@Getter
public enum StateEnum implements CodeEnum {
    START(2, "启用"),
    STOP(-1, "停用"),
    INVALID(-2, "作废");

    private Integer code;

    private String msg;

    StateEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

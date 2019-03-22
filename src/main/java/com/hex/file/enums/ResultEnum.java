package com.hex.file.enums;

public enum ResultEnum {
    UN_KNOW_ERRO(-1, "未知错误"),
    SUCCESS(0, "成功"),
    SAVE_ERROR(101, "保存文件发生错误"),
    PARAM_ERROR(102, "传递参数错误"),;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

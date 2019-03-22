package com.hex.file.exception;

import com.hex.file.enums.ResultEnum;

/**
 * User: hexuan
 * Date: 2017/8/14
 * Time: 下午3:02
 */
public class HexException extends RuntimeException {
    private Integer code;

    public HexException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public HexException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}

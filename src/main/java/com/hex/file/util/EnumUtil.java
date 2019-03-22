package com.hex.file.util;

import com.hex.file.enums.CodeEnum;

/**
 * User: hexuan
 * Date: 2018/4/26
 * Time: 上午11:39
 */
public class EnumUtil {
    public static <T extends CodeEnum> T getCode(Integer code, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}

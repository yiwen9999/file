package com.hex.file.handle;

import com.hex.file.domain.Result;
import com.hex.file.enums.ResultEnum;
import com.hex.file.exception.HexException;
import com.hex.file.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User: hexuan
 * Date: 2017/8/14
 * Time: 下午3:04
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandle {
//    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        log.error("【系统异常】{}", e);
        if (e instanceof HexException) {
            HexException exception = (HexException) e;
            return ResultUtil.error(exception.getCode(), exception.getMessage());
        } else {
            //logger.error("【系统异常】{}", e);
            return ResultUtil.error(ResultEnum.UN_KNOW_ERRO.getCode(), ResultEnum.UN_KNOW_ERRO.getMsg());
        }

    }
}

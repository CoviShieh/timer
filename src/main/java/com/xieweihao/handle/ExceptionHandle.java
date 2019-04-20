package com.xieweihao.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xieweihao.exception.UserException;
import com.xieweihao.utils.Result;

@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    /**
     * 全局异常捕捉处理 被 @ExceptionHandler、@InitBinder、@ModelAttribute 注解的方法，都会作用在 被 @RequestMapping 注解的方法上。
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
    	 if (e instanceof UserException) {
             UserException userException = (UserException) e;
             return Result.error(userException.getResultEnum());
         }else {
             logger.error("【系统异常】{}", e);
             return Result.error();
         }
    }
}


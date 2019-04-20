package com.xieweihao.exception;

import com.xieweihao.enums.ResultEnum;

public class UserException extends RuntimeException {

    private ResultEnum resultEnum;
    
    public UserException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.resultEnum = resultEnum;
    }

    public ResultEnum getResultEnum() {
        return resultEnum;
    }

    public void setResultEnum(ResultEnum resultEnum) {
        this.resultEnum = resultEnum;
    }
}


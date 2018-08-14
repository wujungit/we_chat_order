package com.webank.exception;

import com.webank.enums.ResultEnum;
import lombok.Data;

@Data
public class WeChatOrderException extends RuntimeException {
    private Integer code;

    public WeChatOrderException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }
}

package com.webank.vo;

import lombok.Data;

/**
 * HTTP请求返回对象
 *
 * @param <T>
 */
@Data
public class ResultVo<T> {
    private Integer code;//错误码
    private String msg;//错误信息
    private T data;//数据
}

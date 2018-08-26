package com.webank.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * HTTP请求返回对象
 *
 * @param <T>
 */
@Data
public class ResultVo<T> implements Serializable {
    private static final long serialVersionUID = 3068837394742385883L;
    private Integer code;//错误码
    private String msg;//错误信息
    private T data;//数据
}

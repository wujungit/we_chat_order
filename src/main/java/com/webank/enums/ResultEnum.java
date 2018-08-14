package com.webank.enums;

/**
 * 返回结果信息
 */
public enum ResultEnum {
    UNKNOWN_ERROR(-1, "未知错误"),
    SUCCESS(0, "成功"),
    ERROR(1, "失败"),
    PRODUCT_NOT_EXIST(101, "该商品不存在"),
    PRODUCT_UNDER_STOCK(102, "该商品库存不足");

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

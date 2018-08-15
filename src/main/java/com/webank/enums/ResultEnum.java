package com.webank.enums;

/**
 * 返回结果信息
 */
public enum ResultEnum {
    UNKNOWN_ERROR(-1, "未知错误"),
    SUCCESS(0, "成功"),
    ERROR(1, "失败"),
    PRODUCT_NOT_EXIST(101, "商品不存在"),
    PRODUCT_UNDER_STOCK(102, "商品库存不足"),
    ORDER_NOT_EXIST(103, "订单不存在"),
    ORDER_DETAIL_NOT_EXIST(104, "订单详情不存在"),
    ORDER_STATUS_ERROR(105, "订单状态不正确"),
    ORDER_STATUS_UPDATE_FAIL(106, "订单状态更新失败"),
    ORDER_DETAIL_EMPTY(107, "订单详情为空"),
    ORDER_PAY_STATUS_ERROR(108, "订单支付状态不正确"),
    ORDER_PAY_STATUS_UPDATE_FAIL(109, "订单支付状态更新失败")
    ;

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

package com.webank.enums;

import lombok.Getter;

/**
 * 返回结果信息
 */
@Getter
public enum ResultEnum {
    UNKNOWN_ERROR(-1, "未知错误"),
    SUCCESS(0, "成功"),
    ERROR(1, "失败"),
    PARAM_ERROR(2, "参数不正确"),
    PRODUCT_NOT_EXIST(101, "商品不存在"),
    PRODUCT_UNDER_STOCK(102, "商品库存不足"),
    ORDER_NOT_EXIST(103, "订单不存在"),
    ORDER_DETAIL_NOT_EXIST(104, "订单详情不存在"),
    ORDER_STATUS_ERROR(105, "订单状态不正确"),
    ORDER_STATUS_UPDATE_FAIL(106, "订单状态更新失败"),
    ORDER_DETAIL_EMPTY(107, "订单详情为空"),
    ORDER_PAY_STATUS_ERROR(108, "订单支付状态不正确"),
    ORDER_PAY_STATUS_UPDATE_FAIL(109, "订单支付状态更新失败"),
    ORDER_OWNER_ERROR(110, "订单不属于当前用户"),
    WE_CHAT_MP_ERROR(111, "微信公众号异常"),
    WE_CHAT_NOTIFY_VERIFY_ERROR(112, "微信支付异步通知金额异常"),
    ORDER_CANCEL_SUCCESS(113, "订单取消成功"),
    ORDER_FINISH_SUCCESS(114, "订单完结成功"),
    PRODUCT_STATUS_ERROR(115, "商品状态不正确"),
    PRODUCT_ON_SALE_SUCCESS(117, "商品上架成功"),
    PRODUCT_OFF_SALE_SUCCESS(118, "商品下架成功"),
    ;

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

package com.webank.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class OrderDetailForm {
    @NotEmpty(message = "微信openid必填")
    private String openid;//买家微信openid
    @NotEmpty(message = "订单ID必填")
    private String orderId;//订单ID
}

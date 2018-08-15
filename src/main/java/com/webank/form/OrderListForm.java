package com.webank.form;

import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class OrderListForm {
    @NotEmpty(message = "微信openid必填")
    private String openid;//买家微信openid
    private Integer page;//当前页数
    private Integer size;//行数
}

package com.webank.enums;

import lombok.Getter;

/**
 * 商品状态
 */
@Getter
public enum ProductStatusEnum implements CodeEnum {
    UP(0, "正常"),
    DOWN(1, "下架");

    private Integer code;
    private String msg;

    ProductStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

package com.webank.dto;

import lombok.Data;

/**
 * 购物车
 */
@Data
public class CartDto {
    private String productId;//商品ID
    private Integer productQuantity;//数量

    public CartDto(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}

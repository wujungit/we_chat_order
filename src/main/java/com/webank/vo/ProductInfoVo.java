package com.webank.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品Vo
 */
@Data
public class ProductInfoVo implements Serializable {
    private static final long serialVersionUID = 3068837394742385881L;
    @JsonProperty("id")
    private String productId;//商品ID
    @JsonProperty("name")
    private String productName;//商品名称
    @JsonProperty("price")
    private BigDecimal productPrice;//单价
    @JsonProperty("desc")
    private String productDesc;//描述
    @JsonProperty("icon")
    private String productIcon;//小图
}

package com.webank.form;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductForm {
    private String productId;//商品ID
    private String productName;//商品名称
    private BigDecimal productPrice;//单价
    private Integer productStock;//库存
    private String productDesc;//描述
    private String productIcon;//小图
    private Integer categoryType;//类目编号
}

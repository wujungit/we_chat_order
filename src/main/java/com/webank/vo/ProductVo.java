package com.webank.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 类目Vo
 */
@Data
public class ProductVo implements Serializable {
    private static final long serialVersionUID = 3068837394742385882L;
    @JsonProperty("name")
    private String categoryName;//类目名字
    @JsonProperty("type")
    private Integer categoryType;//类目编号
    @JsonProperty("foods")
    private List<ProductInfoVo> productInfoVoList;//商品信息列表
}

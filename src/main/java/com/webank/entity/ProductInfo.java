package com.webank.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品
 */
@Entity
@DynamicUpdate
@Data
public class ProductInfo {
    @Id
    private String productId;//商品ID
    private String productName;//商品名称
    private BigDecimal productPrice;//单价
    private Integer productStock;//库存
    private String productDesc;//描述
    private String productIcon;//小图
    private Integer productStatus;//商品状态:0-正常,1-下架
    private Integer categoryType;//类目编号
    private Date createTime;//创建时间
    private Date updateTime;//修改时间
}

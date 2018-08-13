package com.webank.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单详情
 */
@Entity
@DynamicUpdate
@Data
public class OrderDetail {
    @Id
    private String detailId;//详情ID
    private String orderId;//订单ID
    private String productId;//商品ID
    private String productName;//商品名称
    private BigDecimal productPrice;//当前价格,单位分
    private Integer productQuantity;//数量
    private String productIcon;//小图
    private Date createTime;//创建时间
    private Date updateTime;//修改时间
}

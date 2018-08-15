package com.webank.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.webank.entity.OrderDetail;
import com.webank.enums.OrderStatusEnum;
import com.webank.enums.PayStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单Dto
 */
@Data
public class OrderDto {
    private String orderId;//订单ID
    private String buyerName;//买家名字
    private String buyerPhone;//买家电话
    private String buyerAddress;//买家地址
    private String buyerOpenid;//买家微信openid
    private BigDecimal orderAmount;//订单总金额
    private Integer orderStatus;//订单状态,默认新下单
    private Integer payStatus;//支付状态,默认未支付
    private Date createTime;//创建时间
    private Date updateTime;//修改时间
    private List<OrderDetail> orderDetailList;//订单详情列表
}

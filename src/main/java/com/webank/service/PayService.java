package com.webank.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.webank.dto.OrderDto;

/**
 * 支付service
 */
public interface PayService {
    /**
     * 创建订单
     *
     * @param orderDto
     * @return
     */
    PayResponse create(OrderDto orderDto);

    /**
     * 异步通知
     *
     * @param notifyData
     * @return
     */
    PayResponse notify(String notifyData);

    /**
     * 退款
     *
     * @param orderDto
     * @return
     */
    RefundResponse refund(OrderDto orderDto);
}

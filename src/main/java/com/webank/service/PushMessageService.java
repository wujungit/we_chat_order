package com.webank.service;

import com.webank.dto.OrderDto;

/**
 * 消息推送
 */
public interface PushMessageService {
    /**
     * 订单状态
     *
     * @param orderDto
     */
    void orderStatus(OrderDto orderDto);
}

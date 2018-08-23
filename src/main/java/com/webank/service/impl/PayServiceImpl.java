package com.webank.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.BestPayService;
import com.webank.dto.OrderDto;
import com.webank.enums.ResultEnum;
import com.webank.exception.WeChatOrderException;
import com.webank.service.OrderService;
import com.webank.service.PayService;
import com.webank.utils.JsonUtil;
import com.webank.utils.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class PayServiceImpl implements PayService {
    private static final String ORDER_NAME = "微信点餐订单";

    @Autowired
    private BestPayService bestPayService;
    @Autowired
    private OrderService orderService;

    @Override
    public PayResponse create(OrderDto orderDto) {
        PayRequest payRequest = getPayRequest(orderDto);
        log.info("【微信支付】发起支付：payRequest={}", JsonUtil.toJson(payRequest));
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】发起支付：payResponse={}", JsonUtil.toJson(payResponse));
        return payResponse;
    }

    private PayRequest getPayRequest(OrderDto orderDto) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDto.getBuyerOpenid());
        payRequest.setOrderAmount(orderDto.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDto.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        return payRequest;
    }

    @Override
    public PayResponse notify(String notifyData) {
        // 验证签名，判断是否是微信请求过来的参数
        // 判断支付的状态
        // 判断下单人与支付人是否相同
        log.info("【微信支付】异步通知：notifyData={}", JsonUtil.toJson(notifyData));
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("【微信支付】异步通知：payResponse={}", payResponse);
        String orderId = payResponse.getOrderId();
        // 查询订单
        OrderDto orderDto = orderService.getOne(orderId);
        // 判断订单是否存在
        if (null == orderDto) {
            log.error("【微信支付】异步通知，订单不存在，orderId={}", orderId);
            throw new WeChatOrderException(ResultEnum.ORDER_NOT_EXIST);
        }
        // 校验支付的金额与订单的金额是否一致
        Double payAmount = payResponse.getOrderAmount();
        BigDecimal orderAmount = orderDto.getOrderAmount();
        if (!MathUtil.equals(payAmount, orderAmount.doubleValue())) {
            log.error("【微信支付】异步通知，支付金额与订单金额不一致，orderId={}，支付金额={}，订单金额={}",
                    orderId, payAmount, orderAmount);
            throw new WeChatOrderException(ResultEnum.WE_CHAT_NOTIFY_VERIFY_ERROR);
        }
        // 修改订单的支付状态
        orderService.paid(orderDto);
        return payResponse;
    }

    @Override
    public RefundResponse refund(OrderDto orderDto) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDto.getOrderId());
        refundRequest.setOrderAmount(orderDto.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信退款】refundRequest={}", JsonUtil.toJson(refundRequest));
        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退款】refundResponse={}", JsonUtil.toJson(refundResponse));
        return refundResponse;
    }
}

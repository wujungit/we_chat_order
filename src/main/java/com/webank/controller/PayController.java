package com.webank.controller;

import com.lly835.bestpay.model.PayResponse;
import com.webank.dto.OrderDto;
import com.webank.enums.ResultEnum;
import com.webank.exception.WeChatOrderException;
import com.webank.service.OrderService;
import com.webank.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付
 */
@Controller
@Slf4j
@RequestMapping("/pay")
public class PayController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private PayService payService;

    /**
     * 创建订单
     *
     * @param orderId
     * @param returnUrl
     * @return
     */
    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId, @RequestParam("returnUrl") String returnUrl) {
        // 查询订单
        OrderDto orderDto = orderService.getOne(orderId);
        if (null == orderDto) throw new WeChatOrderException(ResultEnum.ORDER_NOT_EXIST);
        // 发起支付
        PayResponse payResponse = payService.create(orderDto);
        Map<String, Object> map = new HashMap<>();
        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);
        return new ModelAndView("pay/create", map);
    }

    /**
     * 异步通知
     *
     * @param notifyData
     * @return
     */
    @PostMapping("notify")
    public ModelAndView notify(@RequestBody String notifyData) {
//        PayResponse payResponse = payService.notify(notifyData);
        // 返回给微信处理结果
        return new ModelAndView("pay/success");
    }
}

package com.webank.controller;

import com.webank.dto.OrderDto;
import com.webank.enums.ResultEnum;
import com.webank.exception.WeChatOrderException;
import com.webank.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * 卖家端订单
 */
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 订单列表
     *
     * @param page 第几页，从第一页开始
     * @param size 一页有多少条数据
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<OrderDto> orderDtoPage = orderService.findList(pageRequest);
        Map<String, Object> map = new HashMap<>();
        map.put("orderDtoPage", orderDtoPage);
        map.put("currentPage", page);
        map.put("size", size);
        // 使用freemarker模板返回
        return new ModelAndView("order/list", map);
    }

    /**
     * 取消订单
     *
     * @param orderId
     * @return
     */
    @GetMapping("cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId) {
        Map<String, Object> map = new HashMap<>();
        OrderDto orderDto;
        try {
            orderDto = orderService.getOne(orderId);
            orderService.cancel(orderDto);
        } catch (WeChatOrderException e) {
            log.error("【卖家端取消订单】发生异常orderId={}", orderId);
            map.put("msg", e.getMessage());
            map.put("url", "/we_chat_order/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMsg());
        map.put("url", "/we_chat_order/seller/order/list");
        return new ModelAndView("common/success", map);
    }
}

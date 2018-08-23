package com.webank.controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.webank.dto.OrderDto;
import com.webank.entity.OrderDetail;
import com.webank.enums.ResultEnum;
import com.webank.exception.WeChatOrderException;
import com.webank.form.OrderCancelForm;
import com.webank.form.OrderDetailForm;
import com.webank.form.OrderForm;
import com.webank.form.OrderListForm;
import com.webank.service.OrderService;
import com.webank.utils.ResultVoUtil;
import com.webank.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 买家端订单
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;

    // 创建订单
    @RequestMapping("/create")
    public ResultVo<Map> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        // 表单校验
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确,orderForm:{}", orderForm.toString());
            throw new WeChatOrderException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        // Dto对象转换
        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerName(orderForm.getName());
        orderDto.setBuyerPhone(orderForm.getPhone());
        orderDto.setBuyerAddress(orderForm.getAddress());
        orderDto.setBuyerOpenid(orderForm.getOpenid());
        // 购物车Json字符串转List
        String items = orderForm.getItems();
        Gson gson = new Gson();
        List<OrderDetail> orderDetailList;
        try {
            orderDetailList = gson.fromJson(items, new TypeToken<List<OrderDetail>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            log.error("购物车Json字符串转List错误,items:{}", items);
            throw new WeChatOrderException(ResultEnum.PARAM_ERROR);
        }
        if (orderDetailList.isEmpty()) {
            log.error("购物车不能为空");
            throw new WeChatOrderException(ResultEnum.PARAM_ERROR);
        }
        orderDto.setOrderDetailList(orderDetailList);
        // 创建订单
        OrderDto createResult = orderService.create(orderDto);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());
        return ResultVoUtil.success(map);

    }

    // 订单列表
    @RequestMapping("/list")
    public ResultVo<List<OrderDto>> list(@Valid OrderListForm orderListForm, BindingResult bindingResult) {
        // 表单校验
        if (bindingResult.hasErrors()) {
            log.error("【订单列表】参数不正确,orderListForm:{}", orderListForm.toString());
            throw new WeChatOrderException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        String openid = orderListForm.getOpenid();
        Integer page = orderListForm.getPage();
        Integer size = orderListForm.getSize();
        PageRequest pageRequest = PageRequest.of(page == null ? 0 : page, size == null ? 10 : size);
        Page<OrderDto> orderDtoPage = orderService.findList(openid, pageRequest);
        List<OrderDto> orderDtoList = orderDtoPage.getContent();
        return ResultVoUtil.success(orderDtoList);
    }

    // 订单详情
    @RequestMapping("/detail")
    public ResultVo<OrderDto> detail(@Valid OrderDetailForm orderDetailForm, BindingResult bindingResult) {
        // 表单校验
        if (bindingResult.hasErrors()) {
            log.error("【订单详情】参数不正确,orderDetailForm:{}", orderDetailForm.toString());
            throw new WeChatOrderException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        String orderId = orderDetailForm.getOrderId();
        OrderDto orderDto = orderService.getOne(orderId);
        if (null == orderDto) {
            log.error("【订单详情】订单不存在");
            throw new WeChatOrderException(ResultEnum.ORDER_NOT_EXIST);
        }
        // 判断是否是本人的订单
        String formOpenid = orderDetailForm.getOpenid();
        if (!orderDto.getBuyerOpenid().equals(formOpenid)) {
            log.error("【订单详情】订单的openid不一致，不属于当前用户");
            throw new WeChatOrderException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return ResultVoUtil.success(orderDto);
    }

    // 取消订单
    @RequestMapping("/cancel")
    public ResultVo cancel(@Valid OrderCancelForm orderCancelForm, BindingResult bindingResult) {
        // 表单校验
        if (bindingResult.hasErrors()) {
            log.error("【取消订单】参数不正确,orderCancelForm:{}", orderCancelForm.toString());
            throw new WeChatOrderException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        String orderId = orderCancelForm.getOrderId();
        OrderDto orderDto = orderService.getOne(orderId);
        if (null == orderDto) {
            log.error("【取消订单】订单不存在");
            throw new WeChatOrderException(ResultEnum.ORDER_NOT_EXIST);
        }
        // 判断是否是本人的订单
        String formOpenid = orderCancelForm.getOpenid();
        if (!orderDto.getBuyerOpenid().equals(formOpenid)) {
            log.error("【取消订单】订单的openid不一致，不属于当前用户");
            throw new WeChatOrderException(ResultEnum.ORDER_OWNER_ERROR);
        }
        orderService.cancel(orderDto);
        return ResultVoUtil.success();
    }
}

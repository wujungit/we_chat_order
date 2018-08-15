package com.webank.service.impl;

import com.webank.dto.CartDto;
import com.webank.dto.OrderDto;
import com.webank.entity.OrderDetail;
import com.webank.entity.OrderMaster;
import com.webank.entity.ProductInfo;
import com.webank.enums.OrderStatusEnum;
import com.webank.enums.PayStatusEnum;
import com.webank.enums.ResultEnum;
import com.webank.exception.WeChatOrderException;
import com.webank.repository.OrderDetailRepository;
import com.webank.repository.OrderMasterRepository;
import com.webank.service.OrderService;
import com.webank.service.ProductInfoService;
import com.webank.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    @Transactional
    public OrderDto create(OrderDto orderDto) {
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);//订单总金额
        // 查询商品（数量，价格）
        List<OrderDetail> orderDetailList = orderDto.getOrderDetailList();
        for (OrderDetail orderDetail : orderDetailList) {
            String productId = orderDetail.getProductId();
            ProductInfo productInfo = productInfoService.getOne(productId);
            if (null == productInfo) {
                throw new WeChatOrderException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            // 计算订单总金额
            BigDecimal productPrice = productInfo.getProductPrice();
            Integer productQuantity = orderDetail.getProductQuantity();
            orderAmount = productPrice.multiply(new BigDecimal(productQuantity)).add(orderAmount);
            // 订单详情入库（OrderDetail）
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            orderDetailRepository.save(orderDetail);
        }
        // 订单入库（OrderMaster）
        OrderMaster orderMaster = new OrderMaster();
        orderDto.setOrderId(orderId);
        BeanUtils.copyProperties(orderDto, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.NOT_PAY.getCode());
        orderMasterRepository.save(orderMaster);
        // 减库存
        List<CartDto> cartDtoList = orderDto.getOrderDetailList().stream()
                .map(e -> new CartDto(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.decreaseStock(cartDtoList);
        // 发送WebSocket消息 TODO
        return orderDto;
    }

    @Override
    public OrderDto getOne(String orderId) {
        OrderDto orderDto = new OrderDto();
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).orElse(null);
        if (null == orderMaster) {
            throw new WeChatOrderException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (orderDetailList.isEmpty()) {
            throw new WeChatOrderException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        BeanUtils.copyProperties(orderMaster, orderDto);
        orderDto.setOrderDetailList(orderDetailList);
        return orderDto;
    }

    @Override
    public Page<OrderDto> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderMaster> orderMasterList = orderMasterPage.getContent();
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (OrderMaster orderMaster : orderMasterList) {
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(orderMaster, orderDto);
            orderDtoList.add(orderDto);
        }
        return new PageImpl<>(orderDtoList, pageable, orderMasterPage.getTotalElements());
    }

    @Override
    @Transactional
    public OrderDto cancel(OrderDto orderDto) {
        // 判断订单状态
        Integer orderStatus = orderDto.getOrderStatus();
        if (!OrderStatusEnum.NEW.getCode().equals(orderStatus)) {
            log.error("订单状态不正确,无法取消订单,orderId={},orderStatus={}", orderDto.getOrderId(), orderDto.getOrderStatus());
            throw new WeChatOrderException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 修改订单状态
        OrderMaster orderMaster = new OrderMaster();
        orderDto.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDto, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        Integer updateOrderStatus = updateResult.getOrderStatus();
        if (!updateOrderStatus.equals(OrderStatusEnum.CANCEL.getCode())) {
            log.error("取消订单时,订单状态更新失败,orderMaster:{}", orderMaster.toString());
            throw new WeChatOrderException(ResultEnum.ORDER_STATUS_UPDATE_FAIL);
        }
        // 返回库存
        List<OrderDetail> orderDetailList = orderDto.getOrderDetailList();
        if (orderDetailList.isEmpty()) {
            log.error("【取消订单】订单中无商品详情,orderDto:{}", orderDto.toString());
            throw new WeChatOrderException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDto> cartDtoList = orderDto.getOrderDetailList().stream()
                .map(e -> new CartDto(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.increaseStock(cartDtoList);
        // 如果已支付，需要退款
        Integer payStatus = orderDto.getPayStatus();
        if (PayStatusEnum.PAID.getCode().equals(payStatus)) {
            // TODO
        }
        return orderDto;
    }

    @Override
    public OrderDto finish(OrderDto orderDto) {
        // 判断订单状态
        Integer orderStatus = orderDto.getOrderStatus();
        if (!OrderStatusEnum.NEW.getCode().equals(orderStatus)) {
            log.error("【完结订单】订单状态不正确,orderId={},orderStatus={}", orderDto.getOrderId(), orderDto.getOrderStatus());
            throw new WeChatOrderException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 修改订单状态
        orderDto.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto, orderMaster);
        OrderMaster updateOrderMaster = orderMasterRepository.save(orderMaster);
        Integer updateOrderStatus = updateOrderMaster.getOrderStatus();
        if (!updateOrderStatus.equals(OrderStatusEnum.FINISH.getCode())) {
            log.error("【完结订单】订单状态更新失败,orderMaster:{}", orderMaster.toString());
            throw new WeChatOrderException(ResultEnum.ORDER_STATUS_UPDATE_FAIL);
        }
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto paid(OrderDto orderDto) {
        // 判断订单状态
        Integer orderStatus = orderDto.getOrderStatus();
        if (!OrderStatusEnum.NEW.getCode().equals(orderStatus)) {
            log.error("【支付订单】订单状态不正确,orderId={},orderStatus={}", orderDto.getOrderId(), orderDto.getOrderStatus());
            throw new WeChatOrderException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 判断支付状态
        Integer payStatus = orderDto.getPayStatus();
        if (PayStatusEnum.PAID.getCode().equals(payStatus)) {
            log.error("【支付订单】订单支付状态不正确,orderId={},payStatus={}", orderDto.getOrderId(), orderDto.getPayStatus());
            throw new WeChatOrderException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        // 修改订单状态
        orderDto.setPayStatus(PayStatusEnum.PAID.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto, orderMaster);
        OrderMaster updateOrderMaster = orderMasterRepository.save(orderMaster);
        Integer updatePayStatus = updateOrderMaster.getPayStatus();
        if (!updatePayStatus.equals(PayStatusEnum.PAID.getCode())) {
            log.error("【支付订单】订单支付状态更新失败,orderMaster:{}", orderMaster.toString());
            throw new WeChatOrderException(ResultEnum.ORDER_PAY_STATUS_UPDATE_FAIL);
        }
        // 支付 TODO
        return orderDto;
    }

    @Override
    public Page<OrderDto> findList(Pageable pageable) {
        return null;
    }
}

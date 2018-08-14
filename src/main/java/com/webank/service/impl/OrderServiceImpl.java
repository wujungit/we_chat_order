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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
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
        BeanUtils.copyProperties(orderDto, orderMaster);
        orderMaster.setOrderId(orderId);
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
        return null;
    }

    @Override
    public Page<OrderDto> findList(String buyerOpenid, Pageable pageable) {
        return null;
    }

    @Override
    public OrderDto cancel(OrderDto orderDto) {
        return null;
    }

    @Override
    public OrderDto finish(OrderDto orderDto) {
        return null;
    }

    @Override
    public OrderDto paid(OrderDto orderDto) {
        return null;
    }

    @Override
    public Page<OrderDto> findList(Pageable pageable) {
        return null;
    }
}

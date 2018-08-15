package com.webank.service;

import com.webank.dto.OrderDto;
import com.webank.entity.OrderDetail;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Test
    public void create() {
        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerName("廖师兄");
        orderDto.setBuyerAddress("幕课网");
        orderDto.setBuyerPhone("12345678901");
        orderDto.setBuyerOpenid("1101110");
        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("1");
        o1.setProductQuantity(1);

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("2");
        o2.setProductQuantity(2);

        orderDetailList.add(o1);
        orderDetailList.add(o2);
        orderDto.setOrderDetailList(orderDetailList);
        OrderDto result = orderService.create(orderDto);
        log.info("result:{}", result.toString());
        Assert.assertNotNull(result);
    }

    @Test
    public void getOne() {
        String orderId = "1534244686974439812";
        OrderDto result = orderService.getOne(orderId);
        log.info("result:{}", result.toString());
        Assert.assertNotNull(result);
    }

    @Test
    public void findList() {
        String buyerOpenid = "1101110";
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<OrderDto> result = orderService.findList(buyerOpenid, pageRequest);
        log.info("result:{}", result.getContent());
        log.info("total:{}", result.getTotalElements());
        Assert.assertNotNull(result);
    }

    @Test
    public void cancel() {
        String orderId = "1534244686974439812";
        OrderDto orderDto = orderService.getOne(orderId);
        OrderDto result = orderService.cancel(orderDto);
        log.info("result:{}", result.toString());
        Assert.assertNotNull(result);
    }

    @Test
    public void finish() {
        String orderId = "1534244686974439812";
        OrderDto orderDto = orderService.getOne(orderId);
        OrderDto result = orderService.finish(orderDto);
        log.info("result:{}", result.toString());
        Assert.assertNotNull(result);
    }

    @Test
    public void paid() {
        String orderId = "1534244686974439812";
        OrderDto orderDto = orderService.getOne(orderId);
        OrderDto result = orderService.paid(orderDto);
        log.info("result:{}", result.toString());
        Assert.assertNotNull(result);
    }

    @Test
    public void findList1() {
    }
}
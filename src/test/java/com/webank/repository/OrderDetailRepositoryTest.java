package com.webank.repository;

import com.webank.entity.OrderDetail;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单详情Dao测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderDetailRepositoryTest {
    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void findByOrderId() {
        String orderId = "100000";
        List<OrderDetail> result = repository.findByOrderId(orderId);
        log.info("result:{}", result.toString());
        Assert.assertNotEquals(0, result.size());
    }

    @Test
    public void save() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("10001");
        orderDetail.setOrderId("100000");
        orderDetail.setProductId("1");
        orderDetail.setProductName("热干面");
        orderDetail.setProductPrice(new BigDecimal(5));
        orderDetail.setProductQuantity(2);
        orderDetail.setProductIcon("http://xxxx.jpg");
        OrderDetail result = repository.save(orderDetail);
        log.info("result:{}", result.toString());
        Assert.assertNotNull(result);
    }
}
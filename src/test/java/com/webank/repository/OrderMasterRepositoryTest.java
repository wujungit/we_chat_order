package com.webank.repository;

import com.webank.entity.OrderMaster;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * 订单Dao测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderMasterRepositoryTest {
    @Autowired
    private OrderMasterRepository repository;

    @Test
    public void findByBuyerOpenid() {
        String buyerOpenid = "123456";
        PageRequest pageRequest = PageRequest.of(1, 1);
        Page<OrderMaster> result = repository.findByBuyerOpenid(buyerOpenid, pageRequest);
        log.info("result:{}", result.getContent().toString());
        log.info("total:{}", result.getTotalElements());
        Assert.assertNotEquals(0, result.getTotalElements());
    }

    @Test
    public void save() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("100001");
        orderMaster.setBuyerName("吴俊");
        orderMaster.setBuyerPhone("13043450000");
        orderMaster.setBuyerAddress("北京天安门");
        orderMaster.setBuyerOpenid("123456");
        orderMaster.setOrderAmount(new BigDecimal(9999));
        OrderMaster result = repository.save(orderMaster);
        log.info("result:{}", result.toString());
        Assert.assertNotNull(result);
    }
}
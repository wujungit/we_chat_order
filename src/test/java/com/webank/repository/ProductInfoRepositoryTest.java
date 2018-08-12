package com.webank.repository;

import com.webank.entity.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 商品Dao测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductInfoRepositoryTest {
    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void findByProductStatus() {
        Integer productStatus = 0;
        List<ProductInfo> result = repository.findByProductStatus(productStatus);
        log.info("result:{}", result.toString());
        Assert.assertNotEquals(0, result.size());
    }
}
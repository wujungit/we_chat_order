package com.webank.service;

import com.webank.entity.ProductInfo;
import com.webank.enums.ProductStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品Service测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductInfoServiceTest {
    @Autowired
    private ProductInfoService productInfoService;

    @Test
    public void getOne() {
        String productId = "2";
        ProductInfo result = productInfoService.getOne(productId);
        log.info("result:{}", result.toString());
        Assert.assertEquals("2", result.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> result = productInfoService.findUpAll();
        log.info("result:{}", result.toString());
        Assert.assertNotEquals(0, result.size());
    }

    @Test
    public void findAll() {
        Pageable pageable = new PageRequest(0, 2);
        Page<ProductInfo> result = productInfoService.findAll(pageable);
        log.info("result:{}", result.getTotalElements());
        Assert.assertNotEquals(0, result.getTotalPages());
    }

    @Test
    public void save() {
//        ProductInfo productInfo = new ProductInfo();
//        productInfo.setProductId("1");
//        productInfo.setProductName("热干面");
//        productInfo.setProductPrice(new BigDecimal(5));
//        productInfo.setProductStock(100);
//        productInfo.setProductDesc("武汉特色早餐面");
//        productInfo.setProductIcon("http://xxxx.jpg");
//        productInfo.setProductStatus(0);
//        productInfo.setCategoryType(1);
//        ProductInfo result = repository.save(productInfo);
//        log.info("result:{}", result.toString());
//        Assert.assertNotNull(result);
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("2");
        productInfo.setProductName("兰州拉面");
        productInfo.setProductPrice(new BigDecimal(8));
        productInfo.setProductStock(200);
        productInfo.setProductDesc("甘肃面食");
        productInfo.setProductIcon("http://xxxx.jpg");
        productInfo.setProductStatus(1);
        productInfo.setCategoryType(1);
        ProductInfo result = productInfoService.save(productInfo);
        log.info("result:{}", result.toString());
        Assert.assertNotNull(result);
    }
}
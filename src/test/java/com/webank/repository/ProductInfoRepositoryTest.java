package com.webank.repository;

import com.webank.entity.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        Integer productStatus = 1;
        List<ProductInfo> result = repository.findByProductStatus(productStatus);
        log.info("result:{}", result.toString());
        Assert.assertNotEquals(0, result.size());
    }

    @Test
    public void test() {
        Calendar cale;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        // 获取前月的第一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, -1);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        String firstday = format.format(cale.getTime());
        // 获取前月的最后一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        String lastday = format.format(cale.getTime());
        log.info("firstday:{},lastday:{}", firstday, lastday);
    }
}
package com.webank.service;

import com.webank.entity.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * 类目Service测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryServiceTest {
    @Autowired
    private ProductCategoryService productCategoryService;

    @Test
    public void getOne() {
        Integer categoryId = 1;
        ProductCategory result = productCategoryService.getOne(categoryId);
        if (null != result) {
            log.info("result:{}", result.toString());
            Assert.assertEquals(new Integer(1), result.getCategoryId());
        } else {
            log.info("result返回为空");
        }
    }

    @Test
    public void findAll() {
        List<ProductCategory> result = productCategoryService.findAll();
        log.info("result:{}", result.toString());
        Assert.assertNotEquals(0, result.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<Integer> categoryTypeList = Arrays.asList(2, 3);
        List<ProductCategory> result = productCategoryService.findByCategoryTypeIn(categoryTypeList);
        log.info("result:{}", result.toString());
        Assert.assertNotEquals(0, result.size());
    }

    @Test
    public void save() {
        ProductCategory productCategory = new ProductCategory("女生最爱", 3);
        ProductCategory result = productCategoryService.save(productCategory);
        log.info("result:{}", result.toString());
        Assert.assertNotNull(result);
    }
}
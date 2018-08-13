package com.webank.repository;

import com.webank.entity.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 类目Dao测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findByCategoryTypeIn() {
//        List<Integer> categoryTypeList = new ArrayList<>();
//        categoryTypeList.add(1);
//        categoryTypeList.add(2);
        List<Integer> categoryTypeList = Arrays.asList(2, 3);
        List<ProductCategory> result = repository.findByCategoryTypeIn(categoryTypeList);
        log.info("result:{}", result.toString());
        Assert.assertNotEquals(0, result.size());
    }
}
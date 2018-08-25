package com.webank.repository;

import com.webank.entity.SellerInfo;
import com.webank.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 卖家信息Test
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SellerInfoRepositoryTest {
    @Autowired
    private SellerInfoRepository repository;

    @Test
    public void save() {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setUserId(KeyUtil.genUniqueKey());
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("123456");
        sellerInfo.setOpenid("123123");
        SellerInfo result = repository.save(sellerInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOpenid() {
        String openid = "123123";
        SellerInfo result = repository.findByOpenid(openid);
        Assert.assertEquals("123123", result.getOpenid());
    }
}
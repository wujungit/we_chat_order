package com.webank.service.impl;

import com.webank.entity.SellerInfo;
import com.webank.repository.SellerInfoRepository;
import com.webank.service.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellerInfoRepository repository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }
}

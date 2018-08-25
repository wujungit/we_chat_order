package com.webank.service;

import com.webank.entity.SellerInfo;

/**
 * 卖家信息Service
 */
public interface SellerService {
    /**
     * 通过openid查询卖家端信息
     *
     * @param openid
     * @return
     */
    SellerInfo findSellerInfoByOpenid(String openid);
}

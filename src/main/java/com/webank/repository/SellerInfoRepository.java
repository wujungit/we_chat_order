package com.webank.repository;

import com.webank.entity.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 卖家信息Dao
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {
    SellerInfo findByOpenid(String openid);
}

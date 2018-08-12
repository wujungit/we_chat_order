package com.webank.service;

import com.webank.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 商品Service
 */
public interface ProductInfoService {
    ProductInfo getOne(String productId);

    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

//    void increaseStock(List<CartDTO> cartDTOList);
//
//    void decreaseStock(List<CartDTO> cartDTOList);
//
//    ProductInfo onSale(String productId);
//
//    ProductInfo offSale(String productId);
}

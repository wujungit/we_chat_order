package com.webank.service;

import com.webank.dto.CartDto;
import com.webank.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 商品Service
 */
public interface ProductInfoService {
    ProductInfo getOne(String productId);

    /**
     * 查询所有上架的商品
     *
     * @return
     */
    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    /**
     * 加库存
     *
     * @param cartDtoList
     */
    void increaseStock(List<CartDto> cartDtoList);

    /**
     * 减库存
     *
     * @param cartDtoList
     */
    void decreaseStock(List<CartDto> cartDtoList);

    ProductInfo onSale(String productId);

    ProductInfo offSale(String productId);
}

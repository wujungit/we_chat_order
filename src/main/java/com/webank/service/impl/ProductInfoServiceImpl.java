package com.webank.service.impl;

import com.webank.dto.CartDto;
import com.webank.entity.ProductInfo;
import com.webank.enums.ProductStatusEnum;
import com.webank.enums.ResultEnum;
import com.webank.exception.WeChatOrderException;
import com.webank.repository.ProductInfoRepository;
import com.webank.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品ServiceImpl
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "product")
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    private ProductInfoRepository repository;

    @Override
//    @Cacheable(key = "123")
    public ProductInfo getOne(String productId) {
        return repository.findById(productId).orElse(null);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        Integer productStatus = ProductStatusEnum.UP.getCode();
        return repository.findByProductStatus(productStatus);
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
//    @Cacheable(key = "123")
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDto> cartDtoList) {
        for (CartDto cartDto : cartDtoList) {
            String productId = cartDto.getProductId();
            Integer productQuantity = cartDto.getProductQuantity();
            ProductInfo productInfo = repository.findById(productId).orElse(null);
            if (null == productInfo) {
                throw new WeChatOrderException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            productInfo.setProductStock(productInfo.getProductStock() + productQuantity);
            repository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDto> cartDtoList) {
        for (CartDto cartDto : cartDtoList) {
            String productId = cartDto.getProductId();
            ProductInfo productInfo = repository.findById(productId).orElse(null);
            if (null == productInfo) {
                throw new WeChatOrderException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer productStock = productInfo.getProductStock();
            Integer productQuantity = cartDto.getProductQuantity();
            Integer result = productStock - productQuantity;
            if (result < 0) {
                throw new WeChatOrderException(ResultEnum.PRODUCT_UNDER_STOCK);
            }
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = repository.getOne(productId);
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP) {
            throw new WeChatOrderException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        // 更新商品状态:0-正常,1-下架
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return repository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = repository.getOne(productId);
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN) {
            throw new WeChatOrderException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return repository.save(productInfo);
    }
}

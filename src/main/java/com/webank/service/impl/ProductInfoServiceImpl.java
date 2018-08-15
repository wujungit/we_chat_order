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
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    private ProductInfoRepository repository;

    @Override
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
        return null;
    }

    @Override
    public ProductInfo offSale(String productId) {
        return null;
    }
}

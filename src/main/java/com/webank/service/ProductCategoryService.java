package com.webank.service;

import com.webank.entity.ProductCategory;

import java.util.List;

/**
 * 类目Service
 */
public interface ProductCategoryService {
    ProductCategory getOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}

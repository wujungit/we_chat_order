package com.webank.service.impl;

import com.webank.entity.ProductCategory;
import com.webank.repository.ProductCategoryRepository;
import com.webank.service.ProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类目ServiceImpl
 */
@Service
@Slf4j
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryRepository repository;

    @Autowired
    public ProductCategoryServiceImpl(ProductCategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductCategory getOne(Integer categoryId) {
        return repository.findById(categoryId).orElse(null);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }
}

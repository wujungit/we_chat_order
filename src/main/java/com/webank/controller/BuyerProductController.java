package com.webank.controller;

import com.webank.entity.ProductCategory;
import com.webank.entity.ProductInfo;
import com.webank.service.ProductCategoryService;
import com.webank.service.ProductInfoService;
import com.webank.utils.ResultVoUtil;
import com.webank.vo.ProductInfoVo;
import com.webank.vo.ProductVo;
import com.webank.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 买家端商品
 */
@RestController
@RequestMapping("/buyer/product")
@Slf4j
public class BuyerProductController {
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductCategoryService productCategoryService;

    // 商品列表
    @RequestMapping("/list")
    @Cacheable(cacheNames = "product", key = "#userId", condition = "#userId.length()>3", unless = "#result.code!=0")
    public ResultVo list(@RequestParam("userId") String userId) {
        // 查询所有上架商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();
        // 查询类目（一次性查询）
        List<Integer> categoryTypeList = new ArrayList<>();
        for (ProductInfo productInfo : productInfoList) {
            categoryTypeList.add(productInfo.getCategoryType());
        }
        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);
        // 数据拼装
        List<ProductVo> productVoList = getProductVoList(productInfoList, productCategoryList);
        return ResultVoUtil.success(productVoList);
    }

    private List<ProductVo> getProductVoList(List<ProductInfo> productInfoList, List<ProductCategory> productCategoryList) {
        List<ProductVo> productVoList = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList) {
            List<ProductInfoVo> productInfoVoList = new ArrayList<>();
            ProductVo productVo = new ProductVo();
            BeanUtils.copyProperties(productCategory, productVo);
            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVo productInfoVo = new ProductInfoVo();
                    BeanUtils.copyProperties(productInfo, productInfoVo);
                    productInfoVoList.add(productInfoVo);
                }
            }
            productVo.setProductInfoVoList(productInfoVoList);
            productVoList.add(productVo);
        }
        return productVoList;
    }
}

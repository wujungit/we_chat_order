package com.webank.controller;

import com.webank.entity.ProductCategory;
import com.webank.form.CategoryForm;
import com.webank.service.ProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 卖家端类目
 */
@Controller
@RequestMapping("/seller/category")
@Slf4j
public class SellerCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 类目列表
     *
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list() {
        Map<String, Object> map = new HashMap<>();
        List<ProductCategory> categoryList = productCategoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("category/list", map);
    }

    /**
     * 展示
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId) {
        Map<String, Object> map = new HashMap<>();
        if (null != categoryId) {
            ProductCategory productCategory = productCategoryService.getOne(categoryId);
            map.put("category", productCategory);
        }
        return new ModelAndView("category/index", map);
    }

    /**
     * 类目保存/更新
     *
     * @param categoryForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm, BindingResult bindingResult) {
        Map<String, Object> map = new HashMap<>();
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/we_chat_order/seller/category/index");
            return new ModelAndView("common/error", map);
        }
        ProductCategory productCategory = new ProductCategory();
        try {
            if (null != categoryForm.getCategoryId()) {
                productCategory = productCategoryService.getOne(categoryForm.getCategoryId());
            }
            BeanUtils.copyProperties(categoryForm, productCategory);
            productCategoryService.save(productCategory);
        } catch (BeansException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/we_chat_order/seller/category/index");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/we_chat_order/seller/category/list");
        return new ModelAndView("common/success", map);
    }
}

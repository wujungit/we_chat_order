package com.webank.controller;

import com.webank.entity.ProductCategory;
import com.webank.entity.ProductInfo;
import com.webank.enums.ResultEnum;
import com.webank.exception.WeChatOrderException;
import com.webank.form.ProductForm;
import com.webank.service.ProductCategoryService;
import com.webank.service.ProductInfoService;
import com.webank.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
 * 卖家端商品
 */
@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<ProductInfo> productInfoPage = productInfoService.findAll(pageRequest);
        Map<String, Object> map = new HashMap<>();
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("product/list", map);
    }

    /**
     * 商品上架
     *
     * @param productId
     * @return
     */
    @GetMapping("/onSale")
    public ModelAndView onSale(@RequestParam("productId") String productId) {
        Map<String, Object> map = new HashMap<>();
        try {
            productInfoService.onSale(productId);
        } catch (WeChatOrderException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/we_chat_order/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMsg());
        map.put("url", "/we_chat_order/seller/product/list");
        return new ModelAndView("common/success", map);
    }

    /**
     * 商品下架
     *
     * @param productId
     * @return
     */
    @GetMapping("/offSale")
    public ModelAndView offSale(@RequestParam("productId") String productId) {
        Map<String, Object> map = new HashMap<>();
        try {
            productInfoService.offSale(productId);
        } catch (WeChatOrderException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/we_chat_order/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.PRODUCT_OFF_SALE_SUCCESS.getMsg());
        map.put("url", "/we_chat_order/seller/product/list");
        return new ModelAndView("common/success", map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId) {
        Map<String, Object> map = new HashMap<>();
        if (!StringUtils.isEmpty(productId)) {
            ProductInfo productInfo = productInfoService.getOne(productId);
            map.put("productInfo", productInfo);
        }
        // 查询所有的类目
        List<ProductCategory> categoryList = productCategoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("product/index", map);
    }

    /**
     * 商品保存/更新
     *
     * @param productForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm productForm, BindingResult bindingResult) {
        Map<String, Object> map = new HashMap<>();
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/we_chat_order/seller/product/index");
            return new ModelAndView("common/error", map);
        }
        try {
            // 新增时productId为空
            ProductInfo productInfo = new ProductInfo();
            if (!StringUtils.isEmpty(productForm.getProductId())) {
                productInfo = productInfoService.getOne(productForm.getProductId());
            } else {
                productForm.setProductId(KeyUtil.genUniqueKey());
            }
            BeanUtils.copyProperties(productForm, productInfo);
            productInfoService.save(productInfo);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/we_chat_order/seller/product/index");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/we_chat_order/seller/product/list");
        return new ModelAndView("common/success", map);
    }
}

package com.webank.controller;

import com.webank.dto.OrderDto;
import com.webank.entity.ProductInfo;
import com.webank.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
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

//    public ModelAndView
}

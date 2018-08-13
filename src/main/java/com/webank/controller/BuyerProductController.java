package com.webank.controller;

import com.webank.vo.ProductInfoVo;
import com.webank.vo.ProductVo;
import com.webank.vo.ResultVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @RequestMapping("/list")
    public ResultVo list() {
        ProductInfoVo productInfoVo = new ProductInfoVo();
        List<ProductInfoVo> productInfoVoList = new ArrayList<>();
        productInfoVoList.add(productInfoVo);
        ProductVo productVo = new ProductVo();
        productVo.setProductInfoVoList(productInfoVoList);
        List<ProductVo> productVoList = new ArrayList<>();
        productVoList.add(productVo);
        ResultVo<List<ProductVo>> resultVo = new ResultVo<>();
        resultVo.setCode(0);
        resultVo.setMsg("成功");
        resultVo.setData(productVoList);
        return resultVo;
    }
}

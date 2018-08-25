package com.webank.handler;

import com.webank.config.ProjectUrlConfig;
import com.webank.exception.SellerAuthorizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SellerExceptionHandler {
    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    // 拦截登陆异常
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handler() {
        return new ModelAndView("redirect:"
                .concat(projectUrlConfig.getWeChatOpenAuthorize())
                .concat("/we_chat_order/wechat/qrAuthorize")
                .concat("?returnUrl=")
                .concat(projectUrlConfig.getWeChatOrder())
                .concat("/we_chat_order/seller/login"));
    }
}

package com.webank.handler;

import com.webank.config.ProjectUrlConfig;
import com.webank.exception.ResponseBankException;
import com.webank.exception.SellerAuthorizeException;
import com.webank.exception.WeChatOrderException;
import com.webank.utils.ResultVoUtil;
import com.webank.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SellerExceptionHandler {
    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    // 拦截登陆异常
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorize() {
        return new ModelAndView("redirect:"
                .concat(projectUrlConfig.getWeChatOpenAuthorize())
                .concat("/we_chat_order/wechat/qrAuthorize")
                .concat("?returnUrl=")
                .concat(projectUrlConfig.getWeChatOrder())
                .concat("/we_chat_order/seller/login"));
    }

    @ExceptionHandler(value = WeChatOrderException.class)
    @ResponseBody
    public ResultVo handlerWeChatOrder(WeChatOrderException e) {
        return ResultVoUtil.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = ResponseBankException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handlerResponseBank() {

    }
}

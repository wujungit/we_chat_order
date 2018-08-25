package com.webank.controller;

import com.webank.config.ProjectUrlConfig;
import com.webank.constant.CookieConstant;
import com.webank.constant.RedisConstant;
import com.webank.entity.SellerInfo;
import com.webank.enums.ResultEnum;
import com.webank.service.SellerService;
import com.webank.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 卖家用户
 */
@Controller
@RequestMapping("/seller/user")
@Slf4j
public class SellerUserController {
    @Autowired
    private SellerService sellerService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    /**
     * 登陆
     *
     * @param openid
     * @param response
     * @return
     */
    @GetMapping("login")
    public ModelAndView login(@RequestParam("openid") String openid, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        // 匹配后台数据库的openid
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if (null == sellerInfo) {
            map.put("msg", ResultEnum.LOGIN_ERROR);
            map.put("url", "/we_chat_order/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        // 设置token到redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
        // 参数：key value 过期时间 时间的单位
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token),
                openid, expire, TimeUnit.SECONDS);
        // 设置token到cookie
        CookieUtil.set(response, CookieConstant.TOKEN, token, expire);
        // 跳转时，一定用完整的http地址
        String weChatOrder = projectUrlConfig.getWeChatOrder();
        return new ModelAndView("redirect:" + weChatOrder + "/we_chat_order/seller/order/list");
    }

    /**
     * 登出
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping("logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        // 从Cookie里查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (null != cookie) {
            // 清除Redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,
                    cookie.getValue()));
            // 清除Cookie
            CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
        }
        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMsg());
        map.put("url", "/we_chat_order/seller/order/list");
        return new ModelAndView("common/success", map);
    }
}

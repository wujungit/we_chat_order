package com.webank.aspect;

import com.webank.constant.CookieConstant;
import com.webank.constant.RedisConstant;
import com.webank.exception.SellerAuthorizeException;
import com.webank.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * com.webank.controller.Seller*.*(..))" +
            "&&!execution(public * com.webank.controller.SellerUserController.*(..))")
    public void verify() {

    }

    @Before("verify()")
    public void doVerify() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 查询Cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (null == cookie) {
            log.warn("【登陆校验】Cookie中查询不到token");
            throw new SellerAuthorizeException();
        }
        // 去redis查询
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,
                cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)) {
            log.warn("【登陆校验】redis中查询不到token");
            throw new SellerAuthorizeException();
        }
    }
}

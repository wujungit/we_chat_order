package com.webank.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "wechat")
public class WeChatAccountConfig {
    private String mpAppId;//公众平台ID
    private String mpAppSecret;//公众平台密钥
    private String openAppId;//开放平台ID
    private String openAppSecret;//开放平台密钥
    private String mchId;//商户号
    private String mchKey;//商户密钥
    private String keyPath;//商户证书路径
    private String notifyUrl;//微信支付异步通知地址
}

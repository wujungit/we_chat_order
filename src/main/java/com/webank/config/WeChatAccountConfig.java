package com.webank.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "weChat")
public class WeChatAccountConfig {
    private String mpAppId;
    private String mpAppSecret;
}

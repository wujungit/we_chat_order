package com.webank.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "projectUrl")
public class ProjectUrlConfig {
    public String weChatOrder;//项目url
    public String weChatMpAuthorize;//微信公众平台授权url
    public String weChatOpenAuthorize;//微信开放平台授权url
}

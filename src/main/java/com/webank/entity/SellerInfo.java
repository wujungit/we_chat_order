package com.webank.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 卖家信息
 */
@Data
@Entity
public class SellerInfo {
    @Id
    private String userId;//用户ID
    private String username;//用户名
    private String password;//密码
    private String openid;//微信openid
    private Date createTime;//创建时间
    private Date updateTime;//修改时间
}

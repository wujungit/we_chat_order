package com.webank.entity;

import com.webank.enums.ProductStatusEnum;
import com.webank.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品
 */
@Entity
@DynamicUpdate
@Data
public class ProductInfo {
    @Id
    private String productId;//商品ID
    private String productName;//商品名称
    private BigDecimal productPrice;//单价
    private Integer productStock;//库存
    private String productDesc;//描述
    /**
     * 为什么图片不使用传统的选择文件上传的方式
     * 由于系统需要支持分布式，传统的图片上传方式，只能上传到某一台服务器的某个路径下面，达不到分布式目的
     * 可以使用第三方的CDN存储，将静态资源保存在第三方，或者创建一台文件服务器
     */
    private String productIcon;//小图
    private Integer productStatus = ProductStatusEnum.UP.getCode();//商品状态:0-正常,1-下架
    private Integer categoryType;//类目编号
    private Date createTime;//创建时间
    private Date updateTime;//修改时间

    public ProductStatusEnum getProductStatusEnum() {
        return EnumUtil.getByCode(productStatus, ProductStatusEnum.class);
    }
}

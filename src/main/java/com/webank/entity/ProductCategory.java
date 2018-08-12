package com.webank.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 类目
 */
@Entity
@DynamicUpdate
@Data
public class ProductCategory {
    @Id//主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增类型
    private Integer categoryId;//类目ID
    private String categoryName;//类目名字
    private Integer categoryType;//类目编号
    private Date createTime;//创建时间
    private Date updateTime;//修改时间

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}

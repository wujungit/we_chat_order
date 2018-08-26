package com.webank.dao;

import com.webank.entity.ProductCategory;
import org.apache.ibatis.annotations.*;

import java.util.Map;

/**
 * mybatis注解方式使用
 */
public interface ProductCategoryMapper {
    @Insert("insert into product_category(category_name,category_type)values(#{category_name,jdbcType=VARCHAR},#{category_type,jdbcType=INTEGER})")
    int insertByMap(Map<String, Object> map);

    @Insert("insert into product_category(category_name,category_type)values(#{categoryName,jdbcType=VARCHAR},#{categoryType,jdbcType=INTEGER})")
    int insertByObject(ProductCategory productCategory);

    @Select("select * from product_category where categoryType = #{categoryType}")
    @Results({
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "category_name", property = "categoryName"),
            @Result(column = "category_type", property = "categoryType")
    })
    ProductCategory findByCategoryType(Integer categoryType);

    @Update("update product_category set category_name = #{categoryName} where categoryType = #{categoryType}")
    int updateByCategoryTpye(@Param("categoryName") String categoryName, @Param("categoryType") Integer categoryType);
}

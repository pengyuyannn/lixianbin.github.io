package com.leon.mapper;

import com.leon.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 购物车 Mapper
 */
@Mapper
public interface ShoppingCartMapper {
    
    int insert(ShoppingCart shoppingCart);
    
    int updateById(ShoppingCart shoppingCart);
    
    ShoppingCart selectById(@Param("id") Integer id);
    
    ShoppingCart selectByStudentIdAndProductId(@Param("studentId") Integer studentId, @Param("productId") Integer productId);
    
    List<ShoppingCart> selectByStudentId(@Param("studentId") Integer studentId);
    
    int deleteById(@Param("id") Integer id);
    
    int deleteByStudentId(@Param("studentId") Integer studentId);
}

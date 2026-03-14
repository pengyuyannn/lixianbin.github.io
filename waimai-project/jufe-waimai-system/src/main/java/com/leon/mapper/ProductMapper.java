package com.leon.mapper;

import com.leon.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品 Mapper
 */
@Mapper
public interface ProductMapper {
    
    int insert(Product product);
    
    int updateById(Product product);
    
    Product selectById(@Param("id") Integer id);
    
    List<Product> selectByMerchantId(@Param("merchantId") Integer merchantId);
    
    List<Product> selectList(Product product);
    
    int deleteById(@Param("id") Integer id);
    
    int updateStock(@Param("id") Integer id, @Param("stock") Integer stock);
}
